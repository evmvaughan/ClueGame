package clueGame.Playables.Events;

import SeedWork.IEvent;
import clueGame.Playables.Entities.Player.Player;
import clueGame.Playables.Entities.Player.Guess.Accusation;

public class AccusationAssertedEvent implements IEvent {
	
	private Player _player; 
	private Accusation _accusation; 
		
	public AccusationAssertedEvent(Player player, Accusation accusation) {
		_player = player;
		_accusation = accusation;
	}
	
	public Player getPlayer() {
		return _player;
	}
	
	public Accusation getAccusation() {
		return _accusation;
	}
}
