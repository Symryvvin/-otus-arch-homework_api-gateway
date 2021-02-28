package ru.aizen.account.management.insfrastructure.persistence;

import ru.aizen.account.management.domain.user.Email;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EmailConverter implements AttributeConverter<Email, String> {

	@Override
	public String convertToDatabaseColumn(Email email) {
		return email.getEmail();
	}

	@Override
	public Email convertToEntityAttribute(String email) {
		return Email.from(email);
	}

}
