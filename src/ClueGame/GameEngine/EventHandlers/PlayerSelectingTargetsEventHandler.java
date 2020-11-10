package ClueGame.GameEngine.EventHandlers;

import ClueGame.GameEngine.GameEngine;
import ClueGame.GameEngine.Movement.Movement;
import ClueGame.Playables.Entities.Player.ComputerPlayer;
import SeedWork.IEventHandler;

@SuppressWarnings("hiding")
public class PlayerSelectingTargetsEventHandler<PlayerSelectingTargetsEvent> implements IEventHandler<PlayerSelectingTargetsEvent> {
	

	public PlayerSelectingTargetsEventHandler() { }
	
	public void Handle(PlayerSelectingTargetsEvent event) {
		
		ComputerPlayer player = (ComputerPlayer) ((ClueGame.Playables.Events.PlayerSelectingTargetsEvent) event).getPlayer();
		int step = ((ClueGame.Playables.Events.PlayerSelectingTargetsEvent) event).getStep();
		
		Movement playerMovement = GameEngine.Movement;
		
		playerMovement.getSelectedTargetsForPlayer(player, step);
	}
}
