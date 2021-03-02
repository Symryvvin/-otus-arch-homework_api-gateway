package ru.aizen.account.management.application.rest;

import lombok.Getter;

@Getter
public class RegistrationCompleteResponse {

	private final boolean success;
	private final int status;
	private final String message;

	public RegistrationCompleteResponse(boolean success, int status, String message) {
		this.success = success;
		this.status = status;
		this.message = message;
	}
}
