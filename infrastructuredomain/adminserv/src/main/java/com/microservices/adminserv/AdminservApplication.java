package com.microservices.adminserv;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SuppressWarnings("ALL")
@SpringBootApplication
@EnableAdminServer
public class AdminservApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminservApplication.class, args);
	}

	@Configuration
	public static class SecurityPermitAllConfig {
		@Bean
		protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			return http.authorizeHttpRequests((authorizeRequests) -> authorizeRequests.anyRequest().permitAll())
					.csrf().disable().build();
		}
	}

}
