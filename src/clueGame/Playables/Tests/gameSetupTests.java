package clueGame.Playables.Tests;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Exceptions.BadConfigFormatException;
import clueGame.Board.Services.BoardServiceCollection;
import clueGame.Playables.Entities.Card.Card;
import clueGame.Playables.Entities.Card.Collection.Hand;
import clueGame.Playables.Entities.Player.Player;
import clueGame.Playables.Entities.Player.Guess.Solution;
import clueGame.Playables.Services.CardService;
import clueGame.Playables.Services.Dealer;
import clueGame.Playables.Services.PlayablesServiceCollection;

public class gameSetupTests {

	
	@BeforeAll
	public static void setUp() {

		PlayablesServiceCollection.initialize();
		BoardServiceCollection.initialize();
			
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
	public void testPlayerCreation()
	{
		ArrayList<Player> players = PlayablesServiceCollection.PlayerService.getPlayers();
		assertEquals(players.size(), 6);
	}
	@Test
	public void testDeckCreation()
	{
		Dealer dealer = PlayablesServiceCollection.Dealer;
		CardService cardServce = PlayablesServiceCollection.CardService;
		
		ArrayList<Card> deck = dealer.getDeck();
		assertEquals(21, deck.size());
		Solution solution = PlayablesServiceCollection.CardService.getSolution();
		assertEquals(3, solution.getCards().size());
		ArrayList<Player> players = PlayablesServiceCollection.PlayerService.getPlayers();
		assertEquals(players.get(0).getHand().getCards().size(),3);
		assertEquals(players.get(1).getHand().getCards().size(),3);
		assertEquals(players.get(2).getHand().getCards().size(),3);
		assertEquals(players.get(3).getHand().getCards().size(),3);
		assertEquals(players.get(4).getHand().getCards().size(),3);
		assertEquals(players.get(5).getHand().getCards().size(),3);
	}
	
	@Test
	public void testDeckDealout() {
		
		Solution solution = PlayablesServiceCollection.CardService.getSolution();
		assertEquals(oneOfEach(solution.getCards()), true);		//we need to have 1 room, 1 player, 1 weapon
		
		ArrayList<Hand> hands = PlayablesServiceCollection.CardService.getHands();
		ArrayList<Player> players = PlayablesServiceCollection.PlayerService.getPlayers();
		
		for (Player player : players) { assertEquals(3, player.getHand().getCards().size()); } // All players have three cards in their hand
		
		
		for (Hand hand : hands) { // Make sure each card is unique from the solution
			for (Card solCard : solution.getCards()) {
				for (Card handCard : hand.getCards()) {
					assertTrue(solCard != handCard);
				}			
			}
		}
	}
	
	@Test
	public void testBoardAggregation() { // Test that the board entity gets all playables information from event bus
		Solution solution = BoardServiceCollection.BoardService.getSolution();
		ArrayList<Player> players = BoardServiceCollection.BoardService.getPlayers();
		ArrayList<Card> cards =  BoardServiceCollection.BoardService.getCards();
		
		assertEquals(3, solution.getCards().size());
		assertEquals(6, players.size());
		assertEquals(21, cards.size());
	}
	
	
	//////////////////////
	// Utility function //
	//////////////////////
	public boolean oneOfEach(ArrayList<Card> hand) {// Ensure one of each card is present
		boolean ian= false;
		boolean nyan= false;
		boolean liam= false;
		for (int i =0; i<3;i++) {
			switch(hand.get(i).getType()) {
			case ROOM:
				ian =true;
				break;
			case PERSON:
				nyan=true;
				break;
			case WEAPON:
				liam=true;
				break;
			}
		}
		return (ian&&nyan&&liam);

	}
}
