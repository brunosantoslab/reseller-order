package app.brunosantos.resellerregistration.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Validation logic for CNPJ
 *
 * @author Bruno da Silva Santos
 * @version 2025.02.24
 * @email contact@brunosantos.app
 * @since 2025-02-24
 */
public class CnpjValidator implements ConstraintValidator<CnpjValid, String> {

    private static final Pattern CNPJ_PATTERN =
        Pattern.compile("^\\d{2}\\.?\\d{3}\\.?\\d{3}/?\\d{4}-?\\d{2}$");

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        if (cnpj == null || cnpj.isBlank()) return false;

        // Remove formatação
        String cleaned = cnpj.replaceAll("[^\\d]", "");

        // Verifica tamanho e formato
        if (cleaned.length() != 14 || !CNPJ_PATTERN.matcher(cnpj).matches()) {
            return false;
        }

        // Verifica dígitos repetidos
        if (cleaned.matches("(\\d)\\1{13}")) return false;

        // Cálculo dos dígitos verificadores
        return validateDigits(cleaned);
    }

    private boolean validateDigits(String cnpj) {
        int[] weights1 = {5,4,3,2,9,8,7,6,5,4,3,2};
        int[] weights2 = {6,5,4,3,2,9,8,7,6,5,4,3,2};

        // Validação do primeiro dígito
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weights1[i];
        }
        int remainder = sum % 11;
        int digit1 = (remainder < 2) ? 0 : 11 - remainder;

        if (Character.getNumericValue(cnpj.charAt(12)) != digit1) {
            return false;
        }

        // Validação do segundo dígito
        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weights2[i];
        }
        remainder = sum % 11;
        int digit2 = (remainder < 2) ? 0 : 11 - remainder;

        return Character.getNumericValue(cnpj.charAt(13)) == digit2;
    }
}