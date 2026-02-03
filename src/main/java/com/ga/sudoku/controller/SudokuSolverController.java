package com.ga.sudoku.controller;

import com.ga.sudoku.IO.SudokuCsvIO;
import com.ga.sudoku.model.Sudoku;
import com.ga.sudoku.model.SudokuSolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/sudoku")
public class SudokuSolverController {
    @GetMapping("/{gameName}")
    public Sudoku getGame(@PathVariable("gameName") String gameName){
        System.out.println("Controller calling getGame==>");
        gameName = gameName.concat(".csv");
        try{
            return new Sudoku(SudokuCsvIO.readSquareGrid(
                    Path.of(SudokuCsvIO.rootPath+gameName)));
        }catch (IOException e){
            return null;
            // handle invalid game name
        }
    }

    @GetMapping("/{gameName}/solve")
    public Sudoku getSolution(@PathVariable("gameName") String gameName){
        System.out.println("Controller calling getGame==>");
        gameName = gameName.concat(".csv");

        SudokuSolver solver = new SudokuSolver();
        try{
            Sudoku sudoku = new Sudoku(SudokuCsvIO.readSquareGrid(
                    Path.of(SudokuCsvIO.rootPath+gameName)));

            Sudoku solvedSudoku = solver.solveSudoku(sudoku);

            SudokuCsvIO.saveSquareGrid(
                    Path.of(SudokuCsvIO.rootPath+gameName),
                    solvedSudoku.getGame());

            return solvedSudoku;

        }catch (IOException e){
            return null;
            // handle invalid game name
        }

    }
}
