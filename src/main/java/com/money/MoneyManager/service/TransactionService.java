package com.money.MoneyManager.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.money.MoneyManager.model.Transaction;
import com.money.MoneyManager.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository repo;

	@Autowired
	private AccountService accountService;

	public Transaction add(Transaction t) {

		if ("INCOME".equals(t.getType())) {
			accountService.credit(t.getFromAccount(), t.getAmount());
		} else if ("EXPENSE".equals(t.getType())) {
			accountService.debit(t.getFromAccount(), t.getAmount());
		} else if ("TRANSFER".equals(t.getType())) {
			accountService.transfer(t.getFromAccount(), t.getToAccount(), t.getAmount());
		}

		return repo.save(t); 
	}

	public Transaction edit(String id, Transaction n) {

		Transaction o = repo.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));

		long hrs = Duration.between(o.getCreatedAt(), LocalDateTime.now()).toHours();

		if (hrs > 12) {
			throw new RuntimeException("Edit not allowed after 12 hours");
		}

		o.setAmount(n.getAmount());
		o.setCategory(n.getCategory());
		o.setDivision(n.getDivision());
		o.setDescription(n.getDescription());

		return repo.save(o);
	}

	// GET ALL
	public List<Transaction> all() {
		return repo.findAll();
	}

	// FILTER BETWEEN DATES
	public List<Transaction> between(LocalDateTime from, LocalDateTime to) {
		return repo.findByCreatedAtBetween(from, to);
	}

	// CATEGORY SUMMARY
	public Map<String, Double> categorySummary() {

		Map<String, Double> map = new HashMap<>();

		for (Transaction t : repo.findAll()) {
			map.put(t.getCategory(), map.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
		}

		return map;
	}

	public Map<String, Double> dashboardSummary() {

		LocalDateTime now = LocalDateTime.now();

		LocalDateTime weekStart = now.minusDays(7);
		LocalDateTime monthStart = now.minusMonths(1);
		LocalDateTime yearStart = now.minusYears(1);

		double weeklyIncome = 0, weeklyExpense = 0;
		double monthlyIncome = 0, monthlyExpense = 0;
		double yearlyIncome = 0, yearlyExpense = 0;

		for (Transaction t : repo.findAll()) {
			LocalDateTime date = t.getCreatedAt();

			if (date.isAfter(weekStart)) {
				if ("INCOME".equals(t.getType()))
					weeklyIncome += t.getAmount();
				else
					weeklyExpense += t.getAmount();
			}

			if (date.isAfter(monthStart)) {
				if ("INCOME".equals(t.getType()))
					monthlyIncome += t.getAmount();
				else
					monthlyExpense += t.getAmount();
			}

			if (date.isAfter(yearStart)) {
				if ("INCOME".equals(t.getType()))
					yearlyIncome += t.getAmount();
				else
					yearlyExpense += t.getAmount();
			}
		}

		Map<String, Double> map = new HashMap<>();
		map.put("weeklyIncome", weeklyIncome);
		map.put("weeklyExpense", weeklyExpense);
		map.put("monthlyIncome", monthlyIncome);
		map.put("monthlyExpense", monthlyExpense);
		map.put("yearlyIncome", yearlyIncome);
		map.put("yearlyExpense", yearlyExpense);

		return map;
	}

}
