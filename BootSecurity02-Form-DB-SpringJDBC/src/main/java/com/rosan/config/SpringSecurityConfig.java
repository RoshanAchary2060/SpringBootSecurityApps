package com.rosan.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource ds;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(ds).passwordEncoder(new BCryptPasswordEncoder())
				.usersByUsernameQuery("select uname, pwd,status from users where uname=?") // for authentication
				.authoritiesByUsernameQuery("select role, uname from user_roles where uname=?"); // for authorization

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
