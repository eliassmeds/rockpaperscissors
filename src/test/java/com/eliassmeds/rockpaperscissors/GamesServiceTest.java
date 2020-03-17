package com.eliassmeds.rockpaperscissors;
import com.eliassmeds.rockpaperscissors.gameExceptions.GameNotFoundException;
import com.eliassmeds.rockpaperscissors.service.GamesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class GamesServiceTest {

    @Test
    public void joiningGameThatDoesNotExsist(){
        GamesService controller=new GamesService();
        UUID id= UUID.randomUUID();
        Assertions.assertThrows(GameNotFoundException.class, ()->
                controller.joinGame(id,"Elias"));
    }

}
