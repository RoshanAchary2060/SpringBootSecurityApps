package com.rosan.config;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// provide logic for configuring Authentication Info Provider like InMemoryDB,
		// DB s/w,etc
		// build Authentication Manager by taking given Authentication Info Provider
		// (InMemoryDB)
//		auth.inMemoryAuthentication().withUser("raja").password("{noop}rani").roles("CUSTOMER");
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("raja")
				.password("$2a$10$uQ.7EfIfNSRWCaxGQ.4gQO/uqWs9fEj8CCw0G7CjcWHN6PWVvrAga").roles("CUSTOMER");

//		auth.inMemoryAuthentication().withUser("ramesh").password("{noop}hyd").roles("MANAGER");
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("ramesh")
				.password("$2a$10$r3Y.j0Sb5pm.BwWuraFb/uG8K3u2QLMTP0wXSTD9YApzY7WQRVocO").roles("MANAGER");

	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// provide logic for Authentication and autherization etc
		http.authorizeRequests().antMatchers("/").permitAll() // no authentication and no authorization
				.antMatchers("/offers").authenticated() // only authentication
				.antMatchers("/balance").hasAnyRole("CUSTOMER", "MANAGER") // authentication + authorization for
																			// "CUSTOMER", "MANAGER"
				.antMatchers("/loanApprove").hasAnyRole("MANAGER") // authentication + authorization for "MANAGER" role
																	// user
				.anyRequest().authenticated()// remaining all request URLs must be authenticated
				// .and().httpBasic()// specify authentication mode
				.and().formLogin() // gives facility to logout and relogin
				.and().rememberMe() // add remember me
				.and().sessionManagement() // add SessionMaxConcurrency count
				.and().exceptionHandling().accessDeniedPage("/denied")// error / exception handling
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/signout")); // enable logout
		http.sessionManagement().maximumSessions(2).maxSessionsPreventsLogin(true).expiredUrl("/timeout");

	}

}
