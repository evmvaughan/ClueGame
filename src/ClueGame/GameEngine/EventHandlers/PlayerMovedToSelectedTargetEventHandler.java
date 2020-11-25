package ClueGame.GameEngine.EventHandlers;

import ClueGame.GameEngine.GameEngine;

import ClueGame.GameEngine.Movement.Movement;
import ClueGame.GameEngine.Panels.ClueGameUI;
import ClueGame.Playables.Entities.Player.ComputerPlayer;
import ClueGame.Playables.Entities.Player.LocationDTO;
import ClueGame.Playables.Entities.Player.Player;
import Exceptions.PlayerSuggestionNotInRoomException;
import SeedWork.IEventHandler;

@SuppressWarnings("hiding")
public class PlayerMovedToSelectedTargetEventHandler<PlayerMovedToSelectedTargetEvent> implements IEventHandler<PlayerMovedToSelectedTargetEvent> {
	

	public PlayerMovedToSelectedTargetEventHandler() { }
	
	public void Handle(PlayerMovedToSelectedTargetEvent event) {
		
		Player player = ((ClueGame.Playables.Events.PlayerMovedToSelectedTargetEvent) event).getPlayer();
		LocationDTO newLocation = ((ClueGame.Playables.Events.PlayerMovedToSelectedTargetEvent) event).getNewLocation();
		
		Movement playerMovement = GameEngine.Movement;
		
		playerMovement.updatePlayerMovementContext(player, newLocation);
		
		if (player instanceof ComputerPlayer) {
			
			if (playerMovement.getPlayersMovementContext(player).getCell().isRoomCenter() && player.hasTurn()) {
				try {
					((ComputerPlayer) player).makeSuggestion();
				} catch (PlayerSuggestionNotInRoomException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
