package com.eliassmeds.rockpaperscissors.model;

import com.eliassmeds.rockpaperscissors.gameExceptions.MoveAlreadyMadeException;

public class Player {
    private String name;
    private String move;


    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) throws MoveAlreadyMadeException {
        if (this.move == null) {
            this.move = move;
        } else {
            throw new MoveAlreadyMadeException();
        }

    }

}
