package ru.aizen.account.management.insfrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.aizen.account.management.domain.jwt.TokenService;
import ru.aizen.account.management.domain.user.User;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenService implements TokenService {

	@Value("${jwt.privateKey}")
	private String privateKeyValue;
	@Value("${jwt.publicKey}")
	private String publicKeyValue;

	private PrivateKey privateKey;
	private PublicKey publicKey;

	@PostConstruct
	public void init() throws NoSuchAlgorithmException, InvalidKeySpecException {
		privateKey = PemKeyUtils.privateKeyFromString(privateKeyValue);
		publicKey = PemKeyUtils.publicKeyFromString(publicKeyValue);
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

}
