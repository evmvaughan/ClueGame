package ClueGame.GameEngine.ViewModels;

import java.awt.Color;

import javax.swing.JPanel;

import ClueGame.Playables.Entities.Player.Player;

public class PlayerView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private String _name;
	private Color _color;
	
	private Player _player;
	
	public PlayerView(Player player, Color color) {
		
		_player = player;
		
		_name = player.getName();
		_color = color;
		
	}

	public String getName() {
		return _name;
	}
	
	public Color getColor() {
		return _color;
	}
}
