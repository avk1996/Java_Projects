package com.app.expense.config;

import com.app.expense.dao.UserAuthenticationDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(UserAuthenticationDao userAuthenticationDao){
        return username -> userAuthenticationDao.findByName(username)
                .orElseThrow(()-> new UsernameNotFoundException("Invalid Username: "+username));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                    csrf(AbstractHttpConfigurer::disable).
                    cors(
                            cors -> cors
                                    .configurationSource( request -> {
                                        CorsConfiguration configuration = new CorsConfiguration();

                                        configuration.setAllowedOrigins(List.of("http://localhost:5173/"));
                                        configuration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "OPTIONS"));
                                        configuration.setAllowedHeaders(List.of("*"));
                                        configuration.setAllowCredentials(true);
                                        return configuration;
                                    })).
                    authorizeHttpRequests(auth -> auth
                            // login and registration
                            .requestMatchers(new AntPathRequestMatcher("/api/expense/auth/login")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/api/expense/register")).permitAll()

                            // admin role
                            .requestMatchers(new AntPathRequestMatcher("/api/expense/admin/**")).hasRole("ADMIN")

                            // user role
                            .requestMatchers(new AntPathRequestMatcher("/api/expense/**")).authenticated()
                            .anyRequest().authenticated()
                    )
        .logout(out->out
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request,response, authentication)->{
                                response.setStatus(HttpServletResponse.SC_OK);
                        })
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"));

        return http.build();
    }
}
