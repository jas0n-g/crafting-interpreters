#!/usr/bin/env sh

rm -rf jlox Expr.java
java tool/GenerateAst.java .
javac -d . ./*.java

if [ "$1" = "run" ]; then
    shift
    java Lox.java "$@"
fi