#include <stdio.h>
#include "a.h"

int main(int argc, char** argv) {
    printf("Hello, world!\n");
    sayA();

    for (int i = 0; i < argc; i++) {
        printf("arg %d: %s\n", i, argv[i]);
    }

    return 0;
}