package com.ga.sudoku.IO;

import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class SudokuCsvIO {
    static String rootPath = "C:\\Users\\alial\\jdb\\projects\\sudoku_project\\sudoku\\games\\";
    public static int[][] readSquareGrid(Path csvPath)  throws IOException {
        java.util.List<int[]> rows = new java.util.ArrayList<>();
        Integer n = null;

        try (var br = Files.newBufferedReader(csvPath)) {
            String line;
            while ((line = br.readLine()) != null){
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")){
                    continue;
                }

                String[] parts = line.split("\\s*[,;]\\s*");

                if (n == null) {
                    n = parts.length;
                    if (n <= 0) {
                        throw new IllegalArgumentException("Could not detect grid size.");
                    }
                } else if (parts.length != n){
                    throw new IllegalArgumentException("Inconsistent columns. Expected "
                    + n + " but got " + parts.length + " at row " + (rows.size() + 1));
                }

                int[] row = new int[n];
                for (int i = 0; i < n; i++){
                    String cell = parts[i].trim();
                    if(cell.isEmpty() || cell.equals(".") || cell.equals("0")){
                        row[i] = 0;
                        continue;
                    }

                    int val;
                    try{
                        val = Integer.parseInt(cell);
                    } catch(NumberFormatException e){
                        throw new IllegalArgumentException(
                                "Invalid number '" + cell + "' at row " + (rows.size() + 1) + ", col " + (i+1)
                        );
                    }

                    if (val < 0 || val > n) {
                        throw new IllegalArgumentException(
                                "Value out of range at row " + (rows.size() + 1) + ", col" + (i + 1)
                            + ": " + val + " (allowed 0.." + n +")"
                        );
                    }

                    row[i] = val;
                }

                rows.add(row);
            }
        }

        if ( n == null ) {
            throw new IllegalArgumentException("No data rows found.");
        }

        if(rows.size() != n) {
            throw new IllegalArgumentException("Grid must be square (NxN). Columns=" + n + ", rows" + rows.size());
        }

        int[][] grid = new int[n][n];
        for(int r = 0; r < n; r++){
            grid[r] = rows.get(r);
        }
        return grid;
    }

    public static void saveSquareGrid(Path csvPath, int[][] grid) throws IOException {
        //validateSquareGrid(grid);

        int n = grid.length;
        try(BufferedWriter bw = Files.newBufferedWriter(csvPath)){
            for(int r = 0; r < n ; r++){
                for(int c = 0; c < n ; c++){
                    if(c > 0){
                        bw.write(",");
                    }
                    bw.write(Integer.toString(grid[r][c]));
                }
                bw.newLine();
            }
        }
    }

    private static void validateSquareGrid(int[][] grid){
        if (grid == null){
            throw new IllegalArgumentException("grid is null");
        }
        if (grid.length == 0){
            throw new IllegalArgumentException("grid has 0 rows");
        }

        int n = grid.length;
        for(int r = 0; r < n; r++){
            if(grid[r] == null){
                throw new IllegalArgumentException("grid row " + r + " is null");
            }
            if(grid[r].length != n){
                throw new IllegalArgumentException(
                        "Grid must be square. Row " + r + " length=" + grid[r].length + ", excepted " + n
                );
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Path in = Path.of((rootPath + "sudoku.csv"));
        int[][] grid = readSquareGrid(in);
        System.out.println(grid.length + ", " + grid[0].length);
        grid[0][0] = 9;
        saveSquareGrid(Path.of(rootPath + "sudokuUpdated.csv"), grid);
    }
}
