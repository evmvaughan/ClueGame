package ClueGame.GameEngine.EventHandlers;

import ClueGame.GameEngine.GameEngine;
import ClueGame.GameEngine.Movement.Movement;
import ClueGame.GameEngine.Panels.ClueGameUI;
import ClueGame.Playables.Entities.Player.HumanPlayer;
import ClueGame.Playables.Entities.Player.Player;
import SeedWork.IEventHandler;

@SuppressWarnings("hiding")
public class PlayerSelectingTargetsEventHandler<PlayerSelectingTargetsEvent> implements IEventHandler<PlayerSelectingTargetsEvent> {
	

	public PlayerSelectingTargetsEventHandler() { }
	
	public void Handle(PlayerSelectingTargetsEvent event) {
		
		Player player = (Player) ((ClueGame.Playables.Events.PlayerSelectingTargetsEvent) event).getPlayer();
		int step = ((ClueGame.Playables.Events.PlayerSelectingTargetsEvent) event).getStep();
		
		Movement playerMovement = GameEngine.Movement;
		
		playerMovement.getSelectedTargetsForPlayer(player, step);
		
		if (player instanceof HumanPlayer) {
			player.setTurnLock(true);			
		}
		
		ClueGameUI.getInstance().updateUIComponents();
	}
}
