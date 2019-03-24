package com.thoughtworks.ship;

import com.thoughtworks.board.Cell;

import java.util.List;

public class Ship
{
    private String name;
    private List<Cell> cells;

    public Ship(String name,List<Cell> cells)
    {
        this.name = name;
        this.cells = cells;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }
}
