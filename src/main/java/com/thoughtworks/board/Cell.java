package com.thoughtworks.board;


public class Cell
{
    private String location;
    private Integer strength; //Hits a cell can endure. ZERO => Cell Destroyed

    public Cell(String location, Integer strength) {
        this.location = location;
        this.strength = strength;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }
}
