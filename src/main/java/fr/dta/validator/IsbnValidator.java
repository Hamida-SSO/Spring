package fr.dta.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class IsbnValidator implements ConstraintValidator<Isbn, String> {
	public void initialize(Isbn constraintAnnotation ) {
		
	}
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.isEmpty(value) || value.length() == 10;
	}
}
