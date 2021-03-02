package ru.aizen.account.management.domain.user;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

	long save(User user) throws UserRepositoryException;

	Optional<User> findById(long userId) throws UserRepositoryException;

	boolean userAlreadyExists(String username, String email) throws UserRepositoryException;

	void deleteById(long userId) throws UserRepositoryException;

}
