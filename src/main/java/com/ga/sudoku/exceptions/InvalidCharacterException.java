package com.ga.sudoku.exceptions;

public class InvalidCharacterException extends RuntimeException {
    public InvalidCharacterException(String message) {
        super(message);
    }
}
