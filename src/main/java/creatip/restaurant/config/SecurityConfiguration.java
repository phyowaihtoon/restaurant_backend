package creatip.restaurant.config;

import creatip.restaurant.controller.filter.SpaWebFilter;
import creatip.restaurant.security.UserRoleConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // If you want to use PathRequest for static resources:
        // import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                // Place SpaWebFilter BEFORE authentication filter so SPA routes are handled first.
                // Change to addFilterAfter/addFilterBefore depending on your SpaWebFilter implementation.
                .addFilterBefore(new SpaWebFilter(), BasicAuthenticationFilter.class)
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                        .referrerPolicy(referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
                        .permissionsPolicy(permissions -> permissions.policy(
                                "camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()"
                        ))
                )
                .authorizeHttpRequests(authz -> authz
                        // Static files & app content - use plain String matchers (MvcRequestMatcher.Builder NOT required).
                        .requestMatchers("/index.html", "/*.js", "/*.txt", "/*.json", "/*.map", "/*.css").permitAll()
                        .requestMatchers("/*.ico", "/*.png", "/*.svg", "/*.webapp").permitAll()
                        .requestMatchers("/app/**").permitAll()
                        .requestMatchers("/i18n/**").permitAll()
                        .requestMatchers("/content/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()

                        // Auth endpoints
                        .requestMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/authenticate").permitAll()
                        .requestMatchers("/api/register").permitAll()
                        .requestMatchers("/api/activate").permitAll()
                        .requestMatchers("/api/account/reset-password/init").permitAll()
                        .requestMatchers("/api/account/reset-password/finish").permitAll()

                        // Admin / management
                        .requestMatchers("/api/admin/**").hasAuthority(UserRoleConstants.ADMIN)
                        .requestMatchers("/v3/api-docs/**").hasAuthority(UserRoleConstants.ADMIN)
                        .requestMatchers("/management/health", "/management/health/**", "/management/info", "/management/prometheus").permitAll()
                        .requestMatchers("/management/**").hasAuthority(UserRoleConstants.ADMIN)

                        // All other API endpoints require authentication
                        .requestMatchers("/api/**").authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
}

