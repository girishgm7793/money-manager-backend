package com.money.MoneyManager.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.money.MoneyManager.model.Transaction;
import com.money.MoneyManager.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin("*")
public class TransactionController {

	@Autowired
	private TransactionService service;

	@PostMapping
	public Transaction add(@RequestBody Transaction t) {
		return service.add(t);
	}

	@GetMapping
	public List<Transaction> all() {
		return service.all();
	}

	@PutMapping("/{id}")
	public Transaction edit(@PathVariable String id, @RequestBody Transaction t) {
		return service.edit(id, t);
	}

	@GetMapping("/filter")
	public List<Transaction> filter(@RequestParam String from, @RequestParam String to) {

		return service.between(LocalDateTime.parse(from), LocalDateTime.parse(to));
	}

	@GetMapping("/summary/category")
	public Map<String, Double> summary() {
		return service.categorySummary();
	}

	@GetMapping("/dashboard")
	public Map<String, Double> dashboard() {
		return service.dashboardSummary();
	}

}
