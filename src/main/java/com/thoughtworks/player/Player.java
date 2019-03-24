package com.thoughtworks.player;

import com.thoughtworks.board.Board;

import java.util.Queue;

public class Player
{
    private int id;
    private Queue<MissileTarget> missileTargets;
    private Board opponentBoard; //TODO Why Opponent

    public Player(int id,Queue<MissileTarget> missileTargets,Board opponentBoard){
        this.id=id;
        this.missileTargets = missileTargets;
        this.opponentBoard = opponentBoard;
    }

    public int getId() {
        return id;
    }

    public Board getOpponentsBoard() {
        return opponentBoard;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOpponentsBoard(Board opponentsBoard) {
        this.opponentBoard = opponentsBoard;
    }

    public Queue<MissileTarget> getMissileTargets() {
        return missileTargets;
    }

    public void setMissileTargets(Queue<MissileTarget> missileTargets) {
        this.missileTargets = missileTargets;
    }
}
