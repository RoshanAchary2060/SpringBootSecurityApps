package com.rosan.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.rosan.model.UserDetails;

public interface IUserService extends UserDetailsService{
	public String register(UserDetails details);
}
