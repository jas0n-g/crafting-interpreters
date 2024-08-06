#/usr/bin/env sh

gcc *.c -o clox

if [ "$1" = "run" ]; then
    shift
    ./clox "$@"
fi