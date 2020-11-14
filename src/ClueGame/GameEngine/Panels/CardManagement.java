package ClueGame.GameEngine.Panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import ClueGame.GameEngine.GameEngine;
import ClueGame.Playables.Entities.Card.Card;
import ClueGame.Playables.Entities.Card.CardType;
import ClueGame.Playables.Entities.Player.HumanPlayer;
import ClueGame.Playables.Services.PlayablesServiceCollection;

public class CardManagement extends JPanel {

	private static final long serialVersionUID = 1L;

	private ArrayList<JTextField> _seenPeople;
	private ArrayList<JTextField> _seenRooms;
	private ArrayList<JTextField> _seenWeapons;

	private ArrayList<JTextField> _handPeople;
	private ArrayList<JTextField> _handRooms;
	private ArrayList<JTextField> _handWeapons;
	
	public CardManagement() {
		
		_seenPeople = new ArrayList<JTextField>();
		_seenRooms = new ArrayList<JTextField>();
		_seenWeapons = new ArrayList<JTextField>();

		_handPeople = new ArrayList<JTextField>();
		_handWeapons= new ArrayList<JTextField>();
		_handRooms = new ArrayList<JTextField>();

		
		HumanPlayer player = (HumanPlayer) PlayablesServiceCollection.PlayerService.getPlayers().get(5);
		
		ArrayList<Card> seenCards = new ArrayList<Card>();
		seenCards.addAll(PlayablesServiceCollection.CardService.getCards());
		player.setSeenCards(seenCards);
		
		setPlayerSeenPeople(PlayablesServiceCollection.PlayerService.getSeenCardsByType(player, CardType.PERSON));
		setPlayerSeenRooms(PlayablesServiceCollection.PlayerService.getSeenCardsByType(player, CardType.ROOM));
		setPlayerSeenWeapons(PlayablesServiceCollection.PlayerService.getSeenCardsByType(player, CardType.WEAPON));

		setPlayerHand(player.getHand().getCards());
					
			setLayout(new GridLayout(3, 1));
			setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
			add(new Section("People").getPanel());
			add(new Section("Rooms").getPanel());
			add(new Section("Weapons").getPanel());
		}
	
	public class Section extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		private String _section;
		private CardType _type;
		
		public Section(String section) {
			
			_section = section;
			
			switch(_section) {
			case "People":
				_type = CardType.PERSON;
				break;
			case "Rooms":
				_type = CardType.ROOM;
				break;
			default:
				_type = CardType.WEAPON;
				break;
			}
			
		}
		
		public JPanel getPanel(){
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(4, 1));
			panel.setBorder(new TitledBorder(new EtchedBorder(), _section));
			panel.add(inHand(_type));
			panel.add(inHandCards(_type));
			panel.add(seen(_type));
			panel.add(seenCards(_type));
			return panel;
		}
	}

	private JPanel inHand(CardType type) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("In Hand:", JLabel.LEFT);
		panel.add(label);
		return panel;
		
	}
	
	private JPanel inHandCards(CardType type) {
		JPanel panel = new JPanel();
		
		JTextField none = new JTextField("None", 20);
		JTextField none1 = new JTextField("Non1e", 20);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
		none.setBackground(Color.WHITE);
		none.setEditable(false);
        panel.add(none, gbc);
//        panel.add(none1, gbc);

		switch(type) {
		case PERSON:
			
			if (_handPeople.size() > 0) {
				panel.setLayout(new GridLayout(_handPeople.size(), 0));
				panel.remove(none);
			}
			
			
			for (JTextField cardData : _handPeople) {
				panel.add(cardData);
			}
			
			break;
		case ROOM:
			
			if (_handRooms.size() > 0) {
				panel.setLayout(new GridLayout(_handRooms.size(), 0));
				panel.remove(none);
			}			

			for (JTextField cardData : _handRooms) {
				panel.add(cardData);
			}
			
			break;
		case WEAPON:
			if (_handWeapons.size() > 0) {
				panel.setLayout(new GridLayout(_handWeapons.size(), 0));
				panel.remove(none);
			}			
			
			for (JTextField cardData : _handWeapons) {
				panel.add(cardData);
			}
			
			break;
		}
		
		return panel;
		
	}
	
	
	private JPanel seen(CardType type) {
		JPanel panel = new JPanel();

		JLabel label = new JLabel("Seen:");
		panel.add(label);
		return panel;
	}
	
	private JPanel seenCards(CardType type) {
		JPanel panel = new JPanel();
		
		JTextField none = new JTextField("None", 20);
		
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
		none.setBackground(Color.WHITE);
		none.setEditable(false);
        panel.add(none, gbc);
		
		switch(type) {
		case PERSON:
			panel.setLayout(new GridLayout(_seenPeople.size(), 0));
			
			panel.remove(none);
			
			for (JTextField cardData : _seenPeople) {
				panel.add(cardData);
			}
			
			break;
		case ROOM:
			panel.setLayout(new GridLayout(_seenRooms.size(), 0));
			
			panel.remove(none);

			for (JTextField cardData : _seenRooms) {
				panel.add(cardData);
			}
			
			break;
		case WEAPON:
			panel.setLayout(new GridLayout(_seenWeapons.size(), 0));
			
			panel.remove(none);
			
			for (JTextField cardData : _seenWeapons) {
				panel.add(cardData);
			}
			
			break;
		}
		
		return panel;
		
	}

	public static void main(String[] args) {
		GameEngine gameEngine = GameEngine.getInstance();
		
		gameEngine.initializeAll();
		
		HumanPlayer player = (HumanPlayer) PlayablesServiceCollection.PlayerService.getPlayers().get(5);
		
		ArrayList<Card> seenCards = new ArrayList<Card>();
		seenCards.addAll(PlayablesServiceCollection.CardService.getCards());
		player.setSeenCards(seenCards);
		
		CardManagement panel = new CardManagement(); // create the panel
		JFrame frame = new JFrame(); // create the frame
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(230, 700); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		

	}

	private void setPlayerHand(ArrayList<Card> cards) {
		
		JTextField field;
		
		for (Card card : cards) {
			CardType type = card.getType();
			
			switch(type) {
			case PERSON:
				
				field = new JTextField(10);
				field.setText(card.getName());
				_handPeople.add(field);
				
				break;
			case ROOM:
				
				field = new JTextField(10);
				field.setText(card.getName());
				_handRooms.add(field);

				break;
			case WEAPON:
				
				field = new JTextField(10);
				field.setText(card.getName());
				_handWeapons.add(field);
				
				break;
			}

		}
		
	}
	
	private void setPlayerSeenWeapons(ArrayList<Card> seenWeapons) {
		for (Card card : seenWeapons) {
			JTextField field = new JTextField(10);
			field.setText(card.getName());
			_seenWeapons.add(field);			
		}
	}
	

	private void setPlayerSeenRooms(ArrayList<Card> seenRooms) {
		for (Card card : seenRooms) {
			JTextField field = new JTextField(10);
			field.setText(card.getName());
			_seenRooms.add(field);			
		}
	}

	private void setPlayerSeenPeople(ArrayList<Card> seenPeople) {
		for (Card card : seenPeople) {
			JTextField field = new JTextField(10);
			field.setText(card.getName());
			_seenPeople.add(field);			
		}
	}
}
