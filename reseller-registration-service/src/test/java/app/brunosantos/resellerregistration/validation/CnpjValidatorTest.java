package app.brunosantos.resellerregistration.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CnpjValidatorTest {
    private CnpjValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new CnpjValidator();
        context = null;
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "76.418.728/0001-08",
        "76418728000108",
        "32.536.877/0001-98"
    })
    void shouldValidateValidCNPJ(String cnpj) {
        assertTrue(validator.isValid(cnpj, context));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "11.111.111/1111-11",
        "12345678901234",
        "33.222.111/0001-81", // DV inválido
        "invalid-cnpj"
    })
    void shouldInvalidateInvalidCNPJ(String cnpj) {
        assertFalse(validator.isValid(cnpj, context));
    }
}