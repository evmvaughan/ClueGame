package ClueGame.Playables.Entities.Card.Collection;

import java.util.ArrayList;

import ClueGame.Playables.Entities.Card.Card;
import Exceptions.TooManyCardsInCollectionException;

public interface ICardCollection {
	
	ArrayList<Card> getCards();
	Card getCardAtIndex(int index);
	
	void addCard(Card card)  throws TooManyCardsInCollectionException;
	
	CollectionType getCollectionType();
}
