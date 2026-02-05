package com.money.MoneyManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.money.MoneyManager.model.Account;
import com.money.MoneyManager.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin("*")
public class AccountController {
	@Autowired
	private AccountService service;

	@PostMapping
	public Account create(@RequestBody Account a) {
		return service.create(a);
	}

	@GetMapping
	public List<Account> all() {
		return service.all();
	}

}
