# Styx

A boundary between worlds.

Styx is a small compiler that carries code downward, from raw syntax into execution. It sits at the point where text stops being abstract and becomes something precise, enforceable, and real.

## Build
antlr4 -visitor styx.g4
javac -cp "$CP:." *.java
echo "5+2*3+4" | java -cp "$CP:." Driver