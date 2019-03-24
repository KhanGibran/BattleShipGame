package com.thoughtworks.game;

import com.thoughtworks.board.Board;
import com.thoughtworks.board.Cell;
import com.thoughtworks.constant.BattleShipConstant;
import com.thoughtworks.player.MissileTarget;
import com.thoughtworks.player.Player;
import com.thoughtworks.ship.Ship;
import com.thoughtworks.util.InputFileUtil;

import java.util.*;

import static com.thoughtworks.constant.BattleShipConstant.*;

public class Game
{
    private Player[] players;

    public Game(){

        Character height = InputFileUtil.getHeightOfBattleField();
        Integer width = InputFileUtil.getWidthOfBattleField();

        Board[] boards = new Board[]{
                new Board(1,height,width,getShips(1)),
                new Board(2,height,width,getShips(2)),
        };

        // For player 1, target is board 2 and vice versa.
        this.players = new Player[]{
                new Player(1,initializeMissileTargets(1),boards[1]),
                new Player(2,initializeMissileTargets(2),boards[0]),
        };
    }

    public void startGame()
    {
        Player playerInAction = players[0];

        do{
            if(players[0].getOpponentsBoard().isEmpty() || players[1].getOpponentsBoard().isEmpty())
                break;
            else if(players[0].getMissileTargets().isEmpty() && players[1].getMissileTargets().isEmpty())
                break;
            playerInAction=action(playerInAction);
        }while(true);

        String  battleResult;
        if(players[0].getOpponentsBoard().isEmpty()==true)
            battleResult =  "Player-1 " + BattleShipConstant.WON;
        else if(players[1].getOpponentsBoard().isEmpty() == true)
            battleResult =  "Player-2 " + BattleShipConstant.WON;
        else
            battleResult = BattleShipConstant.DRAW;

        System.out.println(battleResult);
    }

    /**
     * The player in action gets opponents board and then attack with the next
     * missile with target from the queue. And if successful hit then returns
     * same player else if there is a miss returns another player.
     *
     * @param player
     * @return
     */

     public Player action(Player player)
     {
         boolean isHit;
         if(!player.getMissileTargets().isEmpty())
         {
             //Get attack location field.And removes it from the queue and update the value of MissileTarget
             Queue<MissileTarget> targets = player.getMissileTargets();
             MissileTarget missileTarget = targets.remove();
             player.setMissileTargets(targets);

             //Hits the target and checks if it was a hit or miss.
             isHit = player.getOpponentsBoard().attack(missileTarget.getField());

             String attackResult = isHit ? BattleShipConstant.HIT : BattleShipConstant.MISS;

             String output = "Player-" + player.getId() + " fires a missile" +
                     " with target " + missileTarget.getField() + " which got " + attackResult;

             System.out.println(output);
         }
         else
         {
            String output = "Player-" + player.getId() + " has no more missiles left to launch";
            System.out.println(output);
            isHit=false;
         }

        if(isHit)
            return player;
        else
        {
            Integer nextPlayerId = player.getId() == 1 ? 2 : 1;
            return players[nextPlayerId-1];
        }
     }
    /**
     * Returns the list of ships of a player
     *
     * @param playerId
     * @return
     */
    public List<Ship> getShips(Integer playerId)
    {
        List<Ship> ships = new ArrayList<>();

        List<Map<String,String>> listMapOfShipData = InputFileUtil.getListOfShipDataMap();

        String type;
        Integer height;
        Integer width;
        String p1StartIndex;
        String p2StartIndex;

        for (Map<String,String> map:listMapOfShipData)
        {
            type = map.get(TYPE);
            height = Integer.parseInt(map.get(HEIGHT));
            width = Integer.parseInt(map.get(WIDTH));
            p1StartIndex = map.get(P1_START_INDEX);
            p2StartIndex = map.get(P2_START_INDEX);

            if(playerId==1)
                ships.add(new Ship(type,getCells(height,width,p1StartIndex,type)));
            else if(playerId==2)
                ships.add(new Ship(type,getCells(height,width,p2StartIndex,type)));
        }
        return ships;
    }

    /**
     * Returns list of all cells for a ship based on its dimensions and
     * initial cell location on board.
     *
     * @param height
     * @param width
     * @param startIndex
     * @return
     */
    public List<Cell> getCells(Integer height,Integer width, String startIndex, String type)
    {
        List<Cell> cells = new ArrayList<>();

        Character originalRow = startIndex.charAt(0);
        Character row = originalRow;

        Integer  originalColumn = Integer.parseInt(startIndex.substring(1, 2));
        Integer column = originalColumn;

        String location;
        Integer strength = ShipType.getStrengthByType(type);

        for(int i=0;i<height;i++)
        {
            for(int j=0;j<width;j++)
            {
                location = new StringBuilder().append(row).append(column).toString();
                cells.add(new Cell(location,strength));
                column++;
            }
            column = originalColumn;
            row++;
        }
        row = originalRow;
        return cells;
    }

    /**
     * Returns queue of missile targets for a specified player for opponents board
     *
     * @param playerId
     * @return
     */
    public Queue<MissileTarget> initializeMissileTargets(Integer playerId) {

        Queue<MissileTarget> missileTargets = new LinkedList<>();

        for(String targetField : InputFileUtil.getTargetsForPlayer(playerId)) {
            missileTargets.add(new MissileTarget(targetField));
        }

        return missileTargets;
    }
}
