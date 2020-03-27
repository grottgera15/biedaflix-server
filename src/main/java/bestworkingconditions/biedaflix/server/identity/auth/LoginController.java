package bestworkingconditions.biedaflix.server.identity.auth;

import bestworkingconditions.biedaflix.server.identity.user.model.User;
import bestworkingconditions.biedaflix.server.common.properties.AppProperties;
import bestworkingconditions.biedaflix.server.identity.user.DeviceService;
import bestworkingconditions.biedaflix.server.identity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AppProperties appProperties;
    private final UserRepository userRepository;
    //DODALEM
    private final DeviceService deviceService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsService userDetailsService, AppProperties appProperties, UserRepository userRepository, DeviceService deviceService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.appProperties = appProperties;
        this.userRepository = userRepository;
        this.deviceService = deviceService;
    }

    private Cookie createCookie(String name, String value, Boolean httpOnly){
        Cookie cookie = null;
        try {
            cookie = new Cookie(name, URLEncoder.encode(value,"UTF-8"));
            cookie.setDomain(appProperties.getDomain());
            cookie.setHttpOnly(httpOnly);
            cookie.setMaxAge(60*60*24*30);
            cookie.setPath("/");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return cookie;
    }

    private String refreshUserToken(String email){
        User requestedUser = userRepository.findUserByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"user does not exist")
        );
        UUID refresh_token = UUID.randomUUID();
        requestedUser.setRefreshToken(refresh_token.toString());
        userRepository.save(requestedUser);

        return refresh_token.toString();
    }

    private List<Cookie> generateCookies(User target){

        Date tokenExpiry =  new Date(System.currentTimeMillis() + 1000 * 60 * 15);
        String jwt = jwtService.generateToken(target, tokenExpiry);

        Cookie jwtTokenCookie =  createCookie("jwt_token",jwt,false);
        Cookie jwtTokenExpiry = createCookie("jwt_token_expiry",Long.toString(tokenExpiry.getTime()),false);

        String refreshToken = refreshUserToken(target.getEmail());
        Cookie refreshTokenCookie = createCookie("refresh_token",refreshToken,true);

        return new ArrayList<>(Arrays.asList(jwtTokenCookie, jwtTokenExpiry, refreshTokenCookie));
    }

    private void addCookies(HttpServletResponse response, List<Cookie> cookies){

        for (Cookie c : cookies)
            response.addCookie(c);

    }

    private Optional<User> checkIfUserIsAccepted(String username, String email){

        Optional<User> requestedUser = userRepository.findByUsernameOrEmail(username,email);

        if(appProperties.getRequireUserAcceptance()){
            if(requestedUser.isPresent()){
                if(!requestedUser.get().getAccepted())
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "user is not accepted!");
            }
        }

        return  requestedUser;
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request,HttpServletResponse response){

        Cookie refreshTokenCookie = WebUtils.getCookie(request,"refresh_token");

        if (refreshTokenCookie != null) {
            User requestedUser = userRepository.findUserByRefreshToken(refreshTokenCookie.getValue()).
                        orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid refresh token!"));


            checkIfUserIsAccepted(requestedUser.getUsername(),requestedUser.getEmail());

            requestedUser.setRefreshToken(UUID.randomUUID().toString());
            userRepository.save(requestedUser);
            addCookies(response,generateCookies(requestedUser));

        }else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid refresh token!");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/login" , consumes = {"application/json"})
    public ResponseEntity<?> Login(
            @RequestBody AuthenticationRequest authenticationRequest,
            HttpServletResponse response, HttpServletRequest request) {


        Optional<User> user = checkIfUserIsAccepted(authenticationRequest.getLogin(),authenticationRequest.getLogin());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getLogin(), authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid credentials!");
        }

        user.ifPresent(value -> addCookies(response, generateCookies(value)));

        //DODALEM
        user.ifPresent(value -> deviceService.verifyDevice(value, request));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        Cookie refreshTokenCookie = WebUtils.getCookie(request,"refresh_token");
        if (refreshTokenCookie != null){

            Optional<User> requestedUser = userRepository.findUserByRefreshToken(refreshTokenCookie.getValue());

            if(requestedUser.isPresent()) {
                User u = requestedUser.get();
                u.setRefreshToken("");
                userRepository.save(u);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
