# Development Workflow

1. Always run `./gradlew check` before committing to ensure code quality
2. Use `./gradlew scalafix` to automatically apply code style fixes
3. Build specific modules during development: `./gradlew :{module}:build`
4. Run `./gradlew checkScalafix` in CI to verify code style compliance
5. Use `./gradlew shadowJar` to create deployable artifacts

## Testing Guidelines

- Test fixtures are shared across modules using `testFixtures` source set
- Integration tests in dedicated `integration` module
- Sanity tests in dedicated `sanity` module
