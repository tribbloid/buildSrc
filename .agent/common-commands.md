# Frequently Used Commands

### Build & Compile

```bash
./gradlew testClasses          # Build all production and test modules
./gradlew clean                # Clean build directories
```

### Testing

```bash
./gradlew test                 # Run all tests
./gradlew check                # Run all checks (includes tests)
./gradlew testClasses          # Compile test classes
```

### Code Quality & Formatting

```bash
./gradlew scalafix             # Apply Scalafix rules
./gradlew checkScalafix        # Check Scalafix compliance (read-only)
./gradlew scalafixMain         # Apply Scalafix to main sources
./gradlew scalafixTest         # Apply Scalafix to test sources
```

### Module-Specific Commands

```bash
./gradlew :core:build          # Build core module
./gradlew :web:test           # Test web module  
./gradlew :commons:scalafix   # Fix commons module
./gradlew :prover-commons:build # Build prover-commons submodule
```

### Assembly & Packaging

```bash
./gradlew shadowJar           # Create fat JAR with dependencies
./gradlew :assembly:build     # Build assembly module
```
