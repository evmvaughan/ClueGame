package ClueGame.Playables.Entities.Player;

import java.util.ArrayList;
import java.util.Random;

import ClueGame.Playables.Entities.Card.Card;
import ClueGame.Playables.Entities.Card.Collection.Hand;
import ClueGame.Playables.Entities.Player.Guess.Accusation;
import ClueGame.Playables.Entities.Player.Guess.Suggestion;
import ClueGame.Playables.Events.AccusationAssertedEvent;
import ClueGame.Playables.Events.PlayerMovedToSelectedTargetEvent;
import ClueGame.Playables.Events.PlayerSelectingTargetsEvent;
import ClueGame.Playables.Events.SuggestionAssertedEvent;
import Exceptions.PlayerSuggestionNotInRoomException;
import SeedWork.EventBus;
import SeedWork.IEntity;

public abstract class Player implements IEntity {
	
	private String _playerName;
	
	protected Hand _hand;
	protected LocationDTO _currentLocation;
	protected ArrayList<Card> _deckReference;
	
	protected ArrayList<LocationDTO> _targets;
	
	protected ArrayList<Card> _seenCards;
	
	private boolean _turn;
	private boolean _turnLock;
	
	private Suggestion _recentSuggestion;
	private Card _recentSuggestionResponse;
	
	protected int _roll;
	
	public Player(String playerName, String playerID) {
		_playerName = playerName;
		
		_seenCards = new ArrayList<Card>();
	}
	
	public String getName() {
		return _playerName;
	}

	public void updateHand(Hand hand) {
		
		_seenCards.add(hand.getCardAtIndex(0));
		_seenCards.add(hand.getCardAtIndex(1));
		_seenCards.add(hand.getCardAtIndex(2));

		_hand = hand;
	}
	
	public Hand getHand() {
		return _hand;
	}
	
	public void setSeenCards(ArrayList<Card> cards) {
		_seenCards = cards;
	}
	
	public ArrayList<Card> getSeenCards() {
		return _seenCards;
	}
	
	public ArrayList<Card> getSeenCardsWithoutHand() {
		
		ArrayList<Card> uniqueUnseen = new ArrayList<Card>();
		
		boolean isUnique;
		
		for (Card seen : _seenCards) {
			isUnique = true;
			
			for (Card hand : _hand.getCards()) {
				if (seen == hand) {
					isUnique = false;
				}
			}
			if (isUnique) {
				uniqueUnseen.add(seen);
			}
		}
		
		return uniqueUnseen;
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
	

	public void setTargets(ArrayList<LocationDTO> targets) {
		_targets = targets;
	}

	public boolean hasTurn() {
		return _turn;
	}
	
	public void setTurn(boolean turn) {
		_turn = turn;
	}

	public void rollDice() {
		_roll = new Random().nextInt(7);
		
		if (_roll == 0) {
			_roll = 1;
		}
	}
	
	public ArrayList<LocationDTO> getTargets() {
		return _targets;
	}
	
	public LocationDTO moveToTarget(LocationDTO targetLocation) {
		
		updateLocation(targetLocation);
		
		EventBus.getInstance().Publish(new PlayerMovedToSelectedTargetEvent(this, _currentLocation));
		
		return _currentLocation;
	}

	public void setTurnLock(boolean status) {
		_turnLock = status;
	}
	
	public boolean turnLocked() {
		return _turnLock;
	}
	
	public void selectTargetFromRoll() {
		EventBus.getInstance().Publish(new PlayerSelectingTargetsEvent(this, _roll));
	}

	public Accusation makeAccusation(Card weapon, Card person, Card room) {
		
		Accusation accusation = new Accusation(weapon, person, room);
	
		EventBus.getInstance().Publish(new AccusationAssertedEvent(this, accusation));
		
		return accusation;
	}

	public Suggestion makeSuggestion(Card weapon, Card person, Card room) throws PlayerSuggestionNotInRoomException {
		
		if (!_currentLocation.getCurrentRoom().equals(room.getName())) throw new PlayerSuggestionNotInRoomException("Player not in suggested room!");
		
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

	public Integer getRoll() {
		return _roll;
	}

	public void addSeenCard(Card disprovedCard) {
		
		boolean isUnique = true;
		
		for (Card card : _seenCards) {
			if (disprovedCard == card) {
				isUnique = false;
			}
		}
		
		if (isUnique) {
			_seenCards.add(disprovedCard);
		}
	}
}