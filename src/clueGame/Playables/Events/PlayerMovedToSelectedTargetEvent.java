package clueGame.Playables.Events;

import SeedWork.IEvent;
import clueGame.Playables.Entities.Player.LocationDTO;
import clueGame.Playables.Entities.Player.Player;

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
