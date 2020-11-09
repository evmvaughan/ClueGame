package clueGame.Playables.Entities.Player;

import java.util.*;

import Exceptions.PlayerSuggestionNotInRoomException;
import SeedWork.EventBus;
import SeedWork.IEntity;
import clueGame.Board.Entities.Cell.BoardCell;
import clueGame.Playables.Entities.Card.Card;
import clueGame.Playables.Entities.Card.Collection.Hand;
import clueGame.Playables.Entities.Player.Guess.Accusation;
import clueGame.Playables.Entities.Player.Guess.Suggestion;
import clueGame.Playables.Events.AccusationAssertedEvent;
import clueGame.Playables.Events.SuggestionAssertedEvent;

public abstract class Player implements IEntity {
	
	private String _playerName;
	private String _playerID;
	
	protected Hand _hand;
	protected LocationDTO _currentLocation;
	protected ArrayList<Card> _deckReference;
	protected Set<BoardCell> _targets;
	
	private Suggestion _recentSuggestion;
	private Card _recentSuggestionResponse;
	
	
	public Player(String playerName, String playerID) {
		_playerName = playerName;
		_playerID = playerID;
	}
	
	public String getName() {
		return _playerName;
	}

	public void updateHand(Hand hand) {
		_hand = hand;
	}
	
	public Hand getHand() {
		return _hand;
	}
	
	public void updateLocation(LocationDTO location) {
		_currentLocation = location;
	}
	
	public LocationDTO getLocation() {
		return _currentLocation;
	}
	
	public void setDeckReference(ArrayList<Card> deck) {
		_deckReference = deck;
	}
	
	public boolean madeSuggestion(Suggestion suggestion) {
		return _recentSuggestion == suggestion;
	}
	
	public void receiveSuggestionResponse(Card response) {
		_recentSuggestionResponse = response;
	}
	
	public Card getSuggestionResponse() {
		return _recentSuggestionResponse;
	}

	public Accusation makeAccusation(Card weapon, Card person, Card room) {
		
		Accusation accusation = new Accusation(weapon, person, room);
	
		EventBus.getInstance().Publish(new AccusationAssertedEvent(this, accusation));
		
		return accusation;
	}

	public Suggestion makeSuggestion(Card weapon, Card person, Card room) throws PlayerSuggestionNotInRoomException {
		
		if (_currentLocation.getCurrentRoom() != room.getName()) throw new PlayerSuggestionNotInRoomException("Player not in suggested room!");
		
		Suggestion suggestion = new Suggestion(weapon, person, room);
		_recentSuggestion = suggestion;
		
		EventBus.getInstance().Publish(new SuggestionAssertedEvent(this, suggestion));
		
		return suggestion;
	}
	
	public Card disproveSuggestion(Suggestion suggestion) {
		
		ArrayList<Card> disprovals = new ArrayList<Card>();
		
		for (Card card : _hand.getCards()) {
			
			for (Card suggestedCard : suggestion.getCards()) {
	
				if (card == suggestedCard) {
					disprovals.add(suggestedCard);
				}
			}
		}
		if (disprovals.size() > 0) {
			
			if (disprovals.size() > 1) {
				return disprovals.get(new Random().nextInt(disprovals.size()));
			}
			return disprovals.get(0);
		}
		
		return null;
	}

	public void setTargets(Set<BoardCell> targets) {
		_targets = targets;
	}
}