package ClueGame.GameEngine;

import java.io.FileNotFoundException;

import ClueGame.Board.Services.BoardServiceCollection;
import ClueGame.GameEngine.Movement.Movement;
import ClueGame.Playables.Services.PlayablesServiceCollection;
import Exceptions.BadConfigFormatException;
import SeedWork.ISingleton;

public class GameEngine implements ISingleton<GameEngine> {
	
	private static GameEngine instance = new GameEngine();
	
	public static Movement Movement;
	
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
	}
	
	
	public static void main(String[] args) {
		GameEngine gameEngine = GameEngine.getInstance();
		
		gameEngine.initializeAll();
	}
		

}
