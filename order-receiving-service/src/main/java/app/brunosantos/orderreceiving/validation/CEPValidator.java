package app.brunosantos.orderreceiving.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Validation logic for CEP (ZipCode)
 *
 * @author Bruno da Silva Santos
 * @version 2025.02.24
 * @email contact@brunosantos.app
 * @since 2025-02-24
 */
public class CEPValidator implements ConstraintValidator<CEP, String> {

    private static final Pattern CEP_PATTERN =
        Pattern.compile("^\\d{5}-?\\d{3}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }

        String cleaned = value.replace("-", "");

        return CEP_PATTERN.matcher(value).matches() &&
            !cleaned.matches("(\\d)\\1{7}"); // Evita 00000000, 11111111, etc.
    }
}