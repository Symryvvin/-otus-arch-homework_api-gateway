package ru.aizen.account.management.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aizen.account.management.domain.user.*;

import java.util.Optional;


@Service
public class IdentityService {

	private final UserRepository userRepository;
	private final PasswordSecure passwordSecure;

	@Autowired
	public IdentityService(UserRepository userRepository, PasswordSecure passwordSecure) {
		this.userRepository = userRepository;
		this.passwordSecure = passwordSecure;
	}

	public void create(String username, String email, String password) throws IdentityServiceException {
		try {
			User user = User.register(username, email, passwordSecure.encrypt(password));
			userRepository.save(user);
		} catch (UserRepositoryException | PasswordSecureException e) {
			throw new IdentityServiceException(e);
		}
	}

	@Deprecated
	public void create(String username, String firstName, String lastName, String email, String phone) throws IdentityServiceException {
		try {
			User user = User.from(username, firstName, lastName, email, phone);
			userRepository.save(user);
		} catch (UserRepositoryException e) {
			throw new IdentityServiceException(e);
		}
	}

	public void delete(long userId) throws IdentityServiceException {
		try {
			userRepository.deleteById(userId);
		} catch (UserRepositoryException e) {
			throw new IdentityServiceException(e);
		}
	}

	public void update(long userId, String firstName, String lastName, String email, String phone)
			throws IdentityServiceException {
		try {
			Optional<User> userOptional = userRepository.findById(userId);

			if (userOptional.isPresent()) {
				User user = userOptional.get();

				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setPhone(Phone.from(phone));
				user.setEmail(Email.from(email));

				userRepository.save(user);
			} else {
				throw new IdentityServiceException("User with id " + userId + " not found");
			}
		} catch (UserRepositoryException e) {
			throw new IdentityServiceException(e);
		}
	}

	public User findUser(long userId) throws IdentityServiceException {
		try {
			Optional<User> userOptional = userRepository.findById(userId);

			if (userOptional.isPresent()) {
				return userOptional.get();
			} else {
				throw new IdentityServiceException("User with id " + userId + " not found");
			}
		} catch (UserRepositoryException e) {
			throw new IdentityServiceException("User with id " + userId + " not found");
		}
	}

}
