# spring-rest-docs

Spring REST Docs snippets generated from MockMvc tests of a tiny Orders controller in `OrdersApp.java`.

## Goal

Generate API documentation (curl and HTTP requests, field tables) as a by-product of tests, so the docs cannot drift from the real responses.

## Run it

```bash
mvn -q test
ls target/generated-snippets/create-order
```

Expected: `OrdersDocsTest` runs 2 tests with no failures (verified here: `Tests run: 2, Failures: 0`), and the `create-order` and `get-order` folders appear under `target/generated-snippets`, holding files such as `curl-request.adoc`, `http-response.adoc`, `request-fields.adoc`, `response-fields.adoc` and `path-parameters.adoc`.

## What it proves

- `OrdersDocsTest` documents `POST /orders` (request and response fields) and `GET /orders/{id}` (path parameter and response fields) next to the status assertions.
- Spring REST Docs fails a test if a response field is present but not described, so a new field in `OrderView` breaks the build until it is documented.
- The output is plain AsciiDoc that any hand-written page can pull in with `include::`.

## Trade-offs

- Only snippets are produced. Turning them into HTML needs the Asciidoctor Maven plugin, which is not included, so no rendered page exists here.
- Every field needs a description, which is verbose but is the point.
- Pins: Spring Boot 3.3.5 (which manages Spring REST Docs) and Java 21.

## When not to use it

- When you want an interactive "try it out" page; see the sibling `springdoc-openapi` folder.
- When consumers need a machine-readable contract generated from code annotations.
