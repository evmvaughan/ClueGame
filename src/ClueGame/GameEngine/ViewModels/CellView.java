package ClueGame.GameEngine.ViewModels;

import java.awt.*;

import javax.swing.JPanel;

import ClueGame.Board.Entities.Cell.BasicCell;
import ClueGame.Board.Entities.Cell.BoardCell;
import ClueGame.Board.Entities.Cell.DoorDirection;
import ClueGame.Board.Entities.Cell.DoorwayCell;
import ClueGame.Board.Entities.Cell.RoomCell;
import ClueGame.Board.Services.BoardServiceCollection;

public class CellView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int _width;
	private int _height;
		
	private BoardCell _boardCell;
	private PlayerView _playerView;
	
	private boolean _hasPlayer;
	
	private boolean _isRoom;
	private boolean _isDoorway;
	
	private boolean _isTarget;
	
	private DoorDirection _doorDirection;
	private int _doorShiftX;
	private int _doorShiftY;
	
	private boolean _isRoomLabel;
	private String _roomLabel;
	
	private Color _color;
		
	public CellView(BoardCell cell, int adjustedWidth, int adjustedHeight) {

		_boardCell = cell;
		
		_color = getColorFromType();
		
		_width = adjustedWidth;
		_height = adjustedHeight;
				

	}
	
	public CellView(BoardCell cell, PlayerView playerView, int width, int height) {
		_boardCell = cell;
		_playerView = playerView;
		
		_hasPlayer = true;
		
		_width = width;
		_height = height;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(_color);
		
		Rectangle.Double rectangle = new Rectangle.Double(0, 0, _width, _height);
		((Graphics2D)g).fill(rectangle);
		((Graphics2D)g).draw(rectangle);
		
		if (!_isRoom) {
			g.setColor(Color.BLACK);
			g.draw3DRect(0, 0, _width, _height, true);	
		}
		
		if (_isDoorway) {
			g.setColor(Color.CYAN);
			((Graphics2D) g).rotate(Math.toRadians(getRotationFromDirection()));
			g.fillRect(_doorShiftX, _doorShiftY, _width, _height/4);
		}

		if (_hasPlayer) {
			g.setColor(_playerView.getColor());
	         g.fillOval(0, 0, _width, _height);
		}
		
		if (_isRoomLabel) {
			
			Font font = new Font("font", Font.BOLD, 10);
			g.setFont(font);
			g.setColor(Color.DARK_GRAY);
			g.drawString(_roomLabel, 0, 0);

		}
		
		if (_isTarget) {
			g.setColor(Color.CYAN);
			g.fillRect(0, 0, _width, _height);
		}
	}
	
	
	private double getRotationFromDirection() {
		
		double rotation = 0;
		
		switch(_doorDirection) {
		case DOWN:
			rotation = 0;
			_doorShiftX = 0;
			_doorShiftY = 0;
			break;
		case UP:
			rotation = 0;
			_doorShiftX = 0;
			_doorShiftY = 3*_height/4;
			break;
		case RIGHT:
			rotation = 90;
			_doorShiftX = 0;
			_doorShiftY = -_width/4;
			break;
		case LEFT:
			rotation = 90;
			_doorShiftX = 0;
			_doorShiftY = -_width;
			break;
		}
		return rotation;
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
			if (_boardCell.isLabel()) {
				_isRoomLabel = true;
				_roomLabel = BoardServiceCollection.RoomService.getRoomFromCell(_boardCell).getName();
				
			}
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
		
		if (_hasPlayer) {
			return _boardCell.toString() + "Player: " + _playerView.getName();
		}
		
		return _boardCell.toString();
	}

	public void updateSize(int width, int height) {
		_width = width;
		_height = height;
		
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

	public void clearPlayer() {
		_playerView = null;
		_hasPlayer = false;
	}

	public void updatePlayer(PlayerView playerView) {
		_playerView = playerView;
		_hasPlayer = true;
	}
}
