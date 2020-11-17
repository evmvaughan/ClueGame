package ClueGame.GameEngine;

import java.io.FileNotFoundException;

import ClueGame.Board.Services.BoardServiceCollection;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Services.PlayablesServiceCollection;
import ClueGame.Playables.Services.PlayerService;
import Exceptions.BadConfigFormatException;
import ClueGame.GameEngine.Movement.*;
import SeedWork.ISingleton;

public class GameEngine implements ISingleton<GameEngine>{
	
	private static GameEngine instance = new GameEngine();
	
	public static Movement Movement;
	
	private PlayerService _playerService;
	
	private GameEngine () {}
	
	public static GameEngine getInstance() {
		return instance;
	}
	
	public void initializeAll() {
		BoardServiceCollection.initialize();
		PlayablesServiceCollection.initialize();
		
		try {
			BoardServiceCollection.InitConfigurationService.InitializeBoard("ClueLayout.csv", "ClueSetup.txt");	
			PlayablesServiceCollection.InitConfigurationService.initializePayables("ClueSetup.txt");	
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		
		PlayablesServiceCollection.Dealer.dealRandomSolution();
		PlayablesServiceCollection.Dealer.shuffleAndDealCards();
		
		Movement = ClueGame.GameEngine.Movement.Movement.getInstance();
		
		initializePlayers();
		
	}
	
	private void initializePlayers() {
		
		for (Player player : PlayablesServiceCollection.PlayerService.getPlayers()) {
			
			Movement.initializePlayerAtLocation(player, player.getLocation().getCurrentRow(), player.getLocation().getCurrentColumn());

		}
	}
	
	
	public static void main(String[] args) {
		GameEngine gameEngine = GameEngine.getInstance();
		
		gameEngine.initializeAll();
	}

//	public void checkPlayerTurnStatus() {
//		_playerService.
//	}
		

}
