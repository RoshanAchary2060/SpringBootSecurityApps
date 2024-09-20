package com.rosan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // makes the Normal @Configuration class Spring Security Configuration class
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService service;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(encoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/bank/").permitAll().antMatchers("/user/register", "/user/showLogin")
				.permitAll().antMatchers("/bank/offers").authenticated().antMatchers("/bank/balance")
				.hasAnyAuthority("CUSTOMER", "MANAGER").antMatchers("/bank/loanApprove").hasAuthority("MANAGER") // Requires
																													// MANAGER
																													// role
				.anyRequest().authenticated() // All other requests require authentication

				.and().formLogin().defaultSuccessUrl("/bank/", true) // Redirect to home after login
				.loginPage("/user/showLogin") // Custom login page
				.loginProcessingUrl("/login") // POST request to log in
				.failureUrl("/user/showLogin?error") // Redirect on login failure

				.and().formLogin() // gives facility to logout and relogin
				.and().rememberMe() // add remember me
				.and().sessionManagement() // add SessionMaxConcurrency count
				.and().exceptionHandling().accessDeniedPage("/bank/denied")// error / exception handling
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
				.logoutSuccessUrl("/user/login?logout");
	}

}