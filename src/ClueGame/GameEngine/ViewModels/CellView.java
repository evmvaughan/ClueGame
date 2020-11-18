package ClueGame.GameEngine.ViewModels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;

import ClueGame.Board.Entities.Cell.BasicCell;
import ClueGame.Board.Entities.Cell.BoardCell;
import ClueGame.Board.Entities.Cell.DoorDirection;
import ClueGame.Board.Entities.Cell.DoorwayCell;
import ClueGame.Board.Entities.Cell.RoomCell;

public class CellView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private int _width;
	private int _height;
		
	private BoardCell _boardCell;
	private boolean _isRoom;
	private boolean _isDoorway;
	
	private boolean _isTarget;
	
	private DoorDirection _doorDirection;
	private boolean _isRoomLabel;
	private Color _color;

	private int _locationX;
	private int _locationY;
		
	public CellView(BoardCell cell) {

		_boardCell = cell;
		
		_color = getColorFromType();				

	}
	
	public void setAdjustedSize( int adjustedWidth, int adjustedHeight) {
		_width = adjustedWidth;
		_height = adjustedHeight;
		
		_locationX = _boardCell.getColumn() * _width;
		_locationY = _boardCell.getRow() * _height;
	}
	
	public void draw(Graphics g) {

		g.setColor(_color);
		
		Rectangle.Double rectangle = new Rectangle.Double(_locationX, _locationY, _width, _height);
		((Graphics2D)g).fill(rectangle);
		((Graphics2D)g).draw(rectangle);
		
		if (!_isRoom) {
			g.setColor(Color.BLACK);
			g.draw3DRect(_locationX, _locationY, _width, _height, true);	
		}
		
		if (_isDoorway) {
			
			paintDoorway(g);
			
		}
		
		if (_isTarget) {
			g.setColor(Color.CYAN);
			g.fillRect(_locationX, _locationY, _width, _height);
		}
		
	}
	
	private void paintDoorway(Graphics g) {
		
		g.setColor(Color.CYAN);
		
		switch(_doorDirection) {
		case DOWN:

			g.fillRect(_locationX, _locationY, _width, _height/4);

			break;
		case UP:
			g.fillRect(_locationX, _locationY + 3*_height/4, _width, _height/4);

			break;
		case RIGHT:
			g.fillRect(_locationX, _locationY, _width/4, _height);

			break;
		case LEFT:
			g.fillRect(_locationX +  3*_width/4, _locationY, _width/4, _height);

			break;
		default:
			break;
		}
	}
	
	public boolean clicked(int mouseX, int mouseY) {
		
		Rectangle rect = new Rectangle(_locationX, _locationY, _width, _height);
		if (rect.contains(new Point(mouseX, mouseY))) {
			return true;
		}
		return false;
	}

	public Color getColorFromType() {
		
		if (_boardCell instanceof BasicCell) {
			if (_boardCell.isUseable()) {
				return Color.BLUE;
			} else {
				return Color.BLACK;
			}
		}
		
		if (_boardCell instanceof DoorwayCell) {
			_isRoom = true;
			_isDoorway = true;
			_doorDirection = _boardCell.getDoorDirection();
			return Color.ORANGE;
		}
		
		if (_boardCell instanceof RoomCell) {

			_isRoom = true;
			return Color.ORANGE;
		}
		return Color.BLACK;
	}
	

	public boolean isRoomLabel() {
		return _isRoomLabel;
	}
	
	@Override
	public String toString() {
		
		return _boardCell.toString();
	}


	public BoardCell getCell() {
		return _boardCell;
	}

	public void setAsTarget(boolean isTarget) {
		_isTarget = isTarget;
	}
	
	public boolean isTarget() {
		return _isTarget;
	}


	public int getLocationX() {
		return _locationX;
	}
	
	public int getLocationY() {
		return _locationY;
	}
}
