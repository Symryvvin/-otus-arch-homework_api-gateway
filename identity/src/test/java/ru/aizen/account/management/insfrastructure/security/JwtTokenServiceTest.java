package ru.aizen.account.management.insfrastructure.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.aizen.account.management.domain.jwt.TokenService;
import ru.aizen.account.management.domain.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JwtTokenServiceTest {

	@Autowired
	TokenService tokenService;

	@Test
	void generate() {
		String token = tokenService.generate(User.register("login", "login@domain.com", "password"));
		assertEquals("login", tokenService.validate(token));
	}

}