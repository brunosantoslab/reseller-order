package app.brunosantos.orderreceiving.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;


/**
 * Validation logic for Phone Numbers.
 *
 * @author Bruno da Silva Santos
 * @version 2025.02.24
 * @email contact@brunosantos.app
 * @since 2025-02-24
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValid, String> {

    private static final Pattern PHONE_PATTERN =
        Pattern.compile("^(\\(?\\d{2}\\)?\\s?)(9?\\d{4})[-.\\s]?(\\d{4})$");

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null || phoneNumber.isBlank()) return false;

        String cleaned = phoneNumber.replaceAll("[^\\d]", "");

        return PHONE_PATTERN.matcher(phoneNumber).matches() &&
            (cleaned.length() == 10 || cleaned.length() == 11);
    }
}