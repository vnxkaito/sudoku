package com.ga.sudoku.model;

import java.util.ArrayList;

public class SudokuSolver{
    public Sudoku solveSudoku(Sudoku sudoku){
        Sudoku solvedSudoku = sudoku;
        int[][] game = solvedSudoku.getGame();
        ArrayList<ArrayList<ArrayList<Integer>>> possibleSolutions = new ArrayList<>();

        solvedSudoku.setGame(game);

        return solvedSudoku;
    }
    private ArrayList<ArrayList<ArrayList<Integer>>> getPossibleValues(int[][] game){
        ArrayList<ArrayList<ArrayList<Integer>>> possibleValues = new ArrayList<>();
        for(int i = 0; i < game.length; i++){
            ArrayList<ArrayList<Integer>> possibleValuesInRow = new ArrayList<>();
            for (int j = 0; j < game[i].length; j++){
                ArrayList<Integer> possibleValuesInCell = new ArrayList<>();

                for(int z = 1; z <= game.length; z++){
                    boolean notExistHorizontally = true;
                    boolean notExistVertically   = true;

                    // horizontal check
                    for(int zs = 0; zs < game.length; zs++){
                        if(game[zs][j] == z){
                            notExistHorizontally = false;
                        }
                    }

                    // vertical check
                    for(int zs = 0; zs < game.length; zs++){
                        if(game[i][zs] == z){
                            notExistVertically = false;
                        }
                    }

                    if(!notExistHorizontally && !notExistVertically){
                        possibleValuesInCell.add(z);
                    }

                    possibleValuesInRow.add(possibleValuesInCell);
                }
                possibleValues.add(possibleValuesInRow);
            }
        }
        return possibleValues;
    }

    public ArrayList<SudokuCell> getAllCertain(int[][] game){
        ArrayList<ArrayList<ArrayList<Integer>>> possibleSolutions = getPossibleValues(game);
        ArrayList<SudokuCell> allCertain = new ArrayList<>();

        for(int i = 0; i < possibleSolutions.size(); i++){
            for(int j = 0; j < possibleSolutions.get(i).size(); j++){
                if(possibleSolutions.get(i).get(j).size() == 1){
                    SudokuCell sudokuCell = new SudokuCell();
                    sudokuCell.setX(i);
                    sudokuCell.setY(j);
                    sudokuCell.setValue(possibleSolutions.get(i).get(j).get(0));
                    allCertain.add(sudokuCell);
                }
            }
        }
        return allCertain;
    }

    public int[][] solveAllCertain(int[][] game){
        ArrayList<SudokuCell> certainCells = getAllCertain(game);
        for(int i = 0; i < certainCells.size(); i++){
            game = insertCell(game, certainCells.get(i));
        }
        return game;
    }

    public int[][] insertCell(int[][] game, SudokuCell sudokuCell){
        game[sudokuCell.getX()][sudokuCell.getY()] = sudokuCell.getValue();
        return game;
    }

    public int getScore(int[][] game){
        int score = 0;

        ArrayList<ArrayList<ArrayList<Integer>>> possibleSolutions = getPossibleValues(game);

        for(int i = 0; i < possibleSolutions.size(); i++){
            for(int j = 0; j < possibleSolutions.get(i).size(); j++){
                score = score + possibleSolutions.get(i).get(j).size();
            }
        }

        return score;

    }



}