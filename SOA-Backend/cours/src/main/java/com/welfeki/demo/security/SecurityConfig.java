package com.welfeki.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(requests -> requests
						.requestMatchers("/api/all/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers("/login").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/cours/**", "/api/matieres/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers(HttpMethod.POST, "/api/cours/**", "/api/matieres/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/cours/**", "/api/matieres/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/cours/**", "/api/matieres/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/image/uploadImageCours/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.GET, "/api/image/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers(HttpMethod.POST, "/api/image/upload").hasAuthority("ADMIN")
						.anyRequest().authenticated())
				.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}