package ClueGame.GameEngine.ViewModels;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
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
	
	public BoardView(int width, int height) {
		
		_cellViews = new ArrayList<CellView>();
		_playerViews = new ArrayList<PlayerView>();
		
		_boardService = BoardService.getInstance();
		
		_width = width;
		_height = height;
		
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
			_cellViews.add(cellView);
			add(cellView);
		}
	}
	
	private void drawRoomLabels(Graphics g) {
//		for (CellView cell : _cellViews) {
//			if (cell.isRoomLabel()) {
//				System.out.println(cell.getX());
//				Font font = new Font("font", Font.BOLD, 13);
//				g.setFont(font);
//				g.setColor(Color.DARK_GRAY);
//				g.drawString("fck it all", cell.getX(), cell.getY());
//				
////				cell.drawRoomLabel(g);
//			}
//		}
	}
	
	private ArrayList<Color> generateColorReal() {
		
		ArrayList<Color> colorList = new ArrayList<Color>(
				Arrays.asList(Color.MAGENTA, Color.GRAY, Color.PINK, Color.RED, Color.green, Color.YELLOW)
				);
				
		return colorList;
		
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
