package ru.aizen.account.management.application.rest.identity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aizen.account.management.application.IdentityService;
import ru.aizen.account.management.application.IdentityServiceException;
import ru.aizen.account.management.application.rest.identity.dto.UserLoginDTO;
import ru.aizen.account.management.application.rest.identity.dto.UserRegistrationDTO;
import ru.aizen.account.management.application.rest.identity.dto.UserUpdateDTO;
import ru.aizen.account.management.application.rest.identity.response.SuccessfulResponse;
import ru.aizen.account.management.application.rest.identity.response.UserInfo;
import ru.aizen.account.management.application.rest.identity.response.UserLoginResponse;

@RestController
@RequestMapping("api/v1")
public class IdentityController {

	private final IdentityService identityService;

	@Autowired
	public IdentityController(IdentityService identityService) {
		this.identityService = identityService;
	}

	@PostMapping(path = "/user/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessfulResponse> create(@RequestBody UserRegistrationDTO request) throws Exception {
		identityService.create(request.getUsername(), request.getEmail(), request.getPassword());
		return ResponseEntity.ok(
				new SuccessfulResponse(HttpStatus.OK.value(), "User registration successful")
		);
	}

	@PostMapping(path = "/user/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserLoginResponse> create(@RequestBody UserLoginDTO request) throws Exception {
		String token = identityService.authenticate(request.getUsername(), request.getPassword());
		return ResponseEntity.ok(
				new UserLoginResponse(true, HttpStatus.OK.value(), token, "User login successful")
		);
	}

	@PutMapping(path = "/user/{username}/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessfulResponse> update(@PathVariable("username") String username, @RequestBody UserUpdateDTO request)
			throws Exception {
		identityService.update(username,
				request.getFirstName(),
				request.getLastName(),
				request.getEmail(),
				request.getPhone());
		return ResponseEntity.ok(
				new SuccessfulResponse(HttpStatus.OK.value(), "User updated successful")
		);
	}

	@GetMapping(path = "/user/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserInfo findUser(@PathVariable("username") String username) throws IdentityServiceException {
		return UserInfo.fromUser(identityService.findUser(username));
	}

}
