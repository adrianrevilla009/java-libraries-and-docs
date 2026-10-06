# bean-validation-custom

A custom Bean Validation constraint, `@Sku`, applied to a `Quantity` record and exercised with a plain `Validator` in `SkuTest`.

## Goal

Show the three parts of a custom constraint (the annotation, the validator class and the place it is used) and how constraints sit on record components.

## Run it

```bash
mvn -q test
```

Expected: `SkuTest` runs 4 tests with no failures and Maven reports `BUILD SUCCESS` (verified here: `Tests run: 4, Failures: 0`).

## What it proves

- `Sku.java` declares the annotation and `SkuValidator.java` accepts only values matching `[A-Z]{3}-\d{4}`; `ABC-1234` passes, `abc-12` yields exactly one violation on path `sku` with the message `must look like ABC-1234`.
- `@Sku` treats `null` as valid, so a null SKU in `Quantity` is rejected only by `@NotNull`, not by the custom rule.
- `@Min(1)` on `amount` rejects `0`, showing built-in and custom constraints side by side on a record.

## Trade-offs

- The test uses `Validation.buildDefaultValidatorFactory()` (Hibernate Validator 8.0.1.Final); there is no Spring integration, so nothing here shows validation in a controller.
- The regex is fixed in the validator; making the format configurable would need extra annotation attributes.
- Treating `null` as valid follows the Bean Validation convention but surprises people who expect `@Sku` alone to require a value.

## When not to use it

- When a rule is used in a single place, a plain check in the service is simpler than a new annotation.
- When validation needs a database or another service call, a custom constraint is an awkward fit.
