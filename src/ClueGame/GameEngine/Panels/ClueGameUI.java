package ClueGame.GameEngine.Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ClueGame.GameEngine.GameEngine;
import ClueGame.GameEngine.ViewModels.BoardView;
import ClueGame.Playables.Entities.Player.HumanPlayer;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Services.PlayablesServiceCollection;

public class ClueGameUI extends JPanel {
	
	private static ClueGameUI instance = new ClueGameUI();
	
	public static final int _frameWidth = 800;
	public static final int _frameHeight = 850;
	
	public static final int _cardManagementWidth = 200;
	public static final int _cardManagementHeight = _frameHeight - 150;
	
	public static final int _controlWidth = _frameWidth;
	public static final int _controlHeight = _frameHeight - _cardManagementHeight;
	
	
	private ClueGameUI() {}
	
	public static ClueGameUI getInstance() {
		return instance;
	}
	
	public void initializeUI() {
		setLayout(new BorderLayout());
		
		CardManagement cardManagement = new CardManagement();
		BoardView board = new BoardView(_frameWidth - _cardManagementWidth, _frameHeight - _controlHeight);
		GameControl gameControl = new GameControl();
		
		cardManagement.setPreferredSize(new Dimension(_cardManagementWidth, _cardManagementHeight));
		board.setPreferredSize(new Dimension(_frameWidth - _cardManagementWidth, _frameHeight - _controlHeight));
		gameControl.setPreferredSize(new Dimension(_controlWidth, _controlHeight));

		add(cardManagement, BorderLayout.EAST);
		add(board, BorderLayout.CENTER);
		add(gameControl, BorderLayout.SOUTH);
	}
	
	public void showSplashScreen(JFrame frame, Player player) {
		
		String message = "You are " + player.getName();
		
		message = message + "\nCan you find the solution \nbefore the Computer Players?";
		
		JOptionPane.showMessageDialog(frame, message, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);

	}
	
	public void updateUIComponents() {
		repaint();
	}
	
	public static void main(String[] args) {
		GameEngine gameEngine = GameEngine.getInstance();
		
		gameEngine.initializeAll();
		
		HumanPlayer player = (HumanPlayer) PlayablesServiceCollection.PlayerService.getPlayers().get(5);
				
		ClueGameUI panel = ClueGameUI.getInstance(); 
		
		panel.initializeUI();

		JFrame frame = new JFrame("Clue Game"); 
		frame.setContentPane(panel); 
		frame.setSize(_frameWidth, _frameHeight); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true); 
		
		panel.showSplashScreen(frame, player);
	}
}
