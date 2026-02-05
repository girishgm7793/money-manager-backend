package com.money.MoneyManager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.money.MoneyManager.model.Account;
import com.money.MoneyManager.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repo;

	public Account create(Account a) {
		a.setName(a.getName().trim());
		return repo.save(a);
	}

	public List<Account> all() {
		return repo.findAll();
	}

	public void credit(String acc, double amt) {
		String name = acc.trim();

		Account a = repo.findByNameIgnoreCase(name)
				.orElseThrow(() -> new RuntimeException("Account not found: " + name));

		a.setBalance(a.getBalance() + amt);
		repo.save(a);
	}

	public void debit(String acc, double amt) {
		String name = acc.trim();

		Account a = repo.findByNameIgnoreCase(name)
				.orElseThrow(() -> new RuntimeException("Account not found: " + name));

		if (a.getBalance() < amt)
			throw new RuntimeException("Low balance");

		a.setBalance(a.getBalance() - amt);
		repo.save(a);
	}

	public void transfer(String from, String to, double amt) {
		debit(from, amt);
		credit(to, amt);
	}
}
