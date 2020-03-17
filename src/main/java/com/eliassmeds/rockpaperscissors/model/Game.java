package com.eliassmeds.rockpaperscissors.model;

import com.eliassmeds.rockpaperscissors.gameExceptions.*;
import net.minidev.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class Game {

    private Player p1;
    private Player p2;
    private UUID id;
    public static Map<String, String> rules = new TreeMap<String, String>() {
        {
            put("Rock", "Scissors");
            put("Scissors", "Paper");
            put("Paper", "Rock");
        }
    };

    public Game(Player p1) {
        id = UUID.randomUUID();
        this.p1 = p1;
    }

    public UUID getId() {
        return id;
    }

    public void joinGame(String name) throws GameFullException, NameAlreadyInUseException {
        if (full()) {
            throw new GameFullException();
        }
        if (name.equals(p1.getName())) {
            throw new NameAlreadyInUseException();
        }
        this.p2 = new Player(name);
    }

    public boolean full() {
        return p1 != null && p2 != null;

    }

    public void makeMove(String name, String move) throws GameNotFullException, PlayerNotFoundException, MoveAlreadyMadeException {
        if (!full()) {
            throw new GameNotFullException();
        }
        if (p1.getName().equals(name)) {
            p1.setMove(move);
        } else if (p2.getName().equals(name)) {
            p2.setMove(move);
        } else {
            throw new PlayerNotFoundException();
        }
    }

    public boolean allPlayersHaveMadeMoves() {
        return p1.getMove() != null && p2.getMove() != null;

    }

    public JSONObject getState() {
        JSONObject state = new JSONObject();
        state.put("game", id);
        state.put("player1", p1.getName());
        if (full()) {
            state.put("player2", p2.getName());
            if (allPlayersHaveMadeMoves()) {
                state.put("player1Move", p1.getMove());
                state.put("player2Move", p2.getMove());
                state.put("winner", getWinner());

            }
        }
        return state;
    }

    public String getWinner() {
        if (p2.getMove().equals(rules.get(p1.getMove()))) {
            return p1.getName();
        } else if (p1.getMove().equals(rules.get(p2.getMove()))) {
            return p2.getName();
        } else {
            return "Draw";
        }
    }

}
