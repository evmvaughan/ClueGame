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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ClueGame.Board.Entities.Cell.BoardCell;
import ClueGame.Board.Entities.Room.Room;
import ClueGame.Board.Services.BoardService;
import ClueGame.Board.Services.BoardServiceCollection;
import ClueGame.GameEngine.GameEngine;
import ClueGame.GameEngine.Movement.PlayerMovementContext;
import ClueGame.GameEngine.Panels.ClueGameUI;
import ClueGame.Playables.Entities.Player.HumanPlayer;
import ClueGame.Playables.Entities.Player.LocationDTO;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Services.PlayerService;

public class BoardView extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private static int _width;
	private static int _height;

	private int _numberOfCellRows;
	private int _numberOfCellColumns;
	
	private ArrayList<CellView> _cellViews;
	private ArrayList<PlayerView> _playerViews;
	
	private BoardService _boardService;
	private PlayerService _playerService;
	
	private PanelListener _listener;
	
	public BoardView(int width, int height) {
		
		_cellViews = new ArrayList<CellView>();
		_playerViews = new ArrayList<PlayerView>();
		
		_boardService = BoardService.getInstance();
		_playerService = PlayerService.getInstance();
		
		_width = width;
		_height = height;
		
        _listener = new PanelListener();
        this.addMouseListener(_listener);
		
		ClueGameUI.getInstance();
        
		gatherBoardDimensions();
		drawBoardCells();
		
		setLayout(new GridLayout(_numberOfCellColumns + 1, _numberOfCellRows + 1));
	}
	
	private void drawPlayerTargetCells() {
		
		for (CellView cell : _cellViews) cell.setAsTarget(false);
		
		if (_playerService.getCurrentPlayer().turnLocked()) {
			
			ArrayList<LocationDTO> targets = _playerService.getCurrentPlayer().getTargets();
			
			for  (LocationDTO target : targets) {
				
				for (CellView cell : _cellViews) {
					if (cell.getCell().getRow() == target.getCurrentRow() && cell.getCell().getColumn() == target.getCurrentColumn()) {
						cell.setAsTarget(true);
					}
				}
			}
		}		
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
		drawPlayerTargetCells();
		updatePlayerLocations();
		
		ClueGameUI.PlayerViews = _playerViews;
		ClueGameUI.CellViews = _cellViews;
		
		int cellWidth = _width / _numberOfCellColumns;
		
		g.setColor(Color.BLACK);
		Font font = new Font("Sans Serif", Font.PLAIN, 12);
		g.setFont(font);
		g.drawString("CONSERVATORY", 1*cellWidth, 2*cellWidth);

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
	
	private void updatePlayerLocations() {
		
		for (CellView cell : _cellViews) cell.clearPlayer();
		
		for (CellView cell : _cellViews) {
			
			for (PlayerMovementContext movement : GameEngine.Movement.getPlayerMovementContexts()) {
				
				if (movement.getCell() == cell.getCell()) {
					
					PlayerView playerView = null;
					
					for (PlayerView view : _playerViews) 
						if (movement.getPlayer() == view.getPlayer()) playerView = view;
	
					cell.updatePlayer(playerView);
				}
			}
		}
		
	}
	
    private class PanelListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent event) {
               	
            Object source = event.getSource();
            if(source instanceof CellView){
            	
            	if (((CellView) source).isTarget()) {
            		
            		Player player = _playerService.getCurrentPlayer();
            		
                	player.setTurnLock(false);
                	                	
                	BoardCell targetCell = ((CellView) source).getCell();
                	Room targetRoom = BoardServiceCollection.RoomService.getRoomFromCell(targetCell);
                	
                	((HumanPlayer) player).moveToTarget(new LocationDTO(targetRoom.getName(), targetCell.getRow(), targetCell.getColumn()));
                	                	
            	} else {
            		
            		String message = "The cell you selected is not a target!";
            		            		
            		JOptionPane.showMessageDialog(ClueGameUI.getInstance(), message, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
            		
            		System.out.println("Invalid movement!");
            	}
            	
        		ClueGameUI.getInstance().updateUIComponents();
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
