package com.eliassmeds.rockpaperscissors.service;

import com.eliassmeds.rockpaperscissors.gameExceptions.*;
import com.eliassmeds.rockpaperscissors.model.Game;
import com.eliassmeds.rockpaperscissors.model.Player;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.UUID;

public class GamesService {

    private HashMap<UUID, Game> games = new HashMap<>();

    public JSONObject getState(UUID gameId) throws GameNotFoundException {
        if (games.containsKey(gameId)) {
            Game game = games.get(gameId);
            return game.getState();
        }
        throw new GameNotFoundException();
    }

    public JSONObject createGame(String name) {
        Game game = new Game(new Player(name));
        games.put(game.getId(), game);
        JSONObject gameId = new JSONObject();
        gameId.put("game", game.getId());
        return gameId;
    }

    public void joinGame(UUID gameId, String name) throws NameAlreadyInUseException, GameFullException, GameNotFoundException {
        if (games.containsKey(gameId)) {
            Game game = games.get(gameId);
            game.joinGame(name);
        } else {
            throw new GameNotFoundException();
        }
    }

    public void move(UUID gameId, String name, String move) throws GameNotFullException, PlayerNotFoundException, MoveAlreadyMadeException {
        if (games.containsKey(gameId)) {
            Game game = games.get(gameId);
            game.makeMove(name, move);

        }
    }
}

