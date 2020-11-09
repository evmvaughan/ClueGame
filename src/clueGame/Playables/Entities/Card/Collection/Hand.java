package clueGame.Playables.Entities.Card.Collection;

import java.util.ArrayList;

import Exceptions.TooManyCardsInCollectionException;
import clueGame.Playables.Entities.Card.Card;

public class Hand implements ICardCollection {

	private ArrayList<Card> _cards;
	
	private CollectionType _collectionType;
	
	public Hand() {
		_cards = new ArrayList<Card>();
		_collectionType = CollectionType.HAND;
	}
	
	public Hand(Card cardOne, Card cardTwo, Card cardThree) {
		_cards = new ArrayList<Card>();
		_collectionType = CollectionType.HAND;
		
		_cards.add(cardOne);
		_cards.add(cardTwo);
		_cards.add(cardThree);
	}
	
	public Card getCardAtIndex(int index) {
		return _cards.get(index);
	}

	public ArrayList<Card> getCards() {
		return _cards;
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

	public void setCardAtIndex(Card card, int index) {
		_cards.set(index, card);
	}
	
}
