package app.brunosantos.resellerregistration.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberValidatorTest {
    private PhoneNumberValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new PhoneNumberValidator();
        context = null;
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "(11) 99999-9999",
        "11999999999",
        "11 999999999",
        "1133334444"
    })
    void shouldValidateValidPhoneNumbers(String phone) {
        assertTrue(validator.isValid(phone, context));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "1234",
        "(11) 9999-999",
        "invalid-phone",
        "+1 555-123-4567"
    })
    void shouldInvalidateInvalidPhoneNumbers(String phone) {
        assertFalse(validator.isValid(phone, context));
    }
}