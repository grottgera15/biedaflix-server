package bestworkingconditions.biedaflix.server.identity.user;

import bestworkingconditions.biedaflix.server.identity.user.model.User;
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
    String generateToken(User user, Date expiryDate);
    String createToken(Map<String,Object> claims, Date expiryDate);
    Boolean validateToken(String token, UserDetails userDetails);
}
