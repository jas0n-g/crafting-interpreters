package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.Util.Map;

import static com.craftinginterpreters.lox.TokenType.*;

class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    Scanner (String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // the code here happens when at the start of a lexeme
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            // single character tokens
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': addToken(STAR); break;

            // one or two character tokens
            case '!':
                addToken(match('=') ? BANG_EQUAL : BANG );
                break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;
            case '/':
                // different because slashes start comments
                if (match('/')) {
                    // comments go until the end of a line
                    while (peek() != '\n' && !isAtEnd()) advance;
                } else {
                    addToken(SLASH);
                }
                break;
            
            // whitespace
            case ' ':
            case '\r':
            case '\t':
                // the above whitespace characters are ignored
                break;
            case '\n':
                line++;
                break;

            // unexpected characters
            default:
                Lox.error(line, "Unexpected character.");
                break;
        }
    }

    // helper methods

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    private boolean match(char expected) {
        // only consume a character if it's what we're looking for
        if (isAtEnd() || source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private char peek() {
        // like advance() but doesn't consume a character
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }
}