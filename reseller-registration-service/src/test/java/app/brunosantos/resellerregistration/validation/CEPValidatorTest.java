package app.brunosantos.resellerregistration.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CEPValidatorTest {
    private CEPValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new CEPValidator();
        context = null;
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "12345-678",
        "12345678",
        "04538-132"
    })
    void shouldValidateValidCEP(String cep) {
        assertTrue(validator.isValid(cep, context));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "1234-567",
        "00000000",
        "11111111",
        "invalid-cep"
    })
    void shouldInvalidateInvalidCEP(String cep) {
        assertFalse(validator.isValid(cep, context));
    }
}