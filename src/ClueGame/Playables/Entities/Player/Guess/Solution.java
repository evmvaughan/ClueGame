package ClueGame.Playables.Entities.Player.Guess;

import ClueGame.Playables.Entities.Card.Card;
import ClueGame.Playables.Entities.Card.Collection.CollectionType;

public class Solution extends Guess {

	public Solution(Card weapon, Card person, Card room) {
		super(weapon, person, room);
		
		_collectionType = CollectionType.SOLUTION;
	}
}