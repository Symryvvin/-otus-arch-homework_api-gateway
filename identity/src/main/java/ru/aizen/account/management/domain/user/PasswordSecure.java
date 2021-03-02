package ru.aizen.account.management.domain.user;

public interface PasswordSecure {

	String encrypt(String password) throws PasswordSecureException;

	boolean validate(String password, String encryptedPassword) throws PasswordSecureException;
}
