package ClueGame.Playables.Events;

import ClueGame.Playables.Entities.Player.Player;
import SeedWork.IEvent;

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
