package com.example.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
        UserDetails user1 = User.withUsername("user1").password(passwordEncoder.encode("12345")).authorities("USER").build();
        UserDetails admin = User.withUsername("admin").password(passwordEncoder.encode("admin")).authorities("USER", "ADMIN").build();
        return new InMemoryUserDetailsManager(user1, admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/","/film/details/**","/photos/**","/categorie/**","/login").permitAll()
                        //RestApi:
                        .requestMatchers("/api/films/add","/api/films/update/**","/api/films/delete/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/acteurs/add","/api/acteurs/update","/api/acteurs/delete/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/categories/add","/api/categories/update","/api/categories/delete/**").hasAuthority("ADMIN")
                        //Api:
                        .requestMatchers("/film/new", "/film/add","/film/modifier/**","/film/update", "/film/delete/**").hasAuthority("ADMIN")
                        .requestMatchers("/categorie/newcategorie","/categorie/add", "/categorie/modifier/**","/categorie/edit", "/categorie/delete/**").hasAuthority("ADMIN")
                        .requestMatchers("/acteur/newacteur","/acteur/add","/acteur/edit", "/acteur/modifier/**", "/acteur/delete/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                        .formLogin(form->
                                form.loginPage("/login")
                                        .defaultSuccessUrl("/film/all")
                                        .permitAll())
                        .logout(logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                                .permitAll())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}