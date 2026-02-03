package com.ga.sudoku.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SudokuCell {
    @Id
    private int id;

    @Column
    private int x;

    @Column
    private int y;

    @Column
    private int value;
}
