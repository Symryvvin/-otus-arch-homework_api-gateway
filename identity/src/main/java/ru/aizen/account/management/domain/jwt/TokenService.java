package ru.aizen.account.management.domain.jwt;

import ru.aizen.account.management.domain.user.User;

public interface TokenService {

	String generate(User user);

	String validate(String token);

}
