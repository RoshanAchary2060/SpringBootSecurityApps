package com.rosan.controller;

import java.util.Map;
import java.util.Random;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bank")
public class BankOperationsController {
	@GetMapping("/")
	public String showHome() {
		return "home";
	}

	@GetMapping("/balance")
	public String checkBalance(Map<String, Object> map) {
		map.put("amount", new Random().nextInt(100000));

		return "balance";
	}

	@GetMapping("/offers")
	public String showOffers() {
		return "offers";
	}

	@GetMapping("/loanApprove")
	public String approveLoan(Map<String, Object> map) {
		map.put("amount", new Random().nextInt(100000));
		return "loan";
	}

	@GetMapping("/denied")
	public String accessDenied(Map<String, Object> map) {
		map.put("username", SecurityContextHolder.getContext().getAuthentication().getName());
		return "access_denied";
	}

	@GetMapping("/timeout")
	public String sessionExpired() {
		return "session_timeout";
	}
}
