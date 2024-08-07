#!/usr/bin/env sh

rm -rf jlox
javac -d . *.java

if [ "$1" = "run" ]; then
    shift
    java Lox.java "$@"
fi