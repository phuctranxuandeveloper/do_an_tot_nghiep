package com.phuc158965.do_an_tot_nghiep.security.config;

import com.phuc158965.do_an_tot_nghiep.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
    @Autowired
    private UserService userService;

    private static final String[] WHITE_LIST_URL = {"/api/auth/**"};
    private static final String[] ROLE_ALL = {"ROLE_ADMIN", "ROLE_CUSTOMER"};
    private static final String[] ROLE_ADMIN = {"ROLE_ADMIN"};
    private static final String[] ROLE_MANAGER = {"ROLE_MANAGER"};
    private static final String[] ROLE_CUSTOMER = {"ROLE_CUSTOMER"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers(WHITE_LIST_URL)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/songs/**").hasAnyAuthority(ROLE_ALL)
                                .requestMatchers(HttpMethod.GET, "/api/artists/**").hasAnyAuthority(ROLE_ALL)
                                .requestMatchers(HttpMethod.GET, "/api/albums/**").hasAnyAuthority(ROLE_ALL)
                                .requestMatchers(HttpMethod.GET, "/api/playlists/**").hasAnyAuthority(ROLE_CUSTOMER)
                                .requestMatchers(HttpMethod.POST, "/api/songs/**").hasAnyAuthority(ROLE_ADMIN)
                                .requestMatchers(HttpMethod.POST, "/api/artists/**").hasAnyAuthority(ROLE_ADMIN)
                                .requestMatchers(HttpMethod.POST, "/api/albums/**").hasAnyAuthority(ROLE_ADMIN)
                                .requestMatchers(HttpMethod.POST, "/api/playlists/**").hasAnyAuthority(ROLE_CUSTOMER)
                                .requestMatchers(HttpMethod.PUT, "/api/songs/**").hasAnyAuthority(ROLE_ADMIN)
                                .requestMatchers(HttpMethod.PUT, "/api/artists/**").hasAnyAuthority(ROLE_ADMIN)
                                .requestMatchers(HttpMethod.PUT, "/api/albums/**").hasAnyAuthority(ROLE_ADMIN)
                                .requestMatchers(HttpMethod.PUT, "/api/playlists/**").hasAnyAuthority(ROLE_CUSTOMER)
                                .requestMatchers(HttpMethod.DELETE, "/api/songs/**").hasAnyAuthority(ROLE_MANAGER)
                                .requestMatchers(HttpMethod.DELETE, "/api/artists/**").hasAnyAuthority(ROLE_MANAGER)
                                .requestMatchers(HttpMethod.DELETE, "/api/albums/**").hasAnyAuthority(ROLE_MANAGER)
                                .requestMatchers(HttpMethod.DELETE, "/api/playlists/**").hasAnyAuthority(ROLE_CUSTOMER)

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthFilter, UsernamePasswordAuthenticationFilter.class
                );

        return http.build();


    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
