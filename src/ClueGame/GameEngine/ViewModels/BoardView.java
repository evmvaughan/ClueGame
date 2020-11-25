package ClueGame.GameEngine.ViewModels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ClueGame.Board.Entities.Cell.BoardCell;
import ClueGame.Board.Entities.Room.Room;
import ClueGame.Board.Services.BoardService;
import ClueGame.Board.Services.BoardServiceCollection;
import ClueGame.GameEngine.GameEngine;
import ClueGame.GameEngine.Movement.Movement;
import ClueGame.GameEngine.Movement.PlayerMovementContext;
import ClueGame.GameEngine.Panels.ClueGameUI;
import ClueGame.GameEngine.Panels.GuessPanel;
import ClueGame.Playables.Entities.Card.Collection.CollectionType;
import ClueGame.Playables.Entities.Player.HumanPlayer;
import ClueGame.Playables.Entities.Player.LocationDTO;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Services.PlayablesServiceCollection;
import ClueGame.Playables.Services.PlayerService;

public class BoardView extends JPanel {

	private static final long serialVersionUID = 1L;

	private static int _width;
	private static int _height;

	private int _numberOfCellRows;
	private int _numberOfCellColumns;

	private ArrayList<CellView> _cellViews;
	private ArrayList<PlayerView> _playerViews;
	private ArrayList<RoomLabel> _roomLabels;

	private BoardService _boardService;
	private PlayerService _playerService;
	private Movement _movement;

	private PanelListener _listener;
	
	private GuessPanel _guessView;

	public BoardView(int width, int height) {

		_cellViews = new ArrayList<CellView>();
		_playerViews = new ArrayList<PlayerView>();
		_roomLabels = new ArrayList<RoomLabel>();

		_boardService = BoardService.getInstance();
		_playerService = PlayerService.getInstance();
		_movement = Movement.getInstance();

		_width = width;
		_height = height;

		_listener = new PanelListener();
		this.addMouseListener(_listener);

		ClueGameUI.getInstance();

		gatherBoardDimensions();

		mapBoardCellAndPlayerViews();
		
		_guessView = GuessPanel.getInstance();
		
	}

	private void drawPlayerTargetCells() {

		for (CellView cell : _cellViews)
			cell.setAsTarget(false);

		if (_playerService.getCurrentPlayer().turnLocked()) {

			ArrayList<LocationDTO> targets = _playerService.getCurrentPlayer().getTargets();

			for (LocationDTO target : targets) {

				for (CellView cell : _cellViews) {
					if (cell.getCell().getRow() == target.getCurrentRow()
							&& cell.getCell().getColumn() == target.getCurrentColumn()) {
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

		super.paintComponent(g);

		_width = getWidth();
		_height = getHeight();

		updatePlayerAndCellSize();
		drawPlayerTargetCells();
		updatePlayerLocations();

		drawCells(g);
		drawPlayers(g);
		drawRoomLabels(g);

		ClueGameUI.PlayerViews = _playerViews;
		ClueGameUI.CellViews = _cellViews;

	}

	private void drawPlayers(Graphics g) {
		for (PlayerView player : _playerViews) {
			player.draw(g);
		}
	}

	private void drawCells(Graphics g) {
		for (CellView cell : _cellViews) {
			cell.draw(g);
		}
	}

	private void drawRoomLabels(Graphics g) {
		for (RoomLabel label : _roomLabels) {
			label.draw(g);
		}
	}

	private void updatePlayerAndCellSize() {
		for (PlayerView player : _playerViews) {
			player.setAdjustedSize(_width / _numberOfCellColumns, _height / _numberOfCellRows);
		}

		for (CellView cell : _cellViews) {
			cell.setAdjustedSize(_width / _numberOfCellColumns, _height / _numberOfCellRows);
		}
	}

	private void mapBoardCellAndPlayerViews() {

		int adjustedWidth = _width / _numberOfCellColumns;
		int adjustedHeight = _height / _numberOfCellRows;

		for (BoardCell cell : _boardService.getCells()) {

			CellView cellView = new CellView(cell);

			cellView.setAdjustedSize(adjustedWidth, adjustedHeight);

			if (cell.isLabel()) {

				RoomLabel label = new RoomLabel(BoardServiceCollection.RoomService.getRoomFromCell(cell).getName(),
						cellView.getLocationX(), cellView.getLocationY());

				_roomLabels.add(label);
			}

			_cellViews.add(cellView);
		}

		ArrayList<Color> colors = generateColorReal();
		int colorIndex = 0;

		for (PlayerMovementContext movementContext : _movement.getPlayerMovementContexts()) {

			PlayerView playerView = new PlayerView(movementContext.getPlayer(), colors.get(colorIndex));

			playerView.setAdjustedSize(adjustedWidth, adjustedHeight);
			playerView.updateLocation(movementContext.getCell().getColumn(), movementContext.getCell().getRow());

			colorIndex++;

			_playerViews.add(playerView);

		}
	}

	private ArrayList<Color> generateColorReal() {

		ArrayList<Color> colorList = new ArrayList<Color>(
				Arrays.asList(Color.MAGENTA, Color.GRAY, Color.PINK, Color.RED, Color.green, Color.YELLOW));

		return colorList;

	}

	private void updatePlayerLocations() {

		for (PlayerView playerView : _playerViews) {
			PlayerMovementContext context = GameEngine.Movement.getPlayersMovementContext(playerView.getPlayer());
			playerView.updateLocation(context.getCell().getColumn(), context.getCell().getRow());

		}
	}

	////////////////////////////////
	// Handle Mouse Click Actions //
	////////////////////////////////
	private class PanelListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent event) {

			CellView clickedCell = null;

			for (CellView cellView : _cellViews) {
				if (cellView.clicked(event.getPoint().x, event.getPoint().y)) {
					clickedCell = cellView;
				}
			}
			
			if (_guessView.isVisible()) {
				_guessView.hideGuess();
			} else if (clickedCell != null) {

				Player currentPlayer = PlayablesServiceCollection.PlayerService.getCurrentPlayer();
				
				PlayerMovementContext context = _movement.getPlayersMovementContext(currentPlayer);
				
				Room currentRoom = context.getRoom();
				BoardCell currentCell = context.getCell();
				
				
				if (clickedCell.isTarget()) {

					currentPlayer.setTurnLock(false);

					BoardCell targetCell = clickedCell.getCell();
					Room targetRoom = BoardServiceCollection.RoomService.getRoomFromCell(targetCell);

					((HumanPlayer) currentPlayer).moveToTarget(
							new LocationDTO(targetRoom.getName(), targetCell.getRow(), targetCell.getColumn()));
					
					if (targetCell.isRoomCenter()) _guessView.showGuess(CollectionType.SUGGESTION);

				} else if (clickedCellIsInSameRoomAsTarget(clickedCell) != null) {
					
					currentPlayer.setTurnLock(false);

					BoardCell targetCell = clickedCellIsInSameRoomAsTarget(clickedCell).getCell();
					Room targetRoom = BoardServiceCollection.RoomService.getRoomFromCell(targetCell);

					((HumanPlayer) currentPlayer).moveToTarget(
							new LocationDTO(targetRoom.getName(), targetCell.getRow(), targetCell.getColumn()));
					
					if (targetCell.isRoomCenter()) _guessView.showGuess(CollectionType.SUGGESTION);
					
					
				} else if (clickedCell.getCell().getRoomId() == currentRoom.getSymbol() && currentCell.isRoomCenter()){ 
					_guessView.showGuess(CollectionType.SUGGESTION);

				} else {
				
					String message = "The cell you selected is not a target!";

					JOptionPane.showMessageDialog(ClueGameUI.getInstance(), message, "Clue Game Message",
							JOptionPane.INFORMATION_MESSAGE);

					System.out.println("Invalid movement!");
				}


				ClueGameUI.getInstance().updateUIComponents();
			}
		}

		private CellView clickedCellIsInSameRoomAsTarget(CellView clickedCell) {
			
			if (clickedCell.getCell().IsRoom()) {
				
				BoardCell clickedRoomCenterCell = BoardServiceCollection.RoomService.getRoomFromCell(clickedCell.getCell()).getCenterCell();
				
				for (CellView view : _cellViews) 
					if (view.getCell().isEquivalentCell(clickedRoomCenterCell.getRow(), clickedRoomCenterCell.getColumn())) 
						if (view.isTarget()) return view;
				
			}
			
			return null;
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
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
