package com.collegefeedback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // Static resources
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/*.png",
                                "/favicon.ico"
                        ).permitAll()

                        // Public pages
                        .requestMatchers(
                                "/admin/login",
                                "/admin/login-action",
                                "/user/**",
                                "/voting",
                                "/vote-process",
                                "/student/thank-you"
                        ).permitAll()

                        // ✅ ADMIN PAGES (FIXED)
                        .requestMatchers(
                                "/admin/dashboard",
                                "/admin/feedback",          // ✅ ADD THIS
                                "/admin/feedback/**",       // ✅ ADD THIS
                                "/admin/analytics",
                                "/admin/accounts",
                                "/admin/create-account",
                                "/admin/edit-account",
                                "/admin/delete-account",
                                "/admin/reset-password",
                                "/admin/update-password"
                        ).permitAll()

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form.disable())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/admin/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}
