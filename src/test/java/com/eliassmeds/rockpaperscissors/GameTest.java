package com.eliassmeds.rockpaperscissors;


import com.eliassmeds.rockpaperscissors.gameExceptions.*;
import com.eliassmeds.rockpaperscissors.model.Game;
import com.eliassmeds.rockpaperscissors.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class GameTest {


    @Test
    public void joinAlreadyFullGame() throws GameFullException, NameAlreadyInUseException {
        Game game = new Game(new Player("Elias"));
        game.joinGame("Lovisa");
        Assertions.assertThrows(GameFullException.class, () ->
                game.joinGame("Fredrik"));

    }

    @Test
    public void makeMoveInGameBeforePlayerTwoHasJoined() {
        Game game = new Game(new Player("Elias"));
        Assertions.assertThrows(GameNotFullException.class, () ->
                game.makeMove("Elias", "Rock"));

    }

    @Test
    public void makeMoveOnPlayerNotInGame() throws NameAlreadyInUseException, GameFullException {
        Game game = new Game(new Player("Elias"));
        game.joinGame("Lovisa");
        Assertions.assertThrows(PlayerNotFoundException.class, () ->
                game.makeMove("Fredrik", "Rock"));
    }

    @Test
    public void testPerfectGameScenario() throws GameFullException, NameAlreadyInUseException, GameNotFullException,
            PlayerNotFoundException, MoveAlreadyMadeException {
        Game game = new Game(new Player("Elias"));
        game.joinGame("Fredrik");
        game.makeMove("Elias", "Rock");
        game.makeMove("Fredrik", "Paper");

        Assertions.assertSame("Fredrik", game.getWinner());

    }

    @Test
    public void joinGameWithNameAlreadyTaken() {
        Game game = new Game(new Player("Elias"));
        Assertions.assertThrows(NameAlreadyInUseException.class, () ->
                game.joinGame("Elias"));

    }


}
