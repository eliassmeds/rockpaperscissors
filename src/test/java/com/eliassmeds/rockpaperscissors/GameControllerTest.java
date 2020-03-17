package com.eliassmeds.rockpaperscissors;

import com.eliassmeds.rockpaperscissors.controller.GameController;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameControllerTest {

    @Test
    public void joiningGameWithoutName() {
        GameController controller = new GameController();
        Map<String, String> body = new HashMap<>();
        Assertions.assertThrows(ResponseStatusException.class, () ->
                controller.joinGame(UUID.randomUUID().toString(), body));
    }

    @Test
    public void joiningGameWithBadId() {
        GameController controller = new GameController();
        Map<String, String> body = new HashMap<>();
        body.put("name", "Elias");
        Assertions.assertThrows(ResponseStatusException.class, () ->
                controller.getResult("deadbeef"));
    }

    @Test
    public void makingMoveWithBadArgument1() {
        GameController controller = new GameController();
        Map<String, String> body = new HashMap<>();
        String gameID = UUID.randomUUID().toString();
        Assertions.assertThrows(ResponseStatusException.class, () ->
                controller.move(gameID, body));

    }

    @Test
    public void makingMoveWithBadArgument2() {
        GameController controller = new GameController();
        Map<String, String> body = new HashMap<>();
        String gameID = UUID.randomUUID().toString();
        body.put("name", "");
        body.put("move", "Rock");
        Assertions.assertThrows(ResponseStatusException.class, () ->
                controller.move(gameID, body));

    }

    @Test
    public void makingMoveWithBadArgument3() {
        GameController controller = new GameController();
        Map<String, String> body = new HashMap<>();
        String gameID = UUID.randomUUID().toString();
        body.put("name", "Elias");
        body.put("move", "");
        Assertions.assertThrows(ResponseStatusException.class, () ->
                controller.move(gameID, body));

    }
    @Test
    public void makeMoveWhenItIsAlreadyMade(){
        GameController controller=new GameController();
        Map<String, String >body=new HashMap<>();
        Map<String, String >body2=new HashMap<>();
        body.put("name","Elias");
        body2.put("name","Martina");
        JSONObject id=controller.createGame(body);
        Object game=id.get("game");
        controller.joinGame(game.toString(),body2);
        body.put("move","Rock");
        controller.move(game.toString(),body);
        Assertions.assertThrows(ResponseStatusException.class,()->
                controller.move(game.toString(),body));
    }
}
