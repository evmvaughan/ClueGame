package clueGame.Playables.Entities.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import Exceptions.PlayerSuggestionNotInRoomException;
import SeedWork.EventBus;
import clueGame.Board.Entities.Cell.BoardCell;
import clueGame.Board.Entities.Room.Room;
import clueGame.Playables.Entities.Card.Card;
import clueGame.Playables.Entities.Card.CardType;
import clueGame.Playables.Entities.Card.Collection.Hand;
import clueGame.Playables.Entities.Player.Guess.Suggestion;
import clueGame.Playables.Events.PlayerMovedToSelectedTargetEvent;
import clueGame.Playables.Events.PlayerSelectingTargetsEvent;

public class ComputerPlayer extends Player {
	
	private ArrayList<Card> _seenCards;
	
	private BoardCell _target;

	private BoardCell _targetCell;
	
	private LocationDTO _targetLocation;

	public ComputerPlayer(String playerName, String playerID) {
		super(playerName, playerID);

		_seenCards = new ArrayList<Card>();
		
	}
	
	@Override
	public void updateHand(Hand hand) {
		
		_seenCards.add(hand.getCardAtIndex(0));
		_seenCards.add(hand.getCardAtIndex(1));
		_seenCards.add(hand.getCardAtIndex(2));

		_hand = hand;
	}
	
	@Override
	public void receiveSuggestionResponse(Card response) {
		if (response != null) _seenCards.add(response);
		super.receiveSuggestionResponse(response);
	}

	public void setSeenCards(ArrayList<Card> cards) {
		_seenCards = cards;
	}

	public void selectTargetFromRollOf(int step) {
		EventBus.getInstance().Publish(new PlayerSelectingTargetsEvent(this, step));
	}

	public void setTarget(BoardCell target, Room room) {
		_targetLocation = new LocationDTO(room.getName(), target.getRow(), target.getColumn());
	}

	public LocationDTO moveToTarget() {
						
		super.updateLocation(_targetLocation);
		
		EventBus.getInstance().Publish(new PlayerMovedToSelectedTargetEvent(this, _currentLocation));
		
		return _currentLocation;
	}
	
	public Suggestion makeSuggestion() throws PlayerSuggestionNotInRoomException {
			
		ArrayList<Card> unseenCards = new ArrayList<Card>();
		Card room = null;
		
		boolean seen = false;
		
		for (Card card : _deckReference) {
			
			for (Card seenCard : _seenCards) {
				
				if (seenCard == card) seen = true;
			}
			if (!seen) unseenCards.add(card);
			seen = false;
			
			if (card.getName() == _currentLocation.getCurrentRoom()) {
				room = card;
			}
		}
		
		ArrayList<Card> unseenWeapons = getUnseenCardsByType(unseenCards, CardType.WEAPON);
		ArrayList<Card> unseenPersons = getUnseenCardsByType(unseenCards, CardType.PERSON);

		Card randomWeapon = unseenWeapons.get(new Random().nextInt(unseenWeapons.size()));
		Card randomPerson = unseenPersons.get(new Random().nextInt(unseenPersons.size()));
		
		return super.makeSuggestion(randomWeapon, randomPerson, room);
	}
	
	public ArrayList<Card> getUnseenCardsByType(ArrayList<Card> unseenCards, CardType type) {
		
		ArrayList<Card> unseenCardsOfType = new ArrayList<Card>();

		for (Card card : unseenCards) {
			if (card.getType() == type) {
				unseenCardsOfType.add(card);
			}
		}
		return unseenCardsOfType;
	}
}
