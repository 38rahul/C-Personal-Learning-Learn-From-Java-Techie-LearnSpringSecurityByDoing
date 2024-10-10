package com.example.springsecurity.LearnSpringSecurityByDoing.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {


    //Authentication
    @Bean
    public UserDetailsService userDetailsService(){
//        UserDetails admin = User.withUsername("rahul")
//                .password(encoder.encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withUsername("chhote")
//                .password(encoder.encode("chhote"))
//                .roles("USER")
//                .build();
//
//        return  new InMemoryUserDetailsManager(admin,user);

        return new UserInfoUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/products/welcome").permitAll()
                        .requestMatchers("/products/new").permitAll()
                        .requestMatchers("/products/{id}").authenticated()
                        .requestMatchers("/products/all").authenticated()
                        //hasRole("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .formLogin(Customizer.withDefaults());


        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }

}
