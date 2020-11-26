package ClueGame.GameEngine.Commands;

import ClueGame.GameEngine.GameEngine;
import ClueGame.GameEngine.Panels.GuessPanel;
import ClueGame.Playables.Entities.Card.Collection.CollectionType;
import ClueGame.Playables.Entities.Player.ComputerPlayer;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Services.PlayerService;
import Exceptions.GameIsNotRunningException;
import Exceptions.NotPlayersTurnException;
import Exceptions.PlayersTurnNotFinishedException;

public class UserInteractionCommandHandler {
	
	private PlayerService _playerService;
	private GameEngine _gameEngine;
	
	
	public UserInteractionCommandHandler() {
		_playerService = PlayerService.getInstance();
		_gameEngine = GameEngine.getInstance();
	}
	
	public void Handle(UserInteractionCommand command) throws PlayersTurnNotFinishedException, NotPlayersTurnException, GameIsNotRunningException {
		
		if (_gameEngine.gameIsRunning()) {
			handleByType(command.getType());
		} else {
			throw new GameIsNotRunningException("Game is not running!");
		}
	}


	private void handleByType(String type) throws PlayersTurnNotFinishedException, NotPlayersTurnException {
		
		
		switch(type) {
		case "NEXT!":
			handleNextPlayerCommand();

			break;
		case "Make Accusation":
			handleAccusatonCommand();
			
			break;
		}
	}

	private void handleAccusatonCommand() throws NotPlayersTurnException {
		
		if (_playerService.getCurrentPlayer().hasTurn()) {
			GuessPanel.getInstance().showGuess(CollectionType.ACCUSATION);

		} else {
			throw new NotPlayersTurnException("It is not your turn!");
		}
		
		
	}

	private void handleNextPlayerCommand() throws PlayersTurnNotFinishedException {
				
		_playerService.assertCurrentPlayerTurnFinished();
		
		_playerService.initiateNextPlayerTurn();
		
		Player currentPlayer = _playerService.getCurrentPlayer();
		
		if (currentPlayer instanceof ComputerPlayer && ((ComputerPlayer)currentPlayer).hasAccusation()) {
			((ComputerPlayer)currentPlayer).makeAccusation();
		}
				
		currentPlayer.rollDice();
		currentPlayer.selectTargetFromRoll();
		
		if (currentPlayer instanceof ComputerPlayer) {
			((ComputerPlayer) currentPlayer).moveToTarget();
		}
	}
}
