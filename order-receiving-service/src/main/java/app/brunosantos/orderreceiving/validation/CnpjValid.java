package app.brunosantos.orderreceiving.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CnpjValidator.class)
@Documented
public @interface CnpjValid {
    String message() default "Invalid CNPJ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}