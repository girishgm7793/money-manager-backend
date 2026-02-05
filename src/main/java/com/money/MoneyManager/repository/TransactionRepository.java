package com.money.MoneyManager.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.money.MoneyManager.model.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

	List<Transaction> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}
