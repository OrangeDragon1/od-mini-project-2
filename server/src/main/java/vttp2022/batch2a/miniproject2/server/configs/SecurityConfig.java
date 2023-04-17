package vttp2022.batch2a.miniproject2.server.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import vttp2022.batch2a.miniproject2.server.components.JWTAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired private JWTAuthenticationFilter jwtAuthFilter;
  @Autowired private AuthenticationProvider authenticationProvider;

  private final String[] WHITE_LIST = {
    "/api/v1/auth/register",
    "/api/v1/auth/authenticate",
    "/api/v1/auth/verify",
    "/api/v1/auth/forgotPassword",
    "/api/v1/auth/resetPassword",
    "/api/v1/search/**"
  };
  
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity hs) throws Exception {
    hs.csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers(WHITE_LIST)
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return hs.build();
  }

}
