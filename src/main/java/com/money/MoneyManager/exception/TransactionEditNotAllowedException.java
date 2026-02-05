package com.money.MoneyManager.exception;

public class TransactionEditNotAllowedException extends RuntimeException {
	public TransactionEditNotAllowedException(String msg) {
		super(msg);
	}
}
