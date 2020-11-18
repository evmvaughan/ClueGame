package ClueGame.Playables.Tests;

import static org.junit.Assert.*;


import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ClueGame.Board.Services.BoardServiceCollection;
import ClueGame.Playables.Entities.Card.Card;
import ClueGame.Playables.Entities.Player.ComputerPlayer;
import ClueGame.Playables.Entities.Player.LocationDTO;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Entities.Player.Guess.Accusation;
import ClueGame.Playables.Entities.Player.Guess.Solution;
import ClueGame.Playables.Entities.Player.Guess.Suggestion;
import ClueGame.Playables.Services.PlayablesServiceCollection;
import Exceptions.BadConfigFormatException;
import Exceptions.PlayerSuggestionNotInRoomException;
import SeedWork.EventBus;

public class GameSolutionTest {
	@BeforeAll
	public static void setUp() {
		PlayablesServiceCollection.initialize();
		BoardServiceCollection.initialize();
		EventBus.getInstance();
		try {
			PlayablesServiceCollection.InitConfigurationService.initializePayables("ClueSetup.txt");
			
			PlayablesServiceCollection.Dealer.dealRandomSolution();
			PlayablesServiceCollection.Dealer.shuffleAndDealCards();
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	@Test
	public void checkAccusation()
	{
		ArrayList<Player> players = PlayablesServiceCollection.PlayerService.getPlayers();
		Solution solution = PlayablesServiceCollection.CardService.getSolution();
		
		Card wrongWeapon = PlayablesServiceCollection.CardService.getWeapons().get(0);
		Card wrongPerson = PlayablesServiceCollection.CardService.getPersons().get(0);
		Card wrongRoom = PlayablesServiceCollection.CardService.getRooms().get(0);
		
		// Make sure they are, in fact, wrong.
		if (wrongWeapon == solution.getWeaponCard())
			wrongWeapon = PlayablesServiceCollection.CardService.getWeapons().get(1);
		if (wrongPerson == solution.getPersonCard())
			wrongPerson = PlayablesServiceCollection.CardService.getPersons().get(1);
		if (wrongRoom == solution.getRoomCard())
			wrongRoom = PlayablesServiceCollection.CardService.getRooms().get(1);
		
		// Correct Accusation
		Accusation accusation = players.get(0).makeAccusation(solution.getWeaponCard(), solution.getPersonCard(), solution.getRoomCard());
		assertTrue(PlayablesServiceCollection.Dealer.receivedValidAccusation(accusation));

		// Incorrect Accusations
		accusation = players.get(0).makeAccusation(wrongWeapon, solution.getPersonCard(), solution.getRoomCard());
		assertTrue(!PlayablesServiceCollection.Dealer.receivedValidAccusation(accusation));
		
		accusation = players.get(0).makeAccusation(solution.getWeaponCard(), wrongPerson, solution.getRoomCard());
		assertTrue(!PlayablesServiceCollection.Dealer.receivedValidAccusation(accusation));
		
		accusation = players.get(0).makeAccusation(solution.getWeaponCard(), solution.getPersonCard(), wrongRoom);
		assertTrue(!PlayablesServiceCollection.Dealer.receivedValidAccusation(accusation));
	}
	@Test
	public void disproveSuggestions()
	{
		
		ArrayList<Player> players = PlayablesServiceCollection.PlayerService.getPlayers();
		
		Card weapon = PlayablesServiceCollection.CardService.getWeapons().get(0);
		Card wrongWeapon = PlayablesServiceCollection.CardService.getWeapons().get(1);
		Card person = PlayablesServiceCollection.CardService.getPersons().get(0);
		Card wrongPerson = PlayablesServiceCollection.CardService.getPersons().get(1);
		Card room = PlayablesServiceCollection.CardService.getRooms().get(0);
		Card wrongRoom = PlayablesServiceCollection.CardService.getRooms().get(1);
		
		Player suggestor = players.get(0);
		Player disproveSingle = players.get(1);
		Player disproveDouble = players.get(2);
		Player disproveNone = players.get(3);

		disproveSingle.getHand().setCardAtIndex(room, 0);
		disproveSingle.getHand().setCardAtIndex(wrongWeapon, 1);
		disproveSingle.getHand().setCardAtIndex(wrongPerson, 2);
		
		disproveDouble.getHand().setCardAtIndex(weapon, 0);
		disproveDouble.getHand().setCardAtIndex(person, 1);
		disproveDouble.getHand().setCardAtIndex(wrongRoom, 2);

		disproveNone.getHand().setCardAtIndex(wrongRoom, 0);
		disproveNone.getHand().setCardAtIndex(wrongWeapon, 1);
		disproveNone.getHand().setCardAtIndex(wrongPerson, 2);

		suggestor.updateLocation(new LocationDTO(room.getName(), 0, 0));
		
		try {
			
			Suggestion suggestion = suggestor.makeSuggestion(weapon, person, room);
			
			// Return single disproval card
			assertTrue(disproveSingle.disproveSuggestion(suggestion) == room);
			
			// Return random double disproval card	
			Card disproval = disproveDouble.disproveSuggestion(suggestion);
			assertTrue(disproval == weapon || disproval == person);
			
			// Return no disproval cards
			assertTrue(disproveNone.disproveSuggestion(suggestion) == null);
			
		} catch (PlayerSuggestionNotInRoomException e) {
			System.out.println(e.getMessage());
		}
		
	}
	@Test
	public void handleSuggestions()
	{
		
		ArrayList<Player> players = PlayablesServiceCollection.PlayerService.getPlayers();
		
		Card weapon = PlayablesServiceCollection.CardService.getWeapons().get(0);
		Card wrongWeapon = PlayablesServiceCollection.CardService.getWeapons().get(1);
		Card person = PlayablesServiceCollection.CardService.getPersons().get(0);
		Card wrongPerson = PlayablesServiceCollection.CardService.getPersons().get(1);
		Card room = PlayablesServiceCollection.CardService.getRooms().get(0);
		Card wrongRoom = PlayablesServiceCollection.CardService.getRooms().get(1);
		
		// Set incorrect cards for all players. 
		for (Player player : players) {
			player.getHand().setCardAtIndex(wrongRoom, 0);
			player.getHand().setCardAtIndex(wrongWeapon, 1);
			player.getHand().setCardAtIndex(wrongPerson, 2);
		}
		
		Player suggestor = players.get(3);
		suggestor.getHand().setCardAtIndex(room, 0);
		suggestor.getHand().setCardAtIndex(weapon, 1);
		suggestor.getHand().setCardAtIndex(person, 2);
		
		suggestor.updateLocation(new LocationDTO(room.getName(), 0, 0));
		
		try {
			
			Suggestion suggestion = suggestor.makeSuggestion(weapon, person, room);
			
			// All players have wrong cards (Except for the suggestor). Disproval returns null.
			Card disproval = PlayablesServiceCollection.Dealer.distributeSuggestion(suggestion, suggestor);
			assertTrue(disproval == null);
			
			// Two players are given correct cards. The first card disproved is returned.
			players.get(0).getHand().setCardAtIndex(room, 0);
			players.get(5).getHand().setCardAtIndex(weapon, 0);
			
			disproval = PlayablesServiceCollection.Dealer.distributeSuggestion(suggestion, suggestor);
			assertTrue(disproval == weapon);
			
		} catch (PlayerSuggestionNotInRoomException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testSuggestionCreation() {
		
		ArrayList<Player> players = PlayablesServiceCollection.PlayerService.getPlayers();
		
		ArrayList<Card> allButOneWeapons = PlayablesServiceCollection.CardService.getWeapons();
		allButOneWeapons.remove(0); // Remove Knife
		
		Card knife = PlayablesServiceCollection.CardService.getWeapons().get(0);
		Card gun = PlayablesServiceCollection.CardService.getWeapons().get(1);
		Card person = PlayablesServiceCollection.CardService.getPersons().get(0);
		Card room = PlayablesServiceCollection.CardService.getRooms().get(0);
		Card wrongRoom = PlayablesServiceCollection.CardService.getRooms().get(1);

		Player suggestor = players.get(3);
		
		// Suggestor must be in same room
		assertThrows(PlayerSuggestionNotInRoomException.class, () -> {
	
			suggestor.updateLocation(new LocationDTO(wrongRoom.getName(), 0, 0));
			
			@SuppressWarnings("unused")
			Suggestion suggestion = suggestor.makeSuggestion(knife, person, room);
			
		});
		
		ComputerPlayer NPC = (ComputerPlayer)(players.get(0));
		NPC.updateLocation(new LocationDTO(room.getName(), 0, 0));
				
		NPC.setSeenCards(allButOneWeapons);
		
		Suggestion randomSuggestion;
		
		try {
			randomSuggestion = NPC.makeSuggestion();
			
			assertTrue(randomSuggestion.getCardAtIndex(0) == knife); // Can only suggest knife
			
			allButOneWeapons.remove(0); // Remove gun as well
			
			NPC.setSeenCards(allButOneWeapons);
			
			randomSuggestion = NPC.makeSuggestion();

			Card response = randomSuggestion.getCardAtIndex(0);
			assertTrue(response == knife || response == gun); // Can only suggest knife or gun

		} catch (PlayerSuggestionNotInRoomException e) {
			System.out.println("Player not in room!");
		}
	}
}
