package com.rosan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.rosan.service.IUserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private IUserService service;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(encoder);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// provide logic for Authentication and autherization etc
		http.authorizeRequests().antMatchers("/bank/").permitAll() // no authentication and no authorization
				.antMatchers("/user/register", "/user/showLogin").permitAll().antMatchers("/offers").authenticated() // only
				.antMatchers("/loanApprove").hasRole("MANAGER") // authentication + authorization for "MANAGER"
				// role
				// user // authentication
				.antMatchers("/balance").hasRole("CUSTOMER") // authentication + authorization for
																			// "CUSTOMER", "MANAGER"

				.anyRequest().authenticated()// remaining all request URLs must be authenticated
				// .and().httpBasic()// specify authentication mode
				.and().formLogin().defaultSuccessUrl("/bank/", true) // home page url
				.loginPage("/user/showLogin") // gives facility to
												// logout and
												// relogin
				.loginProcessingUrl("/login").failureUrl("/user/showLogin?error").and().rememberMe() // add remember me
				.and().sessionManagement() // add SessionMaxConcurrency count
				.and().exceptionHandling().accessDeniedPage("/denied")// error / exception handling
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
				.logoutSuccessUrl("/user/showLogin?logout"); // after logout url
		http.sessionManagement().maximumSessions(2).maxSessionsPreventsLogin(true).expiredUrl("/timeout");

	}

}
