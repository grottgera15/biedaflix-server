package bestworkingconditions.biedaflix.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()
                    .disable()
                    .authorizeRequests()
                    .antMatchers("/login")
                    .permitAll()
                    .antMatchers("/register")
                    .permitAll()
                    .and()
                    .exceptionHandling()
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
