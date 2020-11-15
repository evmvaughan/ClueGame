package ClueGame.GameEngine.Panels;

import java.awt.Dimension;
import java.awt.GridLayout;
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
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Services.PlayablesServiceCollection;

public class CardManagement extends JPanel {

	private static final long serialVersionUID = 1L;

	public CardManagement(Player player) {

		setLayout(new GridLayout(3, 1));
		setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		add(new Section("People").getPanel(player));
		add(new Section("Rooms").getPanel(player));
		add(new Section("Weapons").getPanel(player));
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

			if (player.getHand().getCards().size() > 0) {
				for (Card card : player.getHand().getCards()) {
					if (card.getType() == _type) {
						
						JTextField field = new JTextField(card.getName());
						field.setPreferredSize(new Dimension(140, 30));
						panel.add(field);
					}
				}
			} else {
				JTextField none = new JTextField("None");
				none.setPreferredSize(new Dimension(140, 30));

				panel.add(none);
			}

			label = new JLabel("Seen:");
			panel.add(label);

			if (player.getSeenCardsWithoutHand().size() > 0) {
				for (Card card : player.getSeenCardsWithoutHand()) {
					if (card.getType() == _type) {
						
						JTextField field = new JTextField(card.getName());
						field.setPreferredSize(new Dimension(140, 30));

						panel.add(field);
					}
				}
			} else {
				JTextField none = new JTextField("None");
				none.setPreferredSize(new Dimension(140, 30));

				panel.add(none);
			}

			return panel;
		}
	}

	public static void main(String[] args) {
		GameEngine gameEngine = GameEngine.getInstance();

		gameEngine.initializeAll();

		HumanPlayer player = (HumanPlayer) PlayablesServiceCollection.PlayerService.getPlayers().get(5);

		CardManagement panel = new CardManagement(player); 
		JFrame frame = new JFrame(); 
		frame.setContentPane(panel); 
		frame.setSize(230, 700); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true);

	}
}
