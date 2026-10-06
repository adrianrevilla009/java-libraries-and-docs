# jackson-config-pitfalls

Three common Jackson surprises (dates, unknown properties, polymorphism), each shown with a failing default and a fixed configuration in `JsonPitfallsTest`.

## Goal

Make the default behaviour of Jackson visible so the settings a service needs are chosen on purpose rather than discovered in production.

## Run it

```bash
mvn -q test
```

Expected: `JsonPitfallsTest` runs 5 tests with no failures and Maven reports `BUILD SUCCESS` (verified here: `Tests run: 5, Failures: 0`).

## What it proves

- `Json.naive()` throws `InvalidDefinitionException` when writing a `Placed` record with an `Instant`; `Json.configured()` registers `JavaTimeModule` and writes `{"orderId":"o-1","at":"2026-01-02T03:04:05Z"}`.
- Even with the time module, an extra `"extra":1` property throws `UnrecognizedPropertyException` unless `FAIL_ON_UNKNOWN_PROPERTIES` is disabled, as `Json.configured()` does.
- `Payment.java` allow-lists the subtypes `card` and `invoice` through `@JsonSubTypes`; a card round-trips as `{"type":"card","last4":"4242"}` and an unlisted type `crypto` throws `InvalidTypeIdException`.

## Trade-offs

- Ignoring unknown properties makes readers tolerant of newer producers but also hides typos in field names.
- Named subtypes must be registered in the annotation, so adding a payment type means editing `Payment.java`.
- Only the mapper configuration is tested; there is no Spring Boot wiring of the mapper here.

## When not to use it

- In a Spring Boot application, which already configures a mapper with the time module; set properties there rather than building a mapper by hand.
- When a strict API contract must reject unknown fields, keep the default instead of `Json.configured()`.
