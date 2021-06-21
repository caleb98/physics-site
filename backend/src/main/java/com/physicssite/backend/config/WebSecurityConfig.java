package com.physicssite.backend.config;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import com.physicssite.backend.user.SiteUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public RedirectStrategy redirectStrategy() {
		return new DefaultRedirectStrategy();
	}
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		return new SiteUserDetailsService();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return (request, response, exception) -> {
			response.addHeader("login-error", "true");
		};
	}
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return (request, response, authentication) -> {
			response.addHeader("login-error", "false");
			String loc = "/";
			
			// Look for redirect in session
			HttpSession session = request.getSession();
			DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
			if(defaultSavedRequest != null) {
				loc = defaultSavedRequest.getRedirectUrl();
			}
			
			// Tell frontend where to redirect to
			response.addHeader("login-redirect", loc);
		};
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic();
		http
			.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and()
			.authorizeRequests()
				.antMatchers("/register").permitAll()
				.antMatchers("/courses/**").authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.failureHandler(authenticationFailureHandler())
				.successHandler(authenticationSuccessHandler())
				.permitAll()
				.and()
			.logout()
				.logoutUrl("/logout");
	}
	
}
