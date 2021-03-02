package ru.aizen.account.management.application.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationDTO {

	private String username;
	private String email;
	private String password;

}
