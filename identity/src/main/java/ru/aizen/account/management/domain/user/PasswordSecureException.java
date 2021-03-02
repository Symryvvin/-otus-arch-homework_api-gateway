package ru.aizen.account.management.domain.user;

public class PasswordSecureException extends Exception {

	public PasswordSecureException(Throwable cause) {
		super(cause.getMessage(), cause);
	}
}
