package ClueGame.GameEngine.Panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import ClueGame.GameEngine.GameEngine;
import ClueGame.Playables.Entities.Card.Card;
import ClueGame.Playables.Entities.Card.CardType;
import ClueGame.Playables.Entities.Player.HumanPlayer;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Services.PlayablesServiceCollection;

public class CardManagement extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private HumanPlayer _player;

	public CardManagement() {
		
		_player = (HumanPlayer) PlayablesServiceCollection.PlayerService.getHumanPlayer();

		setLayout(new GridLayout(3, 1));
		setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		add(new Section("People").getPanel(_player));
		add(new Section("Rooms").getPanel(_player));
		add(new Section("Weapons").getPanel(_player));
	}


	public class Section extends JPanel {

		private static final long serialVersionUID = 1L;

		private String _section;
		private CardType _type;

		public Section(String section) {

			_section = section;

			switch (_section) {
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

		public JPanel getPanel(Player player) {
						
			
			JPanel panel = new JPanel();
								    
			panel.setBorder(new TitledBorder(new EtchedBorder(), _section));

			JLabel label = new JLabel("In Hand:", JLabel.LEFT);
			panel.add(label);

			JTextField noneHand = new JTextField("None");
			noneHand.setPreferredSize(new Dimension(140, 0));

			panel.add(noneHand);
			
			if (player.getHand().getCards().size() > 0) {
				for (Card card : player.getHand().getCards()) {
					if (card.getType() == _type) {
						JTextField field = new JTextField(card.getName());
						field.setPreferredSize(new Dimension(140, 30));
						
						panel.add(field);
						panel.remove(noneHand);

					}
				}
			} else {
				JTextField none = new JTextField("None");
				none.setPreferredSize(new Dimension(140, 30));

				panel.add(none);
			}

			label = new JLabel("Seen:");
			panel.add(label);

			JTextField noneSeen = new JTextField("None");
			noneSeen.setPreferredSize(new Dimension(140, 30));

			panel.add(noneSeen);
			
			if (player.getSeenCardsWithoutHand().size() > 0) {
				for (Card card : player.getSeenCardsWithoutHand()) {
					if (card.getType() == _type) {
						JTextField field = new JTextField(card.getName());
						field.setPreferredSize(new Dimension(140, 30));

						panel.add(field);
						panel.remove(noneSeen);
					}
				}
			} else {

			}

			return panel;
		}
	}

	public static void main(String[] args) {
		GameEngine gameEngine = GameEngine.getInstance();

		gameEngine.initializeAll();

		CardManagement panel = new CardManagement(); 
		JFrame frame = new JFrame(); 
		frame.setContentPane(panel); 
		frame.setSize(230, 700); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true);

	}

	public void updateCards() {
		for (Component comp : this.getComponents()) {
			remove(comp);
		}
		add(new Section("People").getPanel(_player));
		add(new Section("Rooms").getPanel(_player));
		add(new Section("Weapons").getPanel(_player));
		
		revalidate();
		
		ClueGameUI.getInstance().updateUIComponents();
	}
}
