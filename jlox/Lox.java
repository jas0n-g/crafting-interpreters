package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
    static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    // runFile() and runPrompt() are wrappers around run()

    // this reads from a file
    private static void runFile(String path) throws IOException {
        // pass the entire file into run()
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));

        // if there is an error
        if (hadError) System.exit(65);
    }

    // this is the REPL
    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        // take in user input continuously and evaluate the code
        for (;;) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);

            // because the REPL is interactive, this should reset the error
            hadError = false;
        }
    }

    // we'll implement the Scanner and Token classes later
    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        // for now, just print out tokens
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    // error handling
    static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.err.println("[line " + line "] Error" + where + ": " + message);
        hadError = true;
    }
}