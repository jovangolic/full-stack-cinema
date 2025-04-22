package com.jovan.backend_c2.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class CorsConfig {

        private static final Long MAX_AGE = 3600L;
        private static final int CORS_FILTER_ORDER = -102;

        /*
         * @Bean
         * public FilterRegistrationBean corsFilter() {
         * UrlBasedCorsConfigurationSource source = new
         * UrlBasedCorsConfigurationSource();
         * CorsConfiguration config = new CorsConfiguration();
         * config.setAllowCredentials(true);
         * config.addAllowedOrigin("http://localhost:5173");
         * config.setAllowedHeaders(Arrays.asList(
         * HttpHeaders.AUTHORIZATION,
         * HttpHeaders.CONTENT_TYPE,
         * HttpHeaders.ACCEPT));
         * config.setAllowedMethods(Arrays.asList(
         * HttpMethod.GET.name(),
         * HttpMethod.POST.name(),
         * HttpMethod.PUT.name(),
         * HttpMethod.DELETE.name()));
         * config.setMaxAge(MAX_AGE);
         * source.registerCorsConfiguration("/**", config);
         * FilterRegistrationBean bean = new FilterRegistrationBean(new
         * CorsFilter(source));
         * bean.setOrder(CORS_FILTER_ORDER);
         * return bean;
         * }
         */

        /*
         * @Bean
         * public CorsFilter corsFilter() {
         * UrlBasedCorsConfigurationSource source = new
         * UrlBasedCorsConfigurationSource();
         * CorsConfiguration config = new CorsConfiguration();
         * config.setAllowCredentials(true);
         * config.addAllowedOrigin("http://localhost:5173");
         * config.setAllowedHeaders(Arrays.asList(
         * HttpHeaders.AUTHORIZATION,
         * HttpHeaders.CONTENT_TYPE,
         * HttpHeaders.ACCEPT));
         * config.setAllowedMethods(Arrays.asList(
         * HttpMethod.GET.name(),
         * HttpMethod.POST.name(),
         * HttpMethod.PUT.name(),
         * HttpMethod.DELETE.name(),
         * HttpMethod.OPTIONS.name()));
         * config.setMaxAge(MAX_AGE);
         * source.registerCorsConfiguration("/**", config);
         * return new CorsFilter(source);
         * }
         */

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowCredentials(true);
                config.setAllowedOrigins(List.of("http://localhost:5173")); // Može i sa "*", ali nije preporučeno za
                                                                            // withCredentials
                config.setAllowedHeaders(List.of(
                                HttpHeaders.AUTHORIZATION,
                                HttpHeaders.CONTENT_TYPE,
                                HttpHeaders.ACCEPT));
                config.setAllowedMethods(List.of(
                                HttpMethod.GET.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.DELETE.name(),
                                HttpMethod.OPTIONS.name()));
                config.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);
                return source;
        }
}