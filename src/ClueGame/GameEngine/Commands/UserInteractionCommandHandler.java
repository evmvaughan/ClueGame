package ClueGame.GameEngine.Commands;

import ClueGame.Playables.Services.PlayerService;
import Exceptions.PlayersTurnNotFinishedException;

public class UserInteractionCommandHandler {
	
	private PlayerService _playerService;
	
	public UserInteractionCommandHandler() {
		_playerService = PlayerService.getInstance();
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
		
		
	}
}
