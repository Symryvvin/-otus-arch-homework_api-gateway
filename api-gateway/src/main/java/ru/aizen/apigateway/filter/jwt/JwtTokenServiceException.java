package ru.aizen.apigateway.filter.jwt;

public class JwtTokenServiceException extends Exception {

	public JwtTokenServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
