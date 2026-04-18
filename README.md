# Styx

A boundary between worlds.

Styx is a small compiler that carries code downward, from raw syntax into execution. It ferries programs from high-level expressions into precise, explicit instructions.

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