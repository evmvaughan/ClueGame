package ClueGame.GameEngine.Tests;

import static org.junit.Assert.*;


import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ClueGame.Board.Services.BoardServiceCollection;
import ClueGame.GameEngine.GameEngine;
import ClueGame.Playables.Entities.Card.Card;
import ClueGame.Playables.Entities.Player.ComputerPlayer;
import ClueGame.Playables.Entities.Player.HumanPlayer;
import ClueGame.Playables.Entities.Player.LocationDTO;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Entities.Player.Guess.Accusation;
import ClueGame.Playables.Entities.Player.Guess.Solution;
import ClueGame.Playables.Entities.Player.Guess.Suggestion;
import ClueGame.Playables.Services.PlayablesServiceCollection;
import Exceptions.BadConfigFormatException;
import Exceptions.PlayerSuggestionNotInRoomException;
import SeedWork.EventBus;

public class PlayerSuggestionTest {
	@BeforeAll
	public static void setUp() {

		GameEngine gameEngine = GameEngine.getInstance();
		
		gameEngine.initializeAll();
		
	}

	@Test
	public void HandleSuggestionMovingPlayers()
	{
		
		ArrayList<Player> players = PlayablesServiceCollection.PlayerService.getPlayers();
		
		Card weapon = PlayablesServiceCollection.CardService.getWeapons().get(0);
		Card person = PlayablesServiceCollection.CardService.getPersons().get(0);
		Card room = PlayablesServiceCollection.CardService.getRooms().get(0);
		
		HumanPlayer suggestor = PlayablesServiceCollection.PlayerService.getHumanPlayer();
		
		suggestor.updateLocation(new LocationDTO(room.getName(), 0, 0));

		try {
			Suggestion suggestion = suggestor.makeSuggestion(weapon, person, room);
						
			int playersInSuggestionRoom = 0;
			
			for (Player player : players) {
				if (player.getLocation().getCurrentRoom() == room.getName()) {
					playersInSuggestionRoom += 1;
				}
			}
			
			assertEquals(2, playersInSuggestionRoom);
			
		} catch (PlayerSuggestionNotInRoomException e) {
			System.out.println(e.getMessage());
		}
	}

}
