package clueGame.GameEngine.EventHandlers;

import SeedWork.IEventHandler;
import clueGame.GameEngine.GameEngine;
import clueGame.GameEngine.Movement.Movement;
import clueGame.Playables.Entities.Player.ComputerPlayer;

@SuppressWarnings("hiding")
public class PlayerSelectingTargetsEventHandler<PlayerSelectingTargetsEvent> implements IEventHandler<PlayerSelectingTargetsEvent> {
	

	public PlayerSelectingTargetsEventHandler() { }
	
	public void Handle(PlayerSelectingTargetsEvent event) {
		
		ComputerPlayer player = (ComputerPlayer) ((clueGame.Playables.Events.PlayerSelectingTargetsEvent) event).getPlayer();
		int step = ((clueGame.Playables.Events.PlayerSelectingTargetsEvent) event).getStep();
		
		Movement playerMovement = GameEngine.Movement;
		
		playerMovement.getSelectedTargetsForPlayer(player, step);
	}
}
