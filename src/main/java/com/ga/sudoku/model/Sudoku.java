package com.ga.sudoku.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Sudoku {
    public Sudoku(int[][] game){
        Sudoku sudoku = new Sudoku();
        sudoku.setGame(game);
        for(int i = 0; i < game.length; i++){
            if(game.length != game[i].length){
                // raise exception (height should equal width)
            }
            sudoku.setSudokuCells(new ArrayList<>());
            for(int j = 0; j < game[i].length; j++){
                SudokuCell sudokuCell = new SudokuCell();
                sudokuCell.setX(i);
                sudokuCell.setY(j);
                sudokuCell.setValue(game[i][j]);
                sudoku.addCell(sudokuCell);
            }
        }
        sudoku.setSize(game.length);
    }
    @Id
    @Column
    private int id;

    @Column
    private int size;

    //@Column
    //@JsonIgnore
    @ElementCollection
    private int[][] game;

    @Column
    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
    private List<SudokuCell> sudokuCells;

    public void addCell(SudokuCell cell){
        sudokuCells.add(cell);
    }

    @Override
    public String toString(){
        String output = "";
        for(int z = 0; z <= size ; z++){
            output = output.concat("*");
        }
        for(int i = 0; i < game.length; i++){
            output = output.concat("\n");
            for (int j = 0; j < game[i].length; j++){
                output = output.concat("*");
                output = output.concat(game[i][j]+"");
            }
            output = output.concat("*");
            output = output.concat("\n");
            for(int z = 0; z <= size ; z++){
                output = output.concat("*");
            }
        }

        return output;
    }

}
