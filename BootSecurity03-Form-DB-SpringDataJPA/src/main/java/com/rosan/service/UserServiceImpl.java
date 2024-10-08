package com.rosan.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rosan.model.UserDetails;
import com.rosan.repo.IUserDetailsRepo;

@Service("userService")
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDetailsRepo userRepo;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public String register(UserDetails details) {
		details.setPwd(encoder.encode(details.getPwd()));
		return userRepo.save(details).getUid() + " UserId is registered";
	}

	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// get Model class object (com.nt.model.UserDetails )
		Optional<com.rosan.model.UserDetails> opt = userRepo.findByUname(username);
		if (opt.isEmpty())
			throw new IllegalArgumentException("user not found ");
		else {
			com.rosan.model.UserDetails details = opt.get();
			/*
			 * //convert Set<String> type roles to Set<SGA> type roles Set<GrantedAuthority>
			 * roles=new HashSet(); for(String role:details.getRoles()) {
			 * SimpleGrantedAuthority authority=new SimpleGrantedAuthority(role);
			 * roles.add(authority); } //convert Model class object(com.nt.model.UserDetails
			 * obj) to Spring secuirty
			 * User(org.springframework.security.core.userdetails.User) object User user=new
			 * User(details.getUname(),details.getPwd(),roles);
			 */
			User user = new User(details.getUname(), details.getPwd(), details.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toSet()));

			return user;
		} // else
	}// method

}// class