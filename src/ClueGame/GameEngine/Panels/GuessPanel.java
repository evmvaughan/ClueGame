package ClueGame.GameEngine.Panels;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import ClueGame.Board.Entities.Room.Room;
import ClueGame.GameEngine.Movement.Movement;
import ClueGame.Playables.Entities.Card.Card;
import ClueGame.Playables.Entities.Card.Collection.CollectionType;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Services.PlayablesServiceCollection;
import Exceptions.PlayerSuggestionNotInRoomException;

public class GuessPanel extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private static GuessPanel instance = new GuessPanel();
		
	private JLabel currentRoomName;
	private JButton submit;
	
	private JComboBox<?> playerCombo;
	private JComboBox<?> weaponCombo;
	private JComboBox<?> roomCombo;
				
	public static GuessPanel getInstance() {
		return instance;
	}
	
	private GuessPanel() {
				
		this.setTitle("Guess Dialog");
		
		ArrayList<String> players = new ArrayList<String>();
		ArrayList<String> weapons = new ArrayList<String>();
		
		for (Card player : PlayablesServiceCollection.CardService.getPersons()) players.add(player.getName());
		for (Card weapon : PlayablesServiceCollection.CardService.getWeapons()) weapons.add(weapon.getName());

		String[] weaponsArray = weapons.toArray(new String[weapons.size()]);
		String[] playersArray = players.toArray(new String[players.size()]);

		weaponCombo = new JComboBox<Object>(weaponsArray);
		playerCombo = new JComboBox<Object>(playersArray);
        		
		addRoomsToCombo();
		
        currentRoomName = new JLabel();        
        
        setLayout(new GridLayout(4,2));
        
        add(new JLabel("Room"));
        
        add(currentRoomName);
		
        add(new JLabel("Player"));
        add(playerCombo);
        add(new JLabel("Weapon"));
        add(weaponCombo);

		submit = new JButton();
        add(submit);
        submit.addActionListener(this);
        JButton cancel = new JButton("Cancel");
        add(cancel);
        cancel.addActionListener(this);
        
        pack();
        setVisible(false);

	}

	public void showGuess(CollectionType type) {
				
		if (type == CollectionType.ACCUSATION) {
			
			int roomComponentIndex = getIndexFromComponent(currentRoomName);
			
			add(roomCombo, roomComponentIndex);
			remove(currentRoomName);
						
			submit.setText("Accuse");
			
			revalidate();
			repaint();
			
		} else {
			
			int roomComponentIndex = getIndexFromComponent(roomCombo);
			
			add(currentRoomName, roomComponentIndex);
			remove(roomCombo);

			currentRoomName.setText(PlayablesServiceCollection.PlayerService.getCurrentPlayer().getLocation().getCurrentRoom());
			submit.setText("Suggest");
			
			revalidate();
			repaint();
			
		}

		setVisible(true);
	}
	
	
	private int getIndexFromComponent(Component component) {
		
		int index = 0;
		
		for (Component comp : getComponents()) {
			if (component.equals(comp)) break; 
			index++;
		}
		
		return index;
	}

	public void hideGuess() {
		setVisible(false);
	}
	
	private void addRoomsToCombo() {
		
		ArrayList<String> rooms = new ArrayList<String>();
		
		for (Card room : PlayablesServiceCollection.CardService.getRooms()) rooms.add(room.getName());
		
		String[] roomArray = rooms.toArray(new String[rooms.size()]);
		
		roomCombo = new JComboBox<Object>(roomArray);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
				
		Player player = PlayablesServiceCollection.PlayerService.getCurrentPlayer();
		
		if (event.getActionCommand() == "Suggest") {
						
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
		}
		
		if (event.getActionCommand() == "Accuse") {
			
			Card roomCard = PlayablesServiceCollection.CardService.getCardByName(roomCombo.getSelectedItem().toString());
			Card weaponCard = PlayablesServiceCollection.CardService.getCardByName(weaponCombo.getSelectedItem().toString());
			Card personCard = PlayablesServiceCollection.CardService.getCardByName(playerCombo.getSelectedItem().toString());
						
			player.makeAccusation(weaponCard, personCard, roomCard);
			setVisible(false);

		}	
		
		if (event.getActionCommand() == "Cancel") {
			setVisible(false);
		}
	}
}
