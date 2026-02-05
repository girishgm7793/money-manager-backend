package com.money.MoneyManager.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.money.MoneyManager.model.Account;

public interface AccountRepository extends MongoRepository<Account, String> {

	Optional<Account> findByNameIgnoreCase(String name);
}
