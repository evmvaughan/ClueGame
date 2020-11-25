package ClueGame.GameEngine.ViewModels;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import ClueGame.Board.Services.BoardServiceCollection;
import ClueGame.Playables.Entities.Player.Player;

public class PlayerView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private String _name;
	private Color _color;
	
	private int _width;
	private int _height;
	
	private int _locationX;
	private int _locationY;
	
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

	public Player getPlayer() {
		return _player;
	}

	public void draw(Graphics g) {
		
		g.setColor(_color);
		
		g.fillOval(_locationX, _locationY, _width, _height);
		
	}

	public void setAdjustedSize(int adjustedWidth, int adjustedHeight) {
		_width = adjustedWidth;
		_height = adjustedHeight;
	}

	public void updateLocation(int locationX, int locationY) {
		_locationX = locationX * _width;
		_locationY = locationY * _height;
	}

	public boolean isInRoom() {
		return BoardServiceCollection.CellService.getCell(_player.getLocation().getCurrentRow(),_player.getLocation().getCurrentColumn()).IsRoom();
	}
}
