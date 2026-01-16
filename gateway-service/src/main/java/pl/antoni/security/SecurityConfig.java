package pl.antoni.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final OAuth2LoginSuccessHandler successHandler;
    private final OAuth2AuthorizationRequestResolver resolver;

    public SecurityConfig(OAuth2LoginSuccessHandler successHandler, OAuth2AuthorizationRequestResolver resolver) {
        this.successHandler = successHandler;
        this.resolver = resolver;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/outbox", "/outbox/**", "/login", "/oauth2/**", "/error", "/actuator/**").permitAll()
                        .requestMatchers("/gmail/**", "/api/gmail/**").authenticated()
                        .anyRequest().authenticated()
                )
                .oauth2Login(o -> o
                        .authorizationEndpoint(a -> a.authorizationRequestResolver(resolver))
                        .successHandler(successHandler)
                )
                .logout(l -> l.logoutSuccessUrl("/"));

        return http.build();
    }
}
