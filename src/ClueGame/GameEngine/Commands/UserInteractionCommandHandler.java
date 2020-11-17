package ClueGame.GameEngine.Commands;

import java.util.ArrayList;

import ClueGame.GameEngine.GameEngine;
import ClueGame.GameEngine.Movement.Movement;
import ClueGame.Playables.Entities.Player.ComputerPlayer;
import ClueGame.Playables.Entities.Player.LocationDTO;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Services.PlayerService;
import Exceptions.PlayersTurnNotFinishedException;

public class UserInteractionCommandHandler {
	
	private PlayerService _playerService;
	private GameEngine _gameEngine;
	
	public UserInteractionCommandHandler() {
		_playerService = PlayerService.getInstance();
		_gameEngine = GameEngine.getInstance();
	}
	
	public void Handle(UserInteractionCommand command) {
		handleByType(command.getType());
	}

	private void handleByType(String type) {
		
		switch(type) {
		case "NEXT!":
			try {
				handleNextPlayerCommand();
			} catch (PlayersTurnNotFinishedException e) {
				System.out.println(e.getMessage());
			}
			break;
		}
	}

	private void handleNextPlayerCommand() throws PlayersTurnNotFinishedException {
				
		_playerService.assertCurrentPlayerTurnFinished();
		
		_playerService.initiateNextPlayerTurn();
		
		Player currentPlayer = _playerService.getCurrentPlayer();
				
		currentPlayer.rollDice();
		currentPlayer.selectTargetFromRoll();
		
		if (currentPlayer instanceof ComputerPlayer) {
			((ComputerPlayer) currentPlayer).moveToTarget();
			System.out.println("Player location: " + currentPlayer.getName() + " Row: " + currentPlayer.getLocation().getCurrentRow() + " Col: " + currentPlayer.getLocation().getCurrentColumn());
		} else {
						
			ArrayList<LocationDTO> targets = currentPlayer.getTargets();
			
			for (LocationDTO location : targets) {
				System.out.println("Player target: " + currentPlayer.getName() + " Row: " + location.getCurrentRow() + " Col: " + location.getCurrentColumn());
			}
			
			
		}
		
		System.out.println("Current player: " + _playerService.getCurrentPlayer().getName());
	}
}
