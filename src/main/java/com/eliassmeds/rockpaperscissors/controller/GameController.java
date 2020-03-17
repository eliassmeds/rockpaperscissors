package com.eliassmeds.rockpaperscissors.controller;

import com.eliassmeds.rockpaperscissors.gameExceptions.*;
import com.eliassmeds.rockpaperscissors.service.GamesService;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class GameController {
    private GamesService gamesService = new GamesService();

    @RequestMapping("/games/{id}")
    public JSONObject getResult(@PathVariable("id") String id) {
        UUID gameId;
        try {
            gameId = UUID.fromString(id);
            return gamesService.getState(gameId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal gameId format");
        } catch (GameNotFoundException gnfe) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
    }

    @RequestMapping(value = "/games", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject createGame(@RequestBody Map<String, String> body) {
        if (!nameOk(body)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name not provided");
        }
        String name = body.get("name");
        return gamesService.createGame(name);

    }

    @RequestMapping(value = "/games/{id}/join", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void joinGame(@PathVariable("id") String id, @RequestBody Map<String, String> body) {
        UUID gameId;
        if (!nameOk(body)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name not provided");
        }
        try {
            gameId = UUID.fromString(id);
            String name = body.get("name");
            gamesService.joinGame(gameId, name);
        } catch (NameAlreadyInUseException n) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Name is already in use");
        } catch (GameFullException gfe) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game is full");
        } catch (GameNotFoundException gnfe) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game Not found");
        } catch (IllegalArgumentException a) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal gameId format");
        }
    }

    @RequestMapping(value = "/games/{id}/move", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void move(@PathVariable("id") String id, @RequestBody Map<String, String> body) {
        UUID gameId;
        if (!moveOk(body)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Move not provided");
        }
        if (!nameOk(body)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name not provided");

        }
        try {
            gameId = UUID.fromString(id);
            String name = body.get("name");
            String move = body.get("move");
            gamesService.move(gameId, name, move);
        } catch (IllegalArgumentException i) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal gameId format");
        } catch (PlayerNotFoundException pnfe) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        } catch (GameNotFullException gfe) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game is not full");
        } catch (MoveAlreadyMadeException mame) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Move already made");
        }
    }

    public boolean moveOk(Map<String, String> body) {
        return body.containsKey("move") && !body.get("move").isEmpty();

    }

    public boolean nameOk(Map<String, String> body) {
        return body.containsKey("name") && !body.get("name").isEmpty();
    }


}



