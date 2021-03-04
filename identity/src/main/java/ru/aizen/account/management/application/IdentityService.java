package ru.aizen.account.management.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aizen.account.management.domain.jwt.TokenService;
import ru.aizen.account.management.domain.user.*;

import java.util.Optional;


@Service
public class IdentityService {

	private final UserRepository userRepository;
	private final PasswordSecure passwordSecure;
	private final TokenService tokenService;

	@Autowired
	public IdentityService(UserRepository userRepository, PasswordSecure passwordSecure, TokenService tokenService) {
		this.userRepository = userRepository;
		this.passwordSecure = passwordSecure;
		this.tokenService = tokenService;
	}

	public void create(String username, String email, String password) throws IdentityServiceException {
		try {
			if (userRepository.userAlreadyExists(username, email)) {
				throw new IdentityServiceException("User with username '" + username + "' or e-mail '" + email + "' already exists");
			} else {
				User user = User.register(username, email, passwordSecure.encrypt(password));
				userRepository.save(user);
			}
		} catch (UserRepositoryException | PasswordSecureException e) {
			throw new IdentityServiceException(e);
		}
	}

	public void update(String username, String firstName, String lastName, String email, String phone)
			throws IdentityServiceException {
		try {
			Optional<User> userOptional = userRepository.findByUsername(username);

			if (userOptional.isPresent()) {
				User user = userOptional.get();
				user.update(firstName, lastName, email, phone);
				userRepository.update(user);
			} else {
				throw new IdentityServiceException("User '" + username + "' not found");
			}
		} catch (UserRepositoryException e) {
			throw new IdentityServiceException(e);
		}
	}

	public User findUser(String username) throws IdentityServiceException {
		try {
			Optional<User> userOptional = userRepository.findByUsername(username);

			if (userOptional.isPresent()) {
				return userOptional.get();
			} else {
				throw new IdentityServiceException("User '" + username + "' not found");
			}
		} catch (UserRepositoryException e) {
			throw new IdentityServiceException(e);
		}
	}

	public String authenticate(String username, String password) throws IdentityServiceException {
		try {
			Optional<User> userOptional = userRepository.findByUsername(username);

			if (userOptional.isPresent()) {
				User user = userOptional.get();

				if (passwordSecure.validate(password, user.getPassword())) {
					return tokenService.generate(user);
				} else {
					throw new IdentityServiceException("Username or password is not valid");
				}
			} else {
				throw new IdentityServiceException("User '" + username + "' not found");
			}
		} catch (UserRepositoryException | PasswordSecureException e) {
			throw new IdentityServiceException(e);
		}
	}

}
