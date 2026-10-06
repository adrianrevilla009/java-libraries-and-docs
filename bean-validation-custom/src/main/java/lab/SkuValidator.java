package lab;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class SkuValidator implements ConstraintValidator<Sku, String> {
    private static final Pattern SKU = Pattern.compile("[A-Z]{3}-\\d{4}");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        return value == null || SKU.matcher(value).matches();
    }
}
