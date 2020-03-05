package bestworkingconditions.biedaflix.server.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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

    @Autowired
    public JwtServiceImpl(SecretKey secretKey, SignatureAlgorithm algorithm) {
        this.secretKey = secretKey;
        this.algorithm = algorithm;
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
    public String generateToken(UserDetails userDetails,Date expiryDate) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername(),expiryDate);
    }

    @Override
    public String createToken(Map<String, Object> claims, String subject, Date expiryDate) {
        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(subject)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(expiryDate).signWith(secretKey,algorithm).compact();
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String subject = extractSubject(token);
        return ( subject.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
