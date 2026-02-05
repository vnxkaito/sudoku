package com.ga.sudoku.model;

import java.util.ArrayList;

public class SudokuSolver{
    public static void main(String[] args) {
        int[][] game = {{1,2,3},{0,3,1},{0,1,2}};
        Sudoku sudoku = new Sudoku(solveSudoku(new Sudoku(game)).getGame());

        System.out.println(sudoku.toString());
    }

    public static Sudoku solveSudoku(Sudoku sudoku){
        Sudoku solvedSudoku = sudoku;
        int iterations = 0;
        int score;
        int[][] game = solvedSudoku.getGame();
        ArrayList<ArrayList<ArrayList<Integer>>> possibleSolutions = new ArrayList<>();
        do {
            score = getScore(game);
            game = solveAllCertain(game);
            if ( score == 0) {
                solvedSudoku.setGame(game);
            }else{
                gameAfterBestMove(game);
            }
            iterations++;
        } while(score != 0 && iterations < 2000);
        return solvedSudoku;
    }

    private static ArrayList<ArrayList<ArrayList<Integer>>> getPossibleValues(int[][] game){
        ArrayList<ArrayList<ArrayList<Integer>>> possibleValues = new ArrayList<>();
        for(int i = 0; i < game.length; i++){
            ArrayList<ArrayList<Integer>> possibleValuesInRow = new ArrayList<>();
            for (int j = 0; j < game[i].length; j++) {
                if (game[i][j] == 0) {
                    ArrayList<Integer> possibleValuesInCell = new ArrayList<>();
                    for (int z = 1; z <= game.length; z++) {
                        boolean notExistHorizontally = true;
                        boolean notExistVertically = true;
                        // horizontal check
                        for (int zs = 0; zs < game.length; zs++) {
                            if (game[zs][j] == z) {
                                notExistHorizontally = false;
                            }
                        }

                        // vertical check
                        for (int zs = 0; zs < game.length; zs++) {
                            if (game[i][zs] == z) {
                                notExistVertically = false;
                            }
                        }

                        if (notExistHorizontally && notExistVertically) {
                            possibleValuesInCell.add(z);
                        }
                    }
                    possibleValuesInRow.add(possibleValuesInCell);
                }
            }
            possibleValues.add(possibleValuesInRow);
        }
        return possibleValues;
    }

    public static ArrayList<SudokuCell> getAllCertain(int[][] game){
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

    public static int[][] solveAllCertain(int[][] game){
        boolean noCertainCells = false;
        int iterations = 0;
        while(!noCertainCells && iterations < 200){
            ArrayList<SudokuCell> certainCells = getAllCertain(game);
            if(!certainCells.isEmpty()){
                for(int i = 0; i < certainCells.size(); i++){
                    game = insertCell(game, certainCells.get(i));
                }
                iterations++;
            }else{
                noCertainCells = true;
            }

        }
        return game;
    }

    public static int[][] insertCell(int[][] game, SudokuCell sudokuCell){
        game[sudokuCell.getX()][sudokuCell.getY()] = sudokuCell.getValue();
        return game;
    }

    public static int getScore(int[][] game){
        int score = 0;

        ArrayList<ArrayList<ArrayList<Integer>>> possibleSolutions = getPossibleValues(game);

        for(int i = 0; i < possibleSolutions.size(); i++){
            for(int j = 0; j < possibleSolutions.get(i).size(); j++){
                score = score + possibleSolutions.get(i).get(j).size();
            }
        }

        return score;

    }

    public static int[][] gameAfterBestMove(int[][] game){
        ArrayList<ArrayList<ArrayList<Integer>>> possibleValues = getPossibleValues(game);
        SudokuCell cellToBeInserted = new SudokuCell();//best movement ( based on breadth first approach )
        int bestScore = 1;
        for(int i = 0; i < possibleValues.size(); i++){
            for(int j = 0; j < possibleValues.get(i).size(); i++){
                for(int z = 0; z < possibleValues.get(i).get(j).size(); z++){
                    int oldValue = game[i][j];
                    int possibleValue = possibleValues.get(i).get(j).get(z);
                    game[i][j] = possibleValue;
                    int scoreAfterMovement = getScore(game);
                    if(scoreAfterMovement == 0){
                        return game; // game is already solved by this move
                    }else if(scoreAfterMovement < bestScore){
                        game[i][j] = oldValue; // this movement doesn't provide a better score
                    }
                }
            }
        }
        return game;
    }





}