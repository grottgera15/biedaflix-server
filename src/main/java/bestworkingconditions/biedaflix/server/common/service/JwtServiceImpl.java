package bestworkingconditions.biedaflix.server.common.service;

import bestworkingconditions.biedaflix.server.user.model.User;
import bestworkingconditions.biedaflix.server.user.model.UserAdministrateResponse;
import bestworkingconditions.biedaflix.server.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    private final SecretKey secretKey;
    private final SignatureAlgorithm algorithm;
    private final UserService userService;

    @Autowired
    public JwtServiceImpl(SecretKey secretKey, SignatureAlgorithm algorithm, UserService userService) {
        this.secretKey = secretKey;
        this.algorithm = algorithm;
        this.userService = userService;
    }


    @Override
    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                   .setSigningKey(secretKey)
                   .parseClaimsJws(token)
                   .getBody();
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public String generateToken(User user, Date expiryDate) {
        Map<String, Object> claims = new HashMap<>();

        UserAdministrateResponse payload = userService.CreateUserAdministrateResponseFromUser(user);

        claims.put("user",payload);
        claims.put("sub",payload.getId());
        return createToken(claims,expiryDate);
    }

    @Override
    public String createToken(Map<String, Object> claims , Date expiryDate) {
        return Jwts.builder()
                   .setClaims(claims)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(expiryDate).signWith(secretKey,algorithm).compact();
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String subject = extractSubject(token);
        return ( subject.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
