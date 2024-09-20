package com.rosan.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rosan.model.UserDetails;

public interface IUserDetailsRepo extends JpaRepository<UserDetails, Integer> {
	public Optional<UserDetails> findByUname(String uname);
}
