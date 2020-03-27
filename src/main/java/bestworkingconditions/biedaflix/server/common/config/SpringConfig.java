package bestworkingconditions.biedaflix.server.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import ua_parser.Parser;
import java.io.IOException;

@Configuration
@EnableScheduling
public class SpringConfig {

    @Bean
    public Parser uaParser() throws IOException {
        return new Parser();
    }
}
