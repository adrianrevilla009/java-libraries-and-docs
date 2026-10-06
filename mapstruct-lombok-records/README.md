# mapstruct-lombok-records

A MapStruct mapper between a mutable Lombok `Order` entity and an immutable `OrderDto` record, with a test for both directions.

## Goal

Show how MapStruct, Lombok and records cooperate: Lombok generates setters for the entity, MapStruct builds the record through its canonical constructor, and a forgotten field fails the build.

## Run it

```bash
mvn -q test
```

Expected: `OrderMapperTest` runs 2 tests with no failures and Maven reports `BUILD SUCCESS` (verified here: `Tests run: 2, Failures: 0`).

## What it proves

- `OrderMapper.toDto` renames `customerEmail` to `email` and computes `total` with the `total(...)` default method: 3 x 2.50 gives `7.50`.
- `OrderMapper` uses `unmappedTargetPolicy = ReportingPolicy.ERROR`, so adding a field to `OrderDto` without mapping it stops compilation.
- Mapping back with `toEntity` drops `internalCost` (it is explicitly ignored), so the cost never leaks through the API type and returns as `null`.

## Trade-offs

- Lombok and MapStruct are both annotation processors; the `pom.xml` lists them explicitly (Lombok 1.18.34, MapStruct 1.5.5.Final) and the order matters.
- The `total` expression is a Java string inside `@Mapping`, which the compiler checks only after generation.
- `toEntity` loses the entity id handling and the cost on purpose; a real update flow needs more than this round trip.

## When not to use it

- For one or two trivial copies, a hand-written constructor call is clearer than an annotation processor.
- When the mapping needs heavy business logic, keep that in a service rather than in mapper expressions.
