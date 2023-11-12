package com.anhvt.shopapp.configurations;

import com.anhvt.shopapp.components.JwtTokenFilter;
import com.anhvt.shopapp.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//cac req phai co quyen gi do moi cho qua
public class WebSecurityConfig {
    @Value("${api.prefix}")
    private String apiPrefix;

    private final JwtTokenFilter jwtTokenFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers(
                                    apiPrefix + "/users/register",
                                    apiPrefix + "/users/login"
                            )
                            .permitAll()
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/roles**", apiPrefix)).permitAll()

                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/categories**", apiPrefix)).permitAll()

                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/categories/**", apiPrefix)).permitAll()

                            .requestMatchers(HttpMethod.POST,
                                    String.format("%s/categories/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                            .requestMatchers(HttpMethod.PUT,
                                    String.format("%s/categories/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                            .requestMatchers(HttpMethod.DELETE,
                                    String.format("%s/categories/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/products**", apiPrefix)).permitAll()

                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/products/**", apiPrefix)).permitAll()

                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/products/images/*", apiPrefix)).permitAll()

                            .requestMatchers(HttpMethod.POST,
                                    String.format("%s/products**", apiPrefix)).hasAnyRole(Role.ADMIN)

                            .requestMatchers(HttpMethod.PUT,
                                    String.format("%s/products/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                            .requestMatchers(HttpMethod.DELETE,
                                    String.format("%s/products/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                            .requestMatchers(HttpMethod.POST,
                                    String.format("%s/orders/**", apiPrefix)).hasAnyRole(Role.USER)

                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/orders/**", apiPrefix)).permitAll()

                            .requestMatchers(HttpMethod.PUT,
                                    String.format("%s/orders/**", apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(HttpMethod.DELETE,
                                    String.format("%s/orders/**", apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(HttpMethod.POST,
                                    String.format("%s/order_details/**", apiPrefix)).hasAnyRole(Role.USER)

                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/order_details/**", apiPrefix)).permitAll()

                            .requestMatchers(HttpMethod.PUT,
                                    String.format("%s/order_details/**", apiPrefix)).hasRole(Role.ADMIN)

                            .requestMatchers(HttpMethod.DELETE,
                                    String.format("%s/order_details/**", apiPrefix)).hasRole(Role.ADMIN)

                            .anyRequest().authenticated();

                })
                .csrf(AbstractHttpConfigurer::disable);
                httpSecurity.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
                @Override
                public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("*"));
                    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                    configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                    configuration.setExposedHeaders(List.of("x-auth-token"));
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);
                    httpSecurityCorsConfigurer.configurationSource(source);
                }
        });

        return httpSecurity.build();
    }
}
