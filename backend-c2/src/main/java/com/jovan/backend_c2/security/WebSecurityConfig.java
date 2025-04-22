package com.jovan.backend_c2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jovan.backend_c2.security.jwt.AuthTokenFilter;
import com.jovan.backend_c2.security.jwt.JwtAuthEntryPoint;
import com.jovan.backend_c2.security.user.CinemaUserDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
// ova anotacija je za metod level sigurnost
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
// @EnableMethodSecurity
public class WebSecurityConfig {

	private final CinemaUserDetailsService cinemaDetailsService;

	private final JwtAuthEntryPoint jwtAuthEntryPoint;

	@Bean
	public AuthTokenFilter authenticationTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		var authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(cinemaDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.cors(withDefaults())
				.csrf(AbstractHttpConfigurer::disable)
				.exceptionHandling(
						exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/auth/**", "/movies/**", "/projections/**","/halls/**","/seats/**","movieComingSoon/**","/projectionTypes/**","/tickets/**","/account/**")
						.permitAll().requestMatchers("/roles/**").hasRole("ADMIN")
						.anyRequest().authenticated());
		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}