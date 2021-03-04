package ru.aizen.account.management.insfrastructure.persistence;

import org.springframework.stereotype.Repository;
import ru.aizen.account.management.domain.user.User;
import ru.aizen.account.management.domain.user.UserRepository;
import ru.aizen.account.management.domain.user.UserRepositoryException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryUserRepository implements UserRepository {

	private final Map<Long, User> userStorage;
	private long sequence = 0L;

	public InMemoryUserRepository() {
		this.userStorage = new HashMap<>();
	}

	@Override
	public void save(User user) throws UserRepositoryException {
		try {
			sequence++;
			user.updateId(sequence);
			userStorage.put(sequence, user);
		} catch (Exception e) {
			throw new UserRepositoryException(e);
		}
	}

	@Override
	public Optional<User> findById(long userId) throws UserRepositoryException {
		try {
			return Optional.ofNullable(userStorage.get(userId));
		} catch (Exception e) {
			throw new UserRepositoryException(e);
		}
	}

	@Override
	public Optional<User> findByUsername(String username) throws UserRepositoryException {
		try {
			return userStorage.values().stream().filter(u -> u.getUsername().equalsIgnoreCase(username)).findAny();
		} catch (Exception e) {
			throw new UserRepositoryException(e);
		}
	}

	@Override
	public boolean userAlreadyExists(String username, String email) throws UserRepositoryException {
		try {
			return userStorage.values().stream()
					.anyMatch(u -> u.getEmail().getEmail().equalsIgnoreCase(email) || u.getUsername().equalsIgnoreCase(username));
		} catch (Exception e) {
			throw new UserRepositoryException(e);
		}
	}

}
