package clueGame.Playables.Entities.Player.Guess;
import java.util.ArrayList;

import Exceptions.IncorrectCardTypeException;
import Exceptions.TooManyCardsInCollectionException;
import clueGame.Playables.Entities.Card.Card;
import clueGame.Playables.Entities.Card.CardType;
import clueGame.Playables.Entities.Card.Collection.CollectionType;
import clueGame.Playables.Entities.Card.Collection.ICardCollection;

public abstract class Guess implements ICardCollection {
	
	private ArrayList<Card> _cards;
	
	private Card _weapon;
	private Card _person;
	private Card _room;
	
	protected CollectionType _collectionType;
	
	public Guess() {
		_cards = new ArrayList<Card>();
	}
	
	public Guess(Card weapon, Card person, Card room) {
		_cards = new ArrayList<Card>();
		
		try {
			addWeaponCard(weapon);
			addPersonCard(person);
			addRoomCard(room);
		} catch (IncorrectCardTypeException e) {
			System.out.println(e.getMessage());
		}

	}
	
	public void addWeaponCard(Card weapon) throws IncorrectCardTypeException {
		if (weapon.getType() == CardType.WEAPON) {
			_weapon = weapon; _cards.add(weapon);
		} else { throw new IncorrectCardTypeException("Card does not have the correct type!"); }
	}
	
	public void addPersonCard(Card person) throws IncorrectCardTypeException {
		if (person.getType() == CardType.PERSON) {
			_person = person; _cards.add(person);
		} else { throw new IncorrectCardTypeException("Card does not have the correct type!"); }
		
	}
	
	public void addRoomCard(Card room) throws IncorrectCardTypeException {
		if (room.getType() == CardType.ROOM) {
			_room = room; _cards.add(room);
		} else { throw new IncorrectCardTypeException("Card does not have the correct type!"); }
	}
	
	public Card getWeaponCard() {
		return _weapon;
	}
	
	public Card getPersonCard() {
		return _person;
	}
	
	public Card getRoomCard() {
		return _room;
	}

	public Card getCardAtIndex(int index) {
		return _cards.get(index);
	}

	public ArrayList<Card> getCards() {
		return _cards;
	}
	
	public void setCardAtIndex(Card card, int index) {
		_cards.set(index, card);
	}

	public void addCard(Card card) throws TooManyCardsInCollectionException {
		if (_cards.size() < 3) {
			_cards.add(card);
		} else {
			throw new TooManyCardsInCollectionException("Attempted to add too many cards to the collection!");
		}
	}
	
	public CollectionType getCollectionType() {
		return _collectionType;
	}
}
