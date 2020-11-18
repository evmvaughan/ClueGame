package ClueGame.Playables.Entities.Player.Guess;

import ClueGame.Playables.Entities.Card.Card;
import ClueGame.Playables.Entities.Card.Collection.CollectionType;

public class Suggestion extends Guess {

	public Suggestion(Card weapon, Card person, Card room) {
		super(weapon, person, room);
		
		_collectionType = CollectionType.SUGGESTION;
	}
}
