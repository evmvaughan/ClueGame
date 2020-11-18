package ClueGame.GameEngine.ViewModels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class RoomLabel extends JPanel{

	private static final long serialVersionUID = 1L;

	private String _roomLabel;
	
	private int _x;
	private int _y;
	
	public RoomLabel(String roomLabel, int x, int y) {
		
		_roomLabel = roomLabel;
		_x = x;
		_y = y;
	
	}

	public void draw(Graphics g) {
		
		g.setColor(Color.BLACK);
		Font font = new Font("Sans Serif", Font.BOLD, 12);
		g.setFont(font);
		g.drawString(_roomLabel, _x, _y);
	}
}
