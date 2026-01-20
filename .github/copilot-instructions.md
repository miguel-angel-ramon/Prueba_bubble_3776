# Copilot Instructions for Java Lambda Demos Project

## Project Overview
This is a Maven-based Java project demonstrating lambda expression patterns and functional programming features. The codebase consists of demo classes (Demo01-Demo07) showcasing various lambda techniques.

## Key Patterns and Conventions

### Lambda Storage and Organization
Store lambda functions in `Map.of` collections for easy access and organization. Example from `test_lambda_01_map_of.java`:
```java
functions = Map.of(
    "trimOrUpper", (String v) -> {
        if (v == null || v.isBlank()) return "";
        return v.length() < 4 ? v.trim() : v.toUpperCase();
    }
);
```

### Null Safety in Lambdas
Always include null checks at the beginning of lambda bodies. Pattern seen across all demos:
```java
(String v) -> {
    if (v == null) return "default";
    // process v
}
```

### Higher-Order Functions
Use nested lambdas for functions that return other functions. See `test_lambda_05_nested.java`:
```java
Map<String, Function<String, Function<String, String>>> nested;
```

### BiFunction Usage
For operations requiring two inputs, use `BiFunction`. Example from `test_lambda_02_bifunction.java`:
```java
BiFunction<Integer, Integer, Integer> addOrMax = (a, b) -> {
    if (a + b > 100) return Math.max(a, b);
    return a + b;
};
```

### Streams Integration
Combine lambdas with streams for data processing. Pattern in `test_lambda_06_stream.java`:
```java
return input.stream()
    .map(transformLambda)
    .toList();
```

### Switch in Lambdas
Use switch expressions inside lambdas for conditional logic. See `test_lambda_03_switch.java`.

### Class Naming
Demo classes follow `DemoXX` pattern (e.g., `Demo01`, `Demo02`).

## Build and Run
- Compile: `mvn compile`
- Test: `mvn test` (uses JUnit 5)
- Package: `mvn package` (creates shaded JAR, though main class config may be outdated)
- Run main: `java -cp target/classes com.burbuja.App`

## Dependencies
- Java 8+ (though pom.xml targets 1.6 for compatibility)
- Joda-Time (included but not used in demos)
- JUnit 5 for testing

## Code Style
- Prefer explicit lambda parameter types for clarity
- Use meaningful lambda body names when storing in maps
- Include guard clauses for edge cases (null, empty, bounds)

Focus on functional programming idioms while maintaining readability.