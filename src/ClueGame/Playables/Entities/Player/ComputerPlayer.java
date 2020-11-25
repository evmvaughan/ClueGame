package ClueGame.Playables.Entities.Player;

import java.util.ArrayList;
import java.util.Random;

import ClueGame.Playables.Entities.Card.Card;
import ClueGame.Playables.Entities.Card.CardType;
import ClueGame.Playables.Entities.Player.Guess.Suggestion;
import ClueGame.Playables.Events.PlayerMovedToSelectedTargetEvent;
import ClueGame.Playables.Events.PlayerSelectingTargetsEvent;
import Exceptions.PlayerSuggestionNotInRoomException;
import SeedWork.EventBus;

public class ComputerPlayer extends Player {
		
	private LocationDTO _targetLocation;

	public ComputerPlayer(String playerName, String playerID) {
		super(playerName, playerID);		
	}
	
	@Override
	public void receiveSuggestionResponse(Card response) {
		if (response != null) _seenCards.add(response);
		super.receiveSuggestionResponse(response);
	}

	public void selectTargetFromRollOf(int step) {
		EventBus.getInstance().Publish(new PlayerSelectingTargetsEvent(this, step));
	}

	public void setTargetLocation(LocationDTO targetLocation) {
		_targetLocation = targetLocation;
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
			
			if (card.getName().equals(_currentLocation.getCurrentRoom())) {
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
