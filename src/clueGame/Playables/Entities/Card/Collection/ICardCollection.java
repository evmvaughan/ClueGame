package clueGame.Playables.Entities.Card.Collection;

import java.util.ArrayList;

import Exceptions.TooManyCardsInCollectionException;
import clueGame.Playables.Entities.Card.Card;

public interface ICardCollection {
	
	ArrayList<Card> getCards();
	Card getCardAtIndex(int index);
	
	void addCard(Card card)  throws TooManyCardsInCollectionException;
	
	CollectionType getCollectionType();
}
