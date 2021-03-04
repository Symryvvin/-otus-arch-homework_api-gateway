package ru.aizen.apigateway.filter.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class JwtTokenService {

	@Value("${jwt.publicKey}")
	private String publicKeyValue;

	private PublicKey publicKey;

	@PostConstruct
	public void init() throws NoSuchAlgorithmException, InvalidKeySpecException {
		publicKey = publicKeyFromString(publicKeyValue);
	}

	private PublicKey publicKeyFromString(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		publicKey = publicKey.replaceAll("\\n", "")
				.replace("-----BEGIN PUBLIC KEY-----", "")
				.replace("-----END PUBLIC KEY-----", "")
				.replaceAll(" ", "");
		return KeyFactory.getInstance("RSA")
				.generatePublic(
						new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey))
				);
	}

	public String getLogin(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(publicKey)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

}
