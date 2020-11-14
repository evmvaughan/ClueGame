package ClueGame.GameEngine.ViewModels;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ClueGame.Board.Entities.Cell.BoardCell;
import ClueGame.Board.Services.BoardService;
import ClueGame.GameEngine.GameEngine;
import ClueGame.GameEngine.Movement.PlayerMovementContext;

public class Board extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private static final int _width = 750;
	private static final int _height = 750;

	private int _numberOfCellRows;
	private int _numberOfCellColumns;
	
	private BoardService _boardService;
	
	public Board() {
		
		_boardService = BoardService.getInstance();
		
		gatherBoardDimensions();
		
		drawBoardCells();
		setLayout(new GridLayout(_numberOfCellColumns + 1, _numberOfCellRows + 1));
	}
	
	
	private void gatherBoardDimensions() {
		_numberOfCellRows = _boardService.getGrid().size();
		_numberOfCellColumns = _boardService.getGrid().get(0).size();
		
		
		System.out.println(this);
	}

	private void drawBoardCells() {
		
		int adjustedWidth = _width / _numberOfCellRows;
		int adjustedHeight = _height / _numberOfCellColumns;
		
		boolean hasContext = false;
		
		for (BoardCell cell : _boardService.getCells()) {
			
			hasContext = false;
			
			for (PlayerMovementContext movement : GameEngine.Movement.getPlayerMovementContexts()) {
				
				if (movement.getCell() == cell) {
					add(new Cell(cell, movement.getPlayer(), adjustedWidth, adjustedHeight));
					hasContext = true;
				}
			}
			
			if (!hasContext) {
				add(new Cell(cell, adjustedWidth, adjustedHeight));
			}
		}
	}
	
	

	
	public static void main(String[] args) {
		
		GameEngine gameEngine = GameEngine.getInstance();
		
		gameEngine.initializeAll();
		
		Board boardPanel = new Board(); // create the panel
		JFrame frame = new JFrame(); // create the frame
		frame.setContentPane(boardPanel); // put the panel in the frame
		frame.setSize(_width, _height); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible	
	}
	
	@Override
	public String toString() {
		return "Board: [Number of rows: " + _numberOfCellRows + " Number of columns: " + _numberOfCellColumns + "]";
	}
}
