package ClueGame.Playables.Entities.Player.Guess;

import ClueGame.Playables.Entities.Card.Card;
import ClueGame.Playables.Entities.Card.Collection.CollectionType;

public class Accusation extends Guess {
	
	public Accusation(Card weapon, Card person, Card room) {
		super(weapon, person, room);
		
		_collectionType = CollectionType.ACCUSATION;
	}	
}
