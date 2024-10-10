package com.opytha.weatherAPI.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/swagger-ui/**","/v3/api-docs/**","/swagger-ui.html").permitAll()
                .requestMatchers("/api/weather/{cityName}","/api/forecast/{cityName}","/api/pollution/{cityName}","/api/clients").authenticated()
                .anyRequest().denyAll());

        http.csrf(AbstractHttpConfigurer::disable);

        http.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(
                HeadersConfigurer.FrameOptionsConfig::disable));

        http.formLogin( form -> form
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {clearAuthenticationAttributes(request);})
                .failureHandler((request, response, exception) -> response.sendError(401))
        );

        http.logout(httpSecurityLogoutConfigurer ->
                httpSecurityLogoutConfigurer
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                        .deleteCookies("JSESSIONID"));
        http.rememberMe(Customizer.withDefaults());

        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

    // Clase personalizada para manejar la respuesta de autenticación
    private static class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Usuario no registrado");
        }
    }
}
