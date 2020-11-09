package clueGame.Playables.Entities.Player.Guess;

import clueGame.Playables.Entities.Card.Card;
import clueGame.Playables.Entities.Card.Collection.CollectionType;

public class Accusation extends Guess {
	
	public Accusation(Card weapon, Card person, Card room) {
		super(weapon, person, room);
		
		_collectionType = CollectionType.ACCUSATION;
	}	
}
