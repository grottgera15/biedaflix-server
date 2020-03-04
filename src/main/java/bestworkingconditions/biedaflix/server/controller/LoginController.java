package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.auth.AuthenticationRequest;
import bestworkingconditions.biedaflix.server.model.auth.AuthenticationResponse;
import bestworkingconditions.biedaflix.server.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(){

    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@Valid @RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid credentials!");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        String jwt = jwtService.generateToken(userDetails);

        Cookie jwtTokenCookie = new Cookie("jwt_token",jwt);
        jwtTokenCookie.setDomain("biedaflix.pl");
        jwtTokenCookie.setHttpOnly(false);
        jwtTokenCookie.setPath("/");
        response.addCookie(jwtTokenCookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
