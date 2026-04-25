package com.verdantroots.pims.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/styles.css", "/css/**", "/js/**", "/images/**").permitAll()

                        // Reports are limited to managers and admins.
                        .requestMatchers("/reports/**").hasAnyRole("MANAGER", "ADMIN")

                        // Inventory pages are available to all authenticated roles.
                        .requestMatchers("/", "/plants/**", "/supplies/**", "/local-goods/**", "/inventory-search/**")
                        .hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

        UserDetails employee = User.builder()
                .username("employee")
                .password(passwordEncoder.encode(getRequiredEnv("PIMS_EMPLOYEE_PASSWORD")))
                .roles("EMPLOYEE")
                .build();

        UserDetails manager = User.builder()
                .username("manager")
                .password(passwordEncoder.encode(getRequiredEnv("PIMS_MANAGER_PASSWORD")))
                .roles("MANAGER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode(getRequiredEnv("PIMS_ADMIN_PASSWORD")))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(employee, manager, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private String getRequiredEnv(String name) {
        String value = System.getenv(name);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: " + name);
        }

        return value;
    }
}
