package ClueGame.GameEngine.Commands;

import ClueGame.GameEngine.GameEngine;
import ClueGame.Playables.Entities.Player.ComputerPlayer;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Services.PlayerService;
import Exceptions.PlayersTurnNotFinishedException;

public class UserInteractionCommandHandler {
	
	private PlayerService _playerService;
	public UserInteractionCommandHandler() {
		_playerService = PlayerService.getInstance();
		GameEngine.getInstance();
	}
	
	public void Handle(UserInteractionCommand command) throws PlayersTurnNotFinishedException {
		handleByType(command.getType());
	}


	private void handleByType(String type) throws PlayersTurnNotFinishedException {
		
		switch(type) {
		case "NEXT!":
			handleNextPlayerCommand();

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
		}
	}
}
