# Agent Instructions

## Scope

- The Java application is in [`demo/`](demo/).
- Main sources use the `com.lab5` package, with controllers in `com.lab5.controllers` and models in `com.lab5.models`.
- The project is a Spring Boot Maven application; consult [`demo/pom.xml`](demo/pom.xml) for dependencies and Java version.

## Validation

- Run `mvn test` from [`demo/`](demo/) after Java or Maven changes.
- Keep changes focused on the requested laboratory behavior and preserve the existing Spring Boot structure.

## Interaction Preference: Disable Autocomplete

- Do not proactively autocomplete, invent, or expand code beyond the user’s explicit request.
- When the user asks for an implementation, make the smallest complete change supported by the surrounding code.
- Ask a concise clarifying question when the requested behavior or acceptance criteria are ambiguous instead of guessing.
- Do not modify editor settings or application code solely to change autocomplete behavior; this file controls agent behavior only.
