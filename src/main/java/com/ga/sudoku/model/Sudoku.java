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
    @Id
    @Column
    private int id;

    @Column
    private int size;

    @Column
    @JsonIgnore
    private int[][] game;

    @Column
    @OneToMany(mappedBy = "sudokuCellId", fetch = FetchType.EAGER)
    private List<SudokuCell> sudokuCells;

}
