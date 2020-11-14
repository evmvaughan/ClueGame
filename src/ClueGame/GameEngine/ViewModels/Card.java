package ClueGame.GameEngine.ViewModels;

import javax.swing.JPanel;

public class Card extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private String _name;
	private String _color;
	
	public Card(String name, String color) {
		_name = name;
		_color = color;
		
	}

	public String getName() {
		return _name;
	}
	
	public String getColor() {
		return _color;
	}
}
