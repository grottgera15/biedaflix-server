package bestworkingconditions.biedaflix.server.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtSecretKeyConfig {

    @Bean
    public SignatureAlgorithm AlgorithmBean(){
        return SignatureAlgorithm.HS256;
    }

    @Bean
    public SecretKey GenerateSecretKeyBean(SignatureAlgorithm algorithm){
        return Keys.secretKeyFor(algorithm);
    }

}
