package ir.maktab.homeservice.config;

import ir.maktab.homeservice.security.AuthorizationFilter;
import ir.maktab.homeservice.security.JwtAuthenticationFilter;
import ir.maktab.homeservice.security.JwtAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthorizationFilter authorizationFilter;

    public SecurityConfig(
            @Lazy JwtAuthenticationFilter jwtAuthenticationFilter,
                          AuthorizationFilter authorizationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authorizationFilter = authorizationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy
                                        (SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                        .requestMatchers("/api/v1/users/login",
                                "/api/customers/register",
                                "/api/managers/register",
                                "/api/specialist/register",
                                "/api/captcha",
                                "/api/customers/login",
                                "/api/managers/login",
                                "/api/specialist/login",
                                "/api/customers/verify",
                                "/api/specialist/verify",
                                "/api/home-services",
                                "/api/home-services/find-home-service-by-id/{homeServiceId}",
                                "/api/home-services/find-all-by-parent-service-id/{parentServiceId}")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authorizationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService) {

        JwtAuthenticationProvider authenticationProvider =
                new JwtAuthenticationProvider(userDetailsService,
                        passwordEncoder());

        ProviderManager providerManager = new ProviderManager(
                authenticationProvider);

        providerManager.setEraseCredentialsAfterAuthentication(true);

        return providerManager;
    }
}
