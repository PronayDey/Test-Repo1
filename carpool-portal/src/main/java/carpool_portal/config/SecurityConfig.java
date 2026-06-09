package carpool_portal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import carpool_portal.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {
	 @Autowired
	    private CustomUserDetailsService userDetailsService;

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	        http
	                .authorizeHttpRequests(auth -> auth
	                        .requestMatchers("/register", "/login", "/css/**").permitAll()
	                        .requestMatchers("/dashboard").authenticated()
	                        .anyRequest().authenticated()
	                )

	                .formLogin(form -> form
	                        .loginPage("/login")
	                        .defaultSuccessUrl("/dashboard", true)
	                        .failureUrl("/login?error=true")
	                        .permitAll()
	                )

	                .logout(logout -> logout
	                        .logoutSuccessUrl("/login?logout=true")
	                        .permitAll()
	                );

	        return http.build();
	    }

}
