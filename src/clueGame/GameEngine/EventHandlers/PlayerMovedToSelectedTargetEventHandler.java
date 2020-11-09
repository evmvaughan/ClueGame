package clueGame.GameEngine.EventHandlers;

import SeedWork.IEventHandler;
import clueGame.GameEngine.GameEngine;
import clueGame.GameEngine.Movement.Movement;
import clueGame.Playables.Entities.Player.ComputerPlayer;
import clueGame.Playables.Entities.Player.LocationDTO;
import clueGame.Playables.Entities.Player.Player;

@SuppressWarnings("hiding")
public class PlayerMovedToSelectedTargetEventHandler<PlayerMovedToSelectedTargetEvent> implements IEventHandler<PlayerMovedToSelectedTargetEvent> {
	

	public PlayerMovedToSelectedTargetEventHandler() { }
	
	public void Handle(PlayerMovedToSelectedTargetEvent event) {
		
		Player player = ((clueGame.Playables.Events.PlayerMovedToSelectedTargetEvent) event).getPlayer();
		LocationDTO newLocation = ((clueGame.Playables.Events.PlayerMovedToSelectedTargetEvent) event).getNewLocation();
		
		Movement playerMovement = GameEngine.Movement;
		
		playerMovement.updatePlayerMovementContext(player, newLocation);
	}
}
