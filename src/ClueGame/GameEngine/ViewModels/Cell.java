package ClueGame.GameEngine.ViewModels;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import ClueGame.Board.Entities.Cell.BasicCell;
import ClueGame.Board.Entities.Cell.BoardCell;
import ClueGame.Board.Entities.Cell.DoorwayCell;
import ClueGame.Board.Entities.Cell.RoomCell;
import ClueGame.Playables.Entities.Player.Player;

public class Cell extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int _width;
	private int _height;
		
	private BoardCell _boardCell;
	private Player _player;
	
	private boolean _isDoorway;
	private boolean _hasPlayer;
	
	public Cell(BoardCell cell, int adjustedWidth, int adjustedHeight) {

		_boardCell = cell;
		
		_width = adjustedWidth;
		_height = adjustedHeight;
				
        PanelListener listener = new PanelListener();
        this.addMouseListener(listener);
	}
	
	public Cell(BoardCell cell, Player player, int width, int height) {
		_boardCell = cell;
		_player = player;
		
		_hasPlayer = true;
		
		_width = width;
		_height = height;
		
        PanelListener listener = new PanelListener();
        this.addMouseListener(listener);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
				
		g.setColor(getColorFromType());
		
		Rectangle.Double rectangle = new Rectangle.Double(0, 0, _width, _height);
		((Graphics2D)g).fill(rectangle);
		((Graphics2D)g).draw(rectangle);
		
//		g.setColor(Color.BLACK);
//		g.draw3DRect(0, 0, _width, _height, true);

		if (_hasPlayer) {
			g.setColor(Color.BLACK);
	         g.fillOval(0, 0, _height, _width);
		}
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
			_isDoorway = true;
			return Color.cyan;
		}
		
		if (_boardCell instanceof RoomCell) {
			return Color.ORANGE;
		}
		
		return Color.BLACK;
		
	}
	
    private class PanelListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent event) {
            Object source = event.getSource();
            if(source instanceof JPanel){
            	System.out.println(event.getSource());
            }
        }

        @Override
        public void mouseEntered(MouseEvent arg0) {}

        @Override
        public void mouseExited(MouseEvent arg0) {}

        @Override
        public void mousePressed(MouseEvent arg0) {
        }

        @Override
        public void mouseReleased(MouseEvent arg0) {}

    }
	
	@Override
	public String toString() {
		
		if (_hasPlayer) {
			return _boardCell.toString() + "Player: " + _player.getName();
		}
		
		return _boardCell.toString();
	}
}
