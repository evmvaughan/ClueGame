package clueGame.Playables.Events;

import SeedWork.IEvent;
import clueGame.Playables.Entities.Player.Player;
import clueGame.Playables.Entities.Player.Guess.Suggestion;

public class SuggestionAssertedEvent implements IEvent {
	private Player _player; 
	private Suggestion _suggestion; 
		
	public SuggestionAssertedEvent(Player player, Suggestion suggestion) {
		_player = player;
		_suggestion = suggestion;
	}
	
	public Player getPlayer() {
		return _player;
	}
	
	public Suggestion getSuggestion() {
		return _suggestion;
	}
}
