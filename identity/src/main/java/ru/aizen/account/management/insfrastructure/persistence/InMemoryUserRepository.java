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
	public long save(User user) throws UserRepositoryException {
		try {
			sequence++;
			user.updateId(sequence);
			userStorage.put(sequence, user);
			return sequence;
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
	public boolean userAlreadyExists(String username, String email) throws UserRepositoryException {
		try {
			return userStorage.values().stream()
					.anyMatch(u -> u.getEmail().getEmail().equals(email) || u.getUsername().equals(username));
		} catch (Exception e) {
			throw new UserRepositoryException(e);
		}
	}

	@Override
	public void deleteById(long userId) throws UserRepositoryException {
		try {
			userStorage.remove(userId);
		} catch (Exception e) {
			throw new UserRepositoryException(e);
		}
	}

}
