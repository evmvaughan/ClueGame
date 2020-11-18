package ClueGame.Playables.Events;

import ClueGame.Playables.Entities.Player.LocationDTO;
import ClueGame.Playables.Entities.Player.Player;
import SeedWork.IEvent;

public class PlayerMovedToSelectedTargetEvent implements IEvent {

	private Player _player;
	private LocationDTO _newLocation;
	
	public PlayerMovedToSelectedTargetEvent(Player player, LocationDTO newLocation) {
		_player = player;
		_newLocation = newLocation;
	}
	
	public Player getPlayer() {
		return _player;
	}
	
	public LocationDTO getNewLocation() {
		return _newLocation;
	}
	
}
