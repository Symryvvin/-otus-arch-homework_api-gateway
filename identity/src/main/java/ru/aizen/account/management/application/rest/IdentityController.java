package ru.aizen.account.management.application.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aizen.account.management.application.IdentityService;
import ru.aizen.account.management.application.IdentityServiceException;
import ru.aizen.account.management.domain.user.User;

@RestController
@RequestMapping("api/v1")
public class IdentityController {

	private final IdentityService identityService;

	@Autowired
	public IdentityController(IdentityService identityService) {
		this.identityService = identityService;
	}

	@PostMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@RequestBody UserDataRequest request) throws IdentityServiceException {
		identityService.create(
				request.getUsername(),
				request.getFirstName(),
				request.getLastName(),
				request.getEmail(),
				request.getPhone());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping(path = "/user/{userId}")
	public ResponseEntity<Void> delete(@PathVariable("userId") long userId) throws IdentityServiceException {
		identityService.delete(userId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PutMapping(path = "/user/{userId}")
	public ResponseEntity<Void> update(@PathVariable("userId") long userId, @RequestBody UserDataRequest request)
			throws IdentityServiceException {
		identityService.update(userId,
				request.getFirstName(),
				request.getLastName(),
				request.getEmail(),
				request.getPhone());
		return ResponseEntity.ok().build();
	}

	@GetMapping(path = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public User findUser(@PathVariable("userId") long userId) throws IdentityServiceException {
		return identityService.findUser(userId);
	}

}
