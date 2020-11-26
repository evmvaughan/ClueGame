package ClueGame.GameEngine.Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ClueGame.GameEngine.GameEngine;
import ClueGame.GameEngine.ViewModels.BoardView;
import ClueGame.GameEngine.ViewModels.CellView;
import ClueGame.GameEngine.ViewModels.PlayerView;
import ClueGame.GameEngine.ViewModels.RoomLabel;
import ClueGame.Playables.Entities.Card.Card;
import ClueGame.Playables.Entities.Player.HumanPlayer;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Entities.Player.Guess.Accusation;
import ClueGame.Playables.Entities.Player.Guess.Guess;
import ClueGame.Playables.Services.PlayablesServiceCollection;

public class ClueGameUI extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private static ClueGameUI instance = new ClueGameUI();
	
	public static final int FrameWidth = 800;
	public static final int FrameHeight = 850;
	
	public static final int CardManagementWidth = 200;
	public static final int CardManagementHeight = FrameHeight - 150;
	
	public static final int ControlWidth = FrameWidth;
	public static final int ControlHeight = FrameHeight - CardManagementHeight;
	
	public static ArrayList<PlayerView> PlayerViews;
	public static ArrayList<CellView> CellViews;
	
	private BoardView _board;
	private GameControl _gameControl;
	private CardManagement _cardManagement;
	
	private ClueGameUI() {}
	
	public static ClueGameUI getInstance() {
		return instance;
	}
	
	public void initializeUI() {
		setLayout(new BorderLayout());
		
		_cardManagement = new CardManagement();
		_board = new BoardView(FrameWidth - CardManagementWidth, FrameHeight - ControlHeight);
		_gameControl = new GameControl();
		
		_cardManagement.setPreferredSize(new Dimension(CardManagementWidth, CardManagementHeight));
		_board.setPreferredSize(new Dimension(FrameWidth - CardManagementWidth, FrameHeight - ControlHeight));
		_gameControl.setPreferredSize(new Dimension(ControlWidth, ControlHeight));

		add(_cardManagement, BorderLayout.EAST);
		add(_board, BorderLayout.CENTER);
		add(_gameControl, BorderLayout.SOUTH);
		
	}
	
	public void updateUIComponents() {
		repaint();
		
		Player currentPlayer = PlayablesServiceCollection.PlayerService.getCurrentPlayer();
		
		for (PlayerView view : PlayerViews) 
			if (view.getPlayer() == currentPlayer) _gameControl.setTurn(view, currentPlayer.getRoll());
		
	}
	
	public void showSplashScreen(JFrame frame, Player player) {
		
		String message = "You are " + player.getName();
		
		message = message + "\nCan you find the solution \nbefore the Computer Players?";
		
		JOptionPane.showMessageDialog(frame, message, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);

	}
	
	public void showGameIsFinishedDialog(boolean win) {
		
		String playerName = PlayablesServiceCollection.PlayerService.getCurrentPlayer().getName();
		
		String message;
		
		if (win) {
			message = "Player: " + playerName + " won the game!";
		} else {
			message = "Player: " + playerName + " lost the game!";
		}
				
		JOptionPane.showMessageDialog(this, message, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}

	public void updateDisprovedCard(Card disprovedCard) {
		
		if (disprovedCard != null) {
			_gameControl.setGuessResult(disprovedCard.getName());
		} else {
			_gameControl.setGuessResult(null);
		}

	}

	public void updateGuess(Guess guess) {
		_gameControl.setGuess(guess.getCards());
	}

	public void updateSeenCards() {
		_cardManagement.updateCards();
	}
	
	public static void main(String[] args) {
		GameEngine gameEngine = GameEngine.getInstance();
		
		gameEngine.initializeAll();
		
		HumanPlayer player = (HumanPlayer) PlayablesServiceCollection.PlayerService.getHumanPlayer();
				
		ClueGameUI panel = ClueGameUI.getInstance(); 
		
		panel.initializeUI();

		JFrame frame = new JFrame("Clue Game"); 
		frame.setContentPane(panel); 
		frame.setSize(FrameWidth, FrameHeight); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true); 
		
		panel.showSplashScreen(frame, player);
		
		player.rollDice();
		player.selectTargetFromRoll();
		
		panel.updateUIComponents();
		
	}
}
