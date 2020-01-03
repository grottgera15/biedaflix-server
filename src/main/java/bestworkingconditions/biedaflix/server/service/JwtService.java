package bestworkingconditions.biedaflix.server.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String extractSubject(String token);
    Date extractExpiration(String token);
    <T> T extractClaim(String token, Function<Claims,T> claimsResolver);
    Claims extractAllClaims(String token);
    Boolean isTokenExpired(String Token);
    String generateToken(UserDetails userDetails);
    String createToken(Map<String,Object> claims, String subject);
    Boolean validateToken(String token, UserDetails userDetails);
}
