package com.thoughtworks.board;

import com.thoughtworks.constant.BattleShipConstant;
import com.thoughtworks.exception.BattleShipException;
import com.thoughtworks.ship.Ship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Board is the battle place of a player.
 * We logically divide it into Fields (intersection of a Row & a Column)
 *
 * @Author Gibran M Khan
 */

public class Board
{
    Integer id;
    Integer height;       // Number of height or rows [A,Z] char
    Integer width;    // Number of width or columns [1,9] int

    // Stores fields location name
    List<String> emptyFields = new ArrayList<>();

    // Store fields location name with the ships' cells' strength there.
    Map<String, Integer> occupiedFields = new HashMap<>();

    /**
     * Initializes the empty board's dimensions and assign each field a name
     * And then adds that field name to the empty fields list.
     *
     * @param height
     * @param width
     * @param id
     * @return Board
     */
    private Board(Integer id,Character height,Integer width ) // row=E col=5
    {
        this.id = id;
        this.height = (int)height-65+1;   // Height is represented by alphabet
        this.width = width;        // Width is represented by number

        for(Integer row = 0; row <= this.height-1; row++){
            for(Integer column = 1; column <= this.width; column++ ){
                int integer = row+65;
                char character = (char)integer;
                emptyFields.add(Character.toString(character).concat(Integer.toString(column)));
            }
        }
    }

    /**
     *  Places the Ships at its locations
     *
     * @param id
     * @param height
     * @param width
     * @param ships
     */
    public Board(Integer id,Character height,Integer width,List<Ship> ships )
    {
        // Initialize empty board first by calling overloaded private constructor.
        this(id,height,width);

        // Get ships and their cells and put those into empty location
        // by removing empty fields from list and adding cell's location
        for (Ship ship: ships){
            for (Cell cell:ship.getCells()) {

                // Check whether a field was already taken by another cell of a ship
                if(!emptyFields.contains(cell.getLocation()))
                    throw new BattleShipException(BattleShipConstant.Error.SHIPS_OVERLAPPING.getMessage());

                //Remove this location from empty fields in order to place a ship's cell there.
                emptyFields.remove(cell.getLocation());

                // Place a cell at the location
                // i.e. Associate the location and strength of ship's cell with board's field
                occupiedFields.put(cell.getLocation(),cell.getStrength());
            }
        }
    }

    /**
     * Attacks a field of board if hit then returns true else false
     * @param location
     * @return boolean
     */
    public boolean attack(String location){

        boolean isHit = false;

        // If field is occupied by any ships' cell. And cell is not yet destroyed
        if(occupiedFields.containsKey(location) && occupiedFields.get(location) > 0){

            // If the strength before hit was 1. It will be destroyed.
            // Hence cell should be removed from occupied fields.
            if(occupiedFields.get(location) == 1)
                emptyFields.add((location));

            // An attack decreases the strength of cell by 1.
            occupiedFields.put(location, occupiedFields.get(location) - 1);

            isHit = true;
        }

        return isHit;
    }
    /**
     * Checks if the board is empty from ships
     *
     * @return boolean
     */
    public boolean isEmpty(){
        return emptyFields.size() == totalFieldSize();
    }

    /**
     * Finds the total number of fields at the Board
     * @return Integer
     */
    public Integer totalFieldSize(){
        return height * width;
    }
}
