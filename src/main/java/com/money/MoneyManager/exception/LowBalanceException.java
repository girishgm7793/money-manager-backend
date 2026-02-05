package com.money.MoneyManager.exception;

public class LowBalanceException extends RuntimeException {
	public LowBalanceException(String msg) {
		super(msg);
	}
}
