package com.app.expense.config;

import com.app.expense.dao.UserAuthenticationDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
            http.csrf(AbstractHttpConfigurer::disable).
                    authorizeHttpRequests(auth -> auth
                            .requestMatchers(new AntPathRequestMatcher("/login.html")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/register.html")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/api/expense/**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/api/expense/auth/login")).permitAll()
                            .anyRequest().authenticated()
                    )
                .formLogin(form ->
                        form.loginPage("/login.html")
                                .loginProcessingUrl("/api/expense/auth/login")
                                .defaultSuccessUrl("/swagger-ui/index.html", true)
                                .failureUrl("/login.html?error")
                                .permitAll());

        return http.build();
    }
}
