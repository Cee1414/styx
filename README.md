# Styx

A boundary between worlds.

Styx is a small compiler that carries code downward, from raw syntax into execution. It ferries programs from high-level expressions into precise, explicit instructions.

## Phase One Features:
- Parses arithmetic expressions with correct operator precedence (`+`, `-`, `*`, `/`)
- Supports variables and assignment statements
- Handles parentheses for grouping expressions
- Builds an Abstract Syntax Tree (AST) using an ANTLR visitor
- Prints a structured representation of the AST

- Performs a simple semantic-level operation:
  - Traverses the AST to detect use of variables before assignment
  - Demonstrates manipulation of the parse tree via a custom visitor

- Supports multi-line programs with multiple statements
- Accepts input from standard input or file redirection


## Dependencies

- Java (JDK 8+)
- ANTLR4 (`antlr4` command available)

## Build

```bash
CP=$(grep '^CLASSPATH=' "$(which antlr4)" | cut -d= -f2-):.
antlr4 -visitor styx.g4
javac -cp "$CP:." *.java
```

## Run/Test
```bash
echo "5+2*3+4" | java -cp "$CP:." Driver
java -cp "$CP:." Driver < input.txt