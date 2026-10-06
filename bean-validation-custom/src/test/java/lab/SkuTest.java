package lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

class SkuTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validSkuPasses() {
        assertTrue(validator.validate(new Quantity("ABC-1234", 2)).isEmpty());
    }

    @Test
    void badSkuReportsCustomMessage() {
        var v = validator.validate(new Quantity("abc-12", 2));
        assertEquals(1, v.size());
        var violation = v.iterator().next();
        assertEquals("sku", violation.getPropertyPath().toString());
        assertEquals("must look like ABC-1234", violation.getMessage());
    }

    @Test
    void nullSkuIsRejectedOnlyByNotNull() {
        var v = validator.validate(new Quantity(null, 1));
        assertEquals(1, v.size());
        assertEquals("NotNull", v.iterator().next().getConstraintDescriptor()
                .getAnnotation().annotationType().getSimpleName());
    }

    @Test
    void amountBelowOneFails() {
        assertEquals(1, validator.validate(new Quantity("ABC-1234", 0)).size());
    }
}
