package ru.aizen.account.management.application.rest.identity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserUpdateDTO {

	private String firstName;
	private String lastName;
	private String email;
	private String phone;

}
