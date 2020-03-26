package bestworkingconditions.biedaflix.server.identity.user;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DeviceService{

    public DeviceService(){
    }

    //response jest z LoginController HttpServletResponse

    public String extractIp(HttpServletResponse response){
        String clientIp;
        String clientXForwardedForIp;
        Collection<String> headers = response.getHeaderNames();
        return "xD";
    }

}
