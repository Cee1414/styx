# Styx

A boundary between worlds.

Styx is a small compiler that carries code downward, from raw syntax into execution. It ferries programs from high-level code into precise, explicit instructions.

## Features
- Parses arithmetic with correct operator precedence (`+`, `-`, `*`, `/`)
- Supports variables, assignment statements, and nested blocks
- Handles control flow with `if`, `else`, and `while`
- Supports multi-line programs and file/stdin input
- Builds a custom Abstract Syntax Tree (AST) using ANTLR visitors
- Prints structured AST output for debugging and inspection

### Semantic Analysis

- Traverses the AST to detect variables used before assignment
- Tracks initialized parameters and variable state through control flow
- Demonstrates recursive tree traversal and semantic checking passes

### Code Generation

- Targets a custom VM ISA through intermediate code generation
- Uses stack-based evaluation with postorder traversal
- Lowers control flow into labels and conditional branches
- Stores variables at fixed memory offsets
- Generates register-based arithmetic and memory operations


## Dependencies

- Java (JDK 8+)
- ANTLR4 (`antlr4` command available)

## Installing Dependencies

### Java (JDK 8+)

Check if Java is installed:

```bash
java -version
javac -version
```

If not installed:

Ubuntu / Debian
```
sudo apt update
sudo apt install openjdk-8-jdk
```
ANTLR4

Check if installed:
```
antlr4
```
If not installed:
```
Download:
https://www.antlr.org/download/antlr-4.13.1-complete.jar
Create alias:
alias antlr4='java -jar ~/antlr-4.13.1-complete.jar'
```
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

## View Parse Tree
echo "2+3*(2+3)"  | java -cp "$CP:." org.antlr.v4.gui.TestRig styx prog -gui