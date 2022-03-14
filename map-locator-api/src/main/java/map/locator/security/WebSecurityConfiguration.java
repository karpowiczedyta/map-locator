package map.locator.security;

import com.tomtom.traffic.legoland.common.jwt.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class WebSecurityConfiguration

    extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "Cookie"));
//        corsConfiguration.setAllowedOrigins(List.of("https://localhost.tomtom.com:3000"));
//        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        http
                .cors().and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
             ;

        http.addFilterBefore(authenticationTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }

    private AuthenticationFilter authenticationTokenFilter() {
        return new AuthenticationFilter(jwtDecoder());
    }

    private JWTDecoder jwtDecoder() {
        JWTPublicKeyProvider jwtPublicKeyProvider = new HttpJWTPublicKeyProvider("https://test.auth.move.tomtom.com/authenticate/public-key");
        return new CommonJWTDecoder(jwtPublicKeyProvider);
    }






}
