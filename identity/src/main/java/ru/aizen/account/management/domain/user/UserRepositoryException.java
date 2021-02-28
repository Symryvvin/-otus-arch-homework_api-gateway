package ru.aizen.account.management.domain.user;

public class UserRepositoryException extends Exception {

	public UserRepositoryException(Throwable cause) {
		super(cause.getMessage(), cause);
	}

}
