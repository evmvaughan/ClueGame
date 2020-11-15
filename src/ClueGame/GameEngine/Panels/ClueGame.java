package ClueGame.GameEngine.Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ClueGame.GameEngine.GameEngine;
import ClueGame.GameEngine.ViewModels.BoardView;
import ClueGame.Playables.Entities.Player.HumanPlayer;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Services.PlayablesServiceCollection;

public class ClueGame extends JPanel {
	
	public static final int _frameWidth = 800;
	public static final int _frameHeight = 850;
	
	public static final int _cardManagementWidth = 200;
	public static final int _cardManagementHeight = _frameHeight - 150;
	
	public static final int _controlWidth = _frameWidth;
	public static final int _controlHeight = _frameHeight - _cardManagementHeight;
	
	
	public ClueGame(Player player) {
			
		setLayout(new BorderLayout());
		
		CardManagement cardManagement = new CardManagement(player);
		BoardView board = new BoardView(_frameWidth - _cardManagementWidth, _frameHeight - _controlHeight);
		GameControl gameControl = new GameControl();
		
		cardManagement.setPreferredSize(new Dimension(_cardManagementWidth, _cardManagementHeight));
		board.setPreferredSize(new Dimension(_frameWidth - _cardManagementWidth, _frameHeight - _controlHeight));
		gameControl.setPreferredSize(new Dimension(_controlWidth, _controlHeight));

		add(cardManagement, BorderLayout.EAST);
		add(board, BorderLayout.CENTER);
		add(gameControl, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		GameEngine gameEngine = GameEngine.getInstance();
		
		gameEngine.initializeAll();
		
		HumanPlayer player = (HumanPlayer) PlayablesServiceCollection.PlayerService.getPlayers().get(5);
		
		ClueGame panel = new ClueGame(player); 

		JFrame frame = new JFrame("Clue Game"); 
		frame.setContentPane(panel); 
		frame.setSize(_frameWidth, _frameHeight); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true); 
		
		
		
	}
}
