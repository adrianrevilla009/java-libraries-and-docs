package lab;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/** A SKU looks like ABC-1234. Null is valid: combine with @NotNull to require it. */
@Documented
@Constraint(validatedBy = SkuValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sku {
    String message() default "must look like ABC-1234";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
