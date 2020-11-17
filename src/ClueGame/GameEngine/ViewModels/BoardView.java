package ClueGame.GameEngine.ViewModels;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ClueGame.Board.Entities.Cell.BoardCell;
import ClueGame.Board.Services.BoardService;
import ClueGame.Board.Services.BoardServiceCollection;
import ClueGame.GameEngine.GameEngine;
import ClueGame.GameEngine.Movement.PlayerMovementContext;

public class BoardView extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private static int _width;
	private static int _height;

	private int _numberOfCellRows;
	private int _numberOfCellColumns;
	
	private ArrayList<CellView> _cellViews;
	private ArrayList<PlayerView> _playerViews;
	
	private BoardService _boardService;
	
	private PanelListener _listener;
	
	public BoardView(int width, int height) {
		
		_cellViews = new ArrayList<CellView>();
		_playerViews = new ArrayList<PlayerView>();
		
		_boardService = BoardService.getInstance();
		
		_width = width;
		_height = height;
		
        _listener = new PanelListener();
        this.addMouseListener(_listener);
		
		gatherBoardDimensions();
		drawBoardCells();

		setLayout(new GridLayout(_numberOfCellColumns + 1, _numberOfCellRows + 1));
	}
	
	private void gatherBoardDimensions() {
		_numberOfCellRows = _boardService.getGrid().size();
		_numberOfCellColumns = _boardService.getGrid().get(0).size();
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		_width = getWidth();
		_height = getHeight();

		updateCellSize();

	}
	
	private void updateCellSize() {
		for (CellView cell : _cellViews) {
			cell.updateSize(_width / _numberOfCellColumns, _height / _numberOfCellRows);
		}
	}

	private void drawBoardCells() {
		
		int adjustedWidth = _width / _numberOfCellColumns;
		int adjustedHeight = _height / _numberOfCellRows;
		
		ArrayList<Color> colors = generateColorReal();
		int colorIndex = 0;
		
		for (BoardCell cell : _boardService.getCells()) {
			
			CellView cellView = new CellView(cell, adjustedWidth, adjustedHeight);
						
			for (PlayerMovementContext movement : GameEngine.Movement.getPlayerMovementContexts()) {
				
				if (movement.getCell() == cell) {
					
					PlayerView player = new PlayerView(movement.getPlayer(), colors.get(colorIndex));
					
					_playerViews.add(player);
					
					cellView = new CellView(cell, player, adjustedWidth, adjustedHeight);
					colorIndex++;
				}
			}
			
			cellView.addMouseListener(_listener);
			
			_cellViews.add(cellView);
			add(cellView);
		}
	}
	
	private ArrayList<Color> generateColorReal() {
		
		ArrayList<Color> colorList = new ArrayList<Color>(
				Arrays.asList(Color.MAGENTA, Color.GRAY, Color.PINK, Color.RED, Color.green, Color.YELLOW)
				);
				
		return colorList;
		
	}
	
	private void updatePlayerView(PlayerView player) {
		for (PlayerView view : _playerViews) {
			if (player.getPlayer() == view.getPlayer()) {
				
			}
		}
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
	
	public static void main(String[] args) {
		
		GameEngine gameEngine = GameEngine.getInstance();
		
		gameEngine.initializeAll();
		
		BoardView boardPanel = new BoardView(600, 600); 
		JFrame frame = new JFrame(); 
		frame.setContentPane(boardPanel); 
		frame.setSize(_width, _height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true); 
	}
	
	@Override
	public String toString() {
		return "Board: [Number of rows: " + _numberOfCellRows + " Number of columns: " + _numberOfCellColumns + "]";
	}
}
