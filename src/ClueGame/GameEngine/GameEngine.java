package ClueGame.GameEngine;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import ClueGame.Board.Services.BoardServiceCollection;
import ClueGame.Playables.Entities.Player.LocationDTO;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Entities.Player.Guess.Accusation;
import ClueGame.Playables.Services.PlayablesServiceCollection;
import ClueGame.Playables.Services.PlayerService;
import Exceptions.BadConfigFormatException;
import ClueGame.GameEngine.Movement.*;
import ClueGame.GameEngine.Panels.ClueGameUI;
import SeedWork.ISingleton;

public class GameEngine implements ISingleton<GameEngine>{
	
	private static GameEngine instance = new GameEngine();
	
	public static Movement Movement;
	
	private PlayerService _playerService;
	
	private boolean _gameIsRunning;
	
	private boolean _isMock;
	
	private GameEngine () {}
	
	public static GameEngine getInstance() {
		return instance;
	}
	
	public void initializeAll() {
		
		_gameIsRunning = true;
		
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

	public void setAsMock() {
		_isMock = true;
	}

	public void endGame(Accusation accusation, boolean win) {
		
		_gameIsRunning = false;
		if (!_isMock) {
			ClueGameUI.getInstance().showGameIsFinishedDialog(accusation, win);
		}
	}
	
	public boolean gameIsRunning() {
		return _gameIsRunning;
	}
}
