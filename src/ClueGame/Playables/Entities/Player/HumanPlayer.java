package ClueGame.Playables.Entities.Player;

import ClueGame.Playables.Events.PlayerMovedToSelectedTargetEvent;
import SeedWork.EventBus;

public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, String playerID) {
		super(playerName, playerID);
	}

	public LocationDTO moveToTarget(LocationDTO targetLocation) {
		
		super.updateLocation(targetLocation);
		
		EventBus.getInstance().Publish(new PlayerMovedToSelectedTargetEvent(this, _currentLocation));
		
		return _currentLocation;
	}
	
}
