# File Organization

- Source files: `module/{module-name}/src/main/scala/`
- Test files: `module/{module-name}/src/test/scala/`
- Test fixtures: `module/{module-name}/src/testFixtures/scala/`
- Prover-commons is a Git submodule - changes there require separate commits

## Configuration Files

- `.scalafix.conf` - Scalafix configuration for code style rules
- `.scalafmt.conf` - Scalafmt configuration for code formatting
- `build.gradle.kts` - Main build configuration
- `settings.gradle.kts` - Multi-module project settings
- `gradle.properties` - Gradle build properties
- `.gitmodules` - Git submodule configuration (prover-commons)

## Package Structure

- Module-specific packages follow standard conventions
- SpookyStuff-specific functionality likely under `ai.acyclic.spookystuff`
- Prover commons utilities under `ai.acyclic.prover.commons`
- Do not modify gradle file unless explicitly asked to so
