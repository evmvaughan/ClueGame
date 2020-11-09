package clueGame.Playables.Events;

import SeedWork.IEvent;
import clueGame.Playables.Entities.Player.Player;

public class PlayerSelectingTargetsEvent implements IEvent {
	
	private Player _player;
	private int _step;
	
	public PlayerSelectingTargetsEvent(Player player, int step) {
		_player = player;
		_step = step;
	}
	
	public Player getPlayer() {
		return _player;
	}
	
	public int getStep() {
		return _step;
	}
	
}
