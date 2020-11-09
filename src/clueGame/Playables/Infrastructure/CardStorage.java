package clueGame.Playables.Infrastructure;

import java.util.ArrayList;
import java.util.Set;

import Exceptions.CouldNotCreateEntityException;
import SeedWork.ILocalStorage;
import clueGame.Playables.Entities.Card.Card;
import clueGame.Playables.Entities.Card.CardType;
import clueGame.Playables.Entities.Card.Collection.CollectionType;
import clueGame.Playables.Entities.Card.Collection.ICardCollection;

public class CardStorage implements ILocalStorage<Card> {

	private ArrayList<Card> _cards;
	
	private ArrayList<ICardCollection> _cardCollections;
			
	private static CardStorage instance = new CardStorage();

	private CardStorage() { 
		_cards = new ArrayList<Card>();
		_cardCollections = new ArrayList<ICardCollection>();
	}
	
	public static CardStorage getInstance() {
		return instance;
	}

	public ArrayList<Card> getAll() {
		return _cards;
	}

	public void addOne(Card card) throws CouldNotCreateEntityException {
		if (card == null) throw new CouldNotCreateEntityException();
		_cards.add(card);
	}

	public void setAll(ArrayList<Card> cards) {
		_cards=cards;
	}

	public void clear() {
		_cards.clear();
		_cardCollections.clear();
	}
	
	public ArrayList<Card> getCardsByType(CardType type) {
		
		ArrayList<Card> typeSpecificCards = new ArrayList<Card>();
		
		for (Card card : _cards) {
			if (card.getType() == type) {
				typeSpecificCards.add(card);
			}
		}
		return typeSpecificCards;
	}
	
	public ArrayList<ICardCollection> getCardCollectionsByType(CollectionType type) {
		
		ArrayList<ICardCollection> typeSpecificCollection = new ArrayList<ICardCollection>();
		
		for (ICardCollection collection : _cardCollections) {
			if (collection.getCollectionType() == type) {
				typeSpecificCollection.add(collection);
			}
		}
		
		return typeSpecificCollection;
	}
	
	public void addCardCollection(ICardCollection collection) {
		_cardCollections.add(collection);
	}
	
}