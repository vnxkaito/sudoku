package com.ga.sudoku.exceptions;

public class SudokuFileNotFoundException extends RuntimeException {
    public SudokuFileNotFoundException(String message) {
        super(message);
    }
}
