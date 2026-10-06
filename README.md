# java-libraries-and-docs

Five small Maven projects showing how everyday Java libraries (MapStruct, Bean Validation, Jackson) and API documentation tools (Spring REST Docs, springdoc-openapi) are wired, each proven by tests around a tiny Orders domain.

## What is inside

| Folder | What it shows | Run |
| --- | --- | --- |
| [`mapstruct-lombok-records`](./mapstruct-lombok-records) | MapStruct mapping a Lombok entity to and from a record, with unmapped fields as compile errors | `mvn -q test` |
| [`bean-validation-custom`](./bean-validation-custom) | A custom `@Sku` constraint used on a record component | `mvn -q test` |
| [`jackson-config-pitfalls`](./jackson-config-pitfalls) | Jackson defaults that bite: java.time, unknown properties, polymorphism | `mvn -q test` |
| [`spring-rest-docs`](./spring-rest-docs) | AsciiDoc snippets generated from MockMvc tests | `mvn -q test` |
| [`springdoc-openapi`](./springdoc-openapi) | An OpenAPI spec and Swagger UI generated from annotated code | `mvn -q test` |

## Prerequisites

- Java 21
- Maven 3.9 or newer (there is no Maven wrapper)
- Network access on the first run to download dependencies

## How to read it

Start with `mapstruct-lombok-records`, then `bean-validation-custom` and `jackson-config-pitfalls`. Finish with the two documentation folders, `spring-rest-docs` and `springdoc-openapi`, which document nearly the same endpoint in different ways. Run each command from inside its folder.
