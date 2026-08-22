package com.app.expense.config;

import com.app.expense.dao.UserAuthenticationDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        {
                            auth.requestMatchers("/login.html", "/register.html").permitAll();
                            auth.anyRequest().authenticated();
                        }
                )
                .formLogin(form ->
                        form.loginPage("/login.html")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/swagger-ui/index.html", true)
                                .permitAll());

        return http.build();
    }
}
