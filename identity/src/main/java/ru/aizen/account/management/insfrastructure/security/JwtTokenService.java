package ru.aizen.account.management.insfrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import ru.aizen.account.management.domain.jwt.TokenService;
import ru.aizen.account.management.domain.user.User;

import javax.annotation.PostConstruct;
import java.security.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenService implements TokenService {

	private PrivateKey privateKey;
	private PublicKey publicKey;

	@PostConstruct
	public void init() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		privateKey = keyPair.getPrivate();
		publicKey = keyPair.getPublic();
	}

	@Override
	public String generate(User user) {
		Date date = Date.from(LocalDateTime.now().plusHours(12).atZone(ZoneId.systemDefault()).toInstant());
		return Jwts.builder()
				.setSubject(user.getUsername())
				.setExpiration(date)
				.signWith(SignatureAlgorithm.RS256, privateKey)
				.compact();

	}

	@Override
	public String validate(String token) {
		return Jwts.parser()
				.setSigningKey(publicKey)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

	@Override
	public String getPublicKey() {
		return Base64.getEncoder().encodeToString(publicKey.getEncoded());
	}

}
