package BackEnd.BackEnd.config;

import BackEnd.BackEnd.config.token.TokenFilterConfiguerer;
import BackEnd.BackEnd.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final TokenService tokenService;

    private final String[] PUBLIC = {
            "/actuator/**",
            "/socket/**",
            "/users/login",
            "/users/register",
            "/users/allusers",
            "/users/test",
            "/uploads/**"
//            "/file/image/user-profile",
    };

    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .cors(config -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowCredentials(true);
                    cors.setAllowedOriginPatterns(Collections.singletonList("http://*"));
                    cors.addAllowedHeader("*");
                    cors.addAllowedMethod("GET");
                    cors.addAllowedMethod("POST");
                    cors.addAllowedMethod("PUT");
                    cors.addAllowedMethod("DELETE");
                    cors.addAllowedMethod("OPTIONS");

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", cors);

                    config.configurationSource(source);
                })
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests().requestMatchers(PUBLIC).permitAll()
//                .requestMatchers(HttpMethod.POST, PUBLIC).permitAll()
//                .requestMatchers(HttpMethod.GET, PUBLIC).permitAll()
//                .requestMatchers(HttpMethod.PUT, PUBLIC).permitAll()
//                .requestMatchers(HttpMethod.DELETE, PUBLIC).permitAll()
                .anyRequest().authenticated()
                .and().apply(new TokenFilterConfiguerer(tokenService))
                .and().build();
    }
}
