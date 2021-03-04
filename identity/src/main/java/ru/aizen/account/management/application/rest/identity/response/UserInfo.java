package ru.aizen.account.management.application.rest.identity.response;

import lombok.Getter;
import ru.aizen.account.management.domain.user.User;

@Getter
public class UserInfo {

	private final String username;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final String phone;

	private UserInfo(String username, String firstName, String lastName, String email, String phone) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
	}

	public static UserInfo fromUser(User user) {
		return new UserInfo(
				user.getUsername(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail() == null ? null : user.getEmail().getEmail(),
				user.getPhone() == null ? null : user.getPhone().getFormattedPhone()
		);
	}

}
