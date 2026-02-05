package com.money.MoneyManager.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleAccountNotFound(AccountNotFoundException ex) {

		Map<String, Object> res = new HashMap<>();
		res.put("timestamp", LocalDateTime.now());
		res.put("error", ex.getMessage());
		res.put("status", 404);

		return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(LowBalanceException.class)
	public ResponseEntity<Map<String, Object>> handleLowBalance(LowBalanceException ex) {

		Map<String, Object> res = new HashMap<>();
		res.put("timestamp", LocalDateTime.now());
		res.put("error", ex.getMessage());
		res.put("status", 400);

		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TransactionEditNotAllowedException.class)
	public ResponseEntity<Map<String, Object>> handleEditNotAllowed(TransactionEditNotAllowedException ex) {

		Map<String, Object> res = new HashMap<>();
		res.put("timestamp", LocalDateTime.now());
		res.put("error", ex.getMessage());
		res.put("status", 403);

		return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {

		Map<String, Object> res = new HashMap<>();
		res.put("timestamp", LocalDateTime.now());
		res.put("error", "Internal Server Error");
		res.put("details", ex.getMessage());
		res.put("status", 500);

		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
