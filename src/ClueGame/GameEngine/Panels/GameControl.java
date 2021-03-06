package ClueGame.GameEngine.Panels;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import ClueGame.GameEngine.Commands.UserInteractionCommand;
import ClueGame.GameEngine.Commands.UserInteractionCommandHandler;
import ClueGame.GameEngine.ViewModels.CellView;
import ClueGame.GameEngine.ViewModels.PlayerView;
import ClueGame.Playables.Entities.Card.Card;
import Exceptions.GameIsNotRunningException;
import Exceptions.NotPlayersTurnException;
import Exceptions.PlayersTurnNotFinishedException;

public class GameControl extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField _playerName;
	private JTextField _roll;

	private JTextField _guessResult;
	private JTextField _guess;
	
	private UserInteractionCommandHandler _commandHandler;

	public GameControl() {
		
		_commandHandler = new UserInteractionCommandHandler();
		
		setLayout(new GridLayout(2, 0));
		JPanel internalPanel = turnInformation();
		add(internalPanel);
		internalPanel = playerActionButtons();
		add(internalPanel);
		internalPanel = guess();
		add(internalPanel);
		internalPanel = guessResult();
		add(internalPanel);
	}
	
	public void setGuessResult(String prompt) {
		
		_guessResult.setText(prompt);
		
	}
	
	public void setTurn(PlayerView playerView, Integer roll) {
		_playerName.setText(playerView.getName());
		_playerName.setBackground(playerView.getColor());
		_roll.setText(roll.toString());
	}
	
	public void setGuess(ArrayList<Card> cards) {
		_guess.setText(cards.get(0).getName() + ", " + cards.get(1).getName() + ", " + cards.get(2).getName());
	}
	
	private JPanel turnInformation() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));

		JPanel internalPanel = new JPanel();
		internalPanel.setLayout(new GridLayout(2,0));
		
		JPanel internalInternalPanel = new JPanel();
	 	JLabel nameLabel = new JLabel("Whose Turn?");
	 	internalInternalPanel.add(nameLabel);
	 	internalPanel.add(internalInternalPanel);
	 	
		internalInternalPanel = new JPanel();
		_playerName = new JTextField(10);
		_playerName.setEditable(false);
	 	internalInternalPanel.add(_playerName);
	 	internalPanel.add(internalInternalPanel);

		panel.add(internalPanel);
	 	
		internalPanel = new JPanel();
		internalPanel.setLayout(new GridLayout(1,2));
		
		internalInternalPanel = new JPanel();
	 	nameLabel = new JLabel("Roll: ");
	 	internalInternalPanel.add(nameLabel);
	 	internalPanel.add(internalInternalPanel);
	 	
		internalInternalPanel = new JPanel();
		_roll = new JTextField(5);
		_roll.setEditable(false);
	 	internalInternalPanel.add(_roll);
	 	internalPanel.add(internalInternalPanel);
		panel.add(internalPanel);
	 	
		return panel;
	}

	private JPanel playerActionButtons() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		
		JButton accusation = new JButton("Make Accusation");
		panel.add(accusation);
		accusation.addActionListener(this);
		
		JButton next = new JButton("NEXT!");
		panel.add(next);
		next.addActionListener(this);


		return panel;
	}

	private JPanel guess() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		_guess = new JTextField(10);
		_guess.setEditable(false);
	 	panel.add(_guess);
		return panel;
	}
	
	private JPanel guessResult() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		_guessResult = new JTextField(10);
		_guessResult.setEditable(false);
	 	panel.add(_guessResult);
		return panel;
	}

	public static void main(String[] args) {
		GameControl panel = new GameControl(); 
		JFrame frame = new JFrame(); 
		frame.setContentPane(panel); 
		frame.setSize(750, 180); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true); 
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		UserInteractionCommand command = new UserInteractionCommand(event.getActionCommand());
		
		try {
			_commandHandler.Handle(command);
		} catch (PlayersTurnNotFinishedException | NotPlayersTurnException | GameIsNotRunningException e) {
			
			String message = e.getMessage();
    		
    		JOptionPane.showMessageDialog(ClueGameUI.getInstance(), message, "Clue Game Message", JOptionPane.INFORMATION_MESSAGE);
   		
		} 
		
		ClueGameUI.getInstance().updateUIComponents();
	}
}
