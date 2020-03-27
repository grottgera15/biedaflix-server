package bestworkingconditions.biedaflix.server.identity.user;
import javax.servlet.http.HttpServletRequest;

import bestworkingconditions.biedaflix.server.identity.user.model.Device;
import bestworkingconditions.biedaflix.server.identity.user.model.User;
import ua_parser.Client;
import ua_parser.Parser;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

//request jest z LoginController HttpServletRequest

@Service
public class DeviceService{

    private Parser parser;
    private DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository, Parser parser) {
        this.parser = parser;
        this.deviceRepository = deviceRepository;
    }

    public void verifyDevice(User user, HttpServletRequest request){
        String ip = extractIp(request);
        String deviceDetails = getDeviceDetails(request);

        Device existingDevice = findExistingDevice(user.getId(), deviceDetails);

        if (isNull(existingDevice)){

            Device device  = new Device();
            device.setUserId(user.getId());
            device.setDeviceDetails(deviceDetails);
            device.setLastLoggedIn(new Date());
            List<String> ipList = new ArrayList<>();
            ipList.add(ip);
            device.setIpList(ipList);
            deviceRepository.save(device);
        }
        else {
            List<String> ipList = existingDevice.getIpList();
            if (!ipList.contains(ip)){
                ipList.add(ip);
            }
            existingDevice.setIpList(ipList);
            existingDevice.setLastLoggedIn(new Date());
            deviceRepository.save(existingDevice);
        }
    }

    private Device findExistingDevice(String userId, String deviceDetails) {
        List<Device> knownDevices = deviceRepository.findByUserId(userId);

        for (Device existingDevice : knownDevices) {
            if (existingDevice.getDeviceDetails().equals(deviceDetails)) {
                return existingDevice;
            }
        }
        return null;
    }

    private String extractIp(HttpServletRequest request){
        String clientIp;
        String clientXForwardedForIp = request.getHeader("x-forwarded-for");
        if (nonNull(clientXForwardedForIp)){
            clientIp = parseXForwardedHeader(clientXForwardedForIp);
        }
        else {
            clientIp = request.getRemoteAddr();
        }

        return clientIp;
    }

    private String getDeviceDetails(HttpServletRequest request) {
        String deviceDetails = "UNKNOWN";

        Client client = parser.parse(request.getHeader("user-agent"));
        if (nonNull(client)) {
            deviceDetails = client.userAgent.family
                    + " " + client.userAgent.major + "."
                    + client.userAgent.minor + " - "
                    + client.os.family + " " + client.os.major
                    + "." + client.os.minor;
        }
        return deviceDetails;
    }

    private String parseXForwardedHeader(String header) {
        return header.split(" *, *")[0];
    }

}
