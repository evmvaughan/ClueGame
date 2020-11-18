package ClueGame.GameEngine.ViewModels;

import javax.swing.JPanel;

public class CardView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private String _name;
	private String _color;
	
	public CardView(String name, String color) {
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
