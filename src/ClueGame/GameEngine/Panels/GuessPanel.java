package ClueGame.GameEngine.Panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ClueGame.Board.Entities.Room.Room;
import ClueGame.GameEngine.GameEngine;
import ClueGame.GameEngine.Movement.Movement;
import ClueGame.Playables.Entities.Card.Card;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Entities.Player.Guess.Guess;
import ClueGame.Playables.Entities.Player.Guess.Suggestion;
import ClueGame.Playables.Services.PlayablesServiceCollection;
import Exceptions.PlayerSuggestionNotInRoomException;

public class GuessPanel extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private Guess _guess;
	
	private JComboBox playerCombo;
	private JComboBox weaponCombo;
	
	public GuessPanel() {
		
		ArrayList<String> players = new ArrayList<String>();
		ArrayList<String> weapons = new ArrayList<String>();
		
		for (Card player : PlayablesServiceCollection.CardService.getPersons()) {
			players.add(player.getName());
		}
		for (Card weapon : PlayablesServiceCollection.CardService.getWeapons()) {
			weapons.add(weapon.getName());
		}
		
		String[] weaponsArray = weapons.toArray(new String[weapons.size()]);
		String[] playersArray = players.toArray(new String[players.size()]);		

		weaponCombo = new JComboBox(weaponsArray);
		playerCombo = new JComboBox(playersArray);
              
        
        setLayout(new GridLayout(4,2));
        
        add(new JLabel("Room"));
        add(new JLabel("Placeholder"));
        add(new JLabel("Player"));
        add(playerCombo);
        add(new JLabel("Weapon"));
        add(weaponCombo);

        JButton suggest = new JButton("Suggest");
        add(suggest);
        suggest.addActionListener(this);
        JButton cancel = new JButton("Cancel");
        add(cancel);
        cancel.addActionListener(this);
        
        pack();
        setVisible(false);

	}
	
	public void showGuess() {
		setVisible(true);
	}
	
	public void hideGuess() {
		setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
				
		if (event.getActionCommand() == "Suggest") {
			
			Player player = PlayablesServiceCollection.PlayerService.getCurrentPlayer();
			
			Room room = Movement.getInstance().getPlayersMovementContext(player).getRoom();
			
			Card roomCard = PlayablesServiceCollection.CardService.getCardByName(room.getName());
			Card weaponCard = PlayablesServiceCollection.CardService.getCardByName(weaponCombo.getSelectedItem().toString());
			Card personCard = PlayablesServiceCollection.CardService.getCardByName(playerCombo.getSelectedItem().toString());
						
			try {
				player.makeSuggestion(weaponCard, personCard, roomCard);
			} catch (PlayerSuggestionNotInRoomException e) {
				System.out.println(e.getMessage());
			}
						
			setVisible(false);
			ClueGameUI.getInstance().updateUIComponents();

		} else if (event.getActionCommand() == "Cancel") {
			setVisible(false);
		}
		
	}

	public static void main(String[] args) {

		GameEngine gameEngine = GameEngine.getInstance();

		gameEngine.initializeAll();

		JFrame frame = new JFrame();
		
		GuessPanel guessView = new GuessPanel();
		
		frame.setContentPane(guessView);
		frame.setSize(100, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
}
