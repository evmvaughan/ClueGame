package clueGame.Playables.Services;

import SeedWork.EventBus;
import SeedWork.ISingleton;
import java.util.Random;

import Exceptions.CardCollectionException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import clueGame.Playables.Entities.Card.*;
import clueGame.Playables.Entities.Card.Collection.Hand;
import clueGame.Playables.Entities.Player.*;
import clueGame.Playables.Entities.Player.Guess.Accusation;
import clueGame.Playables.Entities.Player.Guess.Solution;
import clueGame.Playables.Entities.Player.Guess.Suggestion;
import clueGame.Playables.Events.PlayablesInitializedEvent;


public class Dealer implements ISingleton<Dealer> {
	
	private static Dealer instance = new Dealer();
	
	private static PlayerService _playerService;
	private static CardService _cardService;
		

	private Dealer() {
		_playerService = PlayablesServiceCollection.PlayerService;
		_cardService = PlayablesServiceCollection.CardService;
	}
	
	public static Dealer getInstance(){
		   return instance;
	}
	
	////////////////////////
	// Dealer Service API //
	////////////////////////
	public ArrayList<Card> getDeck(){
		return _cardService.getCards();
	}
	
	public int generateRandomCardIndex(int size) {
		int randomUnusedCardIndex = new Random().nextInt(size);
		return randomUnusedCardIndex;
	}
	
	//////////////////////////////////////////////////////
	// Deal a random solution and store in cell storage //
	//////////////////////////////////////////////////////
	public void dealRandomSolution() {
		
		System.out.println("Choosing random solution and dealing cards to players");
		
		ArrayList<Card> weapons = _cardService.getWeapons();
		ArrayList<Card> persons = _cardService.getPersons();
		ArrayList<Card> rooms = _cardService.getRooms();

		int numberOfWeaponCards = weapons.size();
		int numberOfPersonCards = persons.size();
		int numberOfRoomCards = rooms.size();
	
		int randomWeaponIndex = generateRandomCardIndex(numberOfWeaponCards);
		int randomPersonIndex = generateRandomCardIndex(numberOfPersonCards);
		int randomRoomIndex = generateRandomCardIndex(numberOfRoomCards);
		
		Card randomWeapon = weapons.get(randomWeaponIndex);
		Card randomPerson = persons.get(randomPersonIndex);
		Card randomRoom = rooms.get(randomRoomIndex);
		
		randomWeapon.setBeingUsed();
		randomPerson.setBeingUsed();
		randomRoom.setBeingUsed();
		
		try {
			_cardService.createNewSolution(new Solution(randomWeapon, randomPerson, randomRoom));
		} catch (CardCollectionException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//////////////////////////////////////////////////////////
	// Shuffle and deal unique cards to players and publish //
	// a new event to update the game engine and board      //
	//////////////////////////////////////////////////////////
	public void shuffleAndDealCards() {
				
		ArrayList<Player> players = _playerService.getPlayers();
		
		for (Player player : players) {
			
			ArrayList<Card> uniqueCards = new ArrayList<Card>();
			
			for (int cardNumber = 0; cardNumber < 3; cardNumber++) {	// Grab three unused cards from the deck and create a unique list
				ArrayList<Card> unusedDeck = _cardService.getUnusedCards();
				
				int randomCardIndex = generateRandomCardIndex(unusedDeck.size());
				
				Card randomCard = unusedDeck.get(randomCardIndex);
				
				uniqueCards.add(randomCard);
				
				unusedDeck.get(randomCardIndex).setBeingUsed();
			}

			Hand hand = new Hand(uniqueCards.get(0), uniqueCards.get(1), uniqueCards.get(2));
			
			player.updateHand(hand);
			player.setDeckReference(getDeck());
		}
		
		///////////////////////////////////////////////////////////////////////////
		// Publish a New Playables Initialized Event Across ClueGame Sub-domains //
		///////////////////////////////////////////////////////////////////////////
		
		EventBus.getInstance().Publish(new PlayablesInitializedEvent(_playerService.getPlayers(), _cardService.getCards(), _cardService.getSolution()));
	}

	
	//////////////////////////////////////////////////////////////////////////////
	// Recursive suggestion distribution based on current player and suggestion //
	//////////////////////////////////////////////////////////////////////////////
	public Card distributeSuggestion(Suggestion suggestion, Player currentPlayer) {
		
        Iterator<Map.Entry<Integer, Player>> playerIterator = _playerService.getPlayerReal().entrySet().iterator(); 
        int nextPlayerIndex = 0;
        
        while(playerIterator.hasNext()) {
        	
             Map.Entry<Integer, Player> entry = playerIterator.next(); 
             
             if (entry.getValue() == currentPlayer) {
            	 nextPlayerIndex = entry.getKey() + 1;
             }
             
             // Loop index back to zero
             if (nextPlayerIndex == _playerService.getPlayers().size()) {
            	 nextPlayerIndex = 0;
             } 
        }
        
        Player nextPlayer = _playerService.getPlayerReal().get(nextPlayerIndex);
        
        // If we've gone full circle, return null (no one disproved).
        if (nextPlayer.madeSuggestion(suggestion)) {
        	
        	nextPlayer.receiveSuggestionResponse(null);
        	
        	return null;
        }
        
        // Return the first suggestion disproval or continue looping. 
        if (nextPlayer.disproveSuggestion(suggestion) == null) {
        	return distributeSuggestion(suggestion, nextPlayer);
        	
        } else {
        	Card response = nextPlayer.disproveSuggestion(suggestion);
        	
        	// Send the response to the suggestor
        	for (Player player : _playerService.getPlayers()) {
        		if (player.madeSuggestion(suggestion)) player.receiveSuggestionResponse(response);
        	}
        	return response;
        }        
	}
	
	////////////////////////////////////////////////////
	// Check to see if the accusation made is correct //
	////////////////////////////////////////////////////
	public boolean receivedValidAccusation(Accusation accusation) {
		
		Card weapon = accusation.getWeaponCard();
		Card person = accusation.getPersonCard();
		Card room = accusation.getRoomCard();
		
		Card solutionWeapon = _cardService.getSolution().getWeaponCard();
		Card solutionPerson = _cardService.getSolution().getPersonCard();
		Card solutionRoom = _cardService.getSolution().getRoomCard();

		if (weapon == solutionWeapon
				&& person == solutionPerson
				&& room == solutionRoom) {
			return true;
		}
		
		return false;
	}
}