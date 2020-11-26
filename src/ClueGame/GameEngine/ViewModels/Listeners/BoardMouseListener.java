package ClueGame.GameEngine.ViewModels.Listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ClueGame.Board.Entities.Cell.BoardCell;
import ClueGame.Board.Entities.Room.Room;
import ClueGame.Board.Services.BoardServiceCollection;
import ClueGame.GameEngine.Movement.Movement;
import ClueGame.GameEngine.Movement.PlayerMovementContext;
import ClueGame.GameEngine.Panels.ClueGameUI;
import ClueGame.GameEngine.Panels.GuessPanel;
import ClueGame.GameEngine.ViewModels.CellView;
import ClueGame.GameEngine.ViewModels.PlayerView;
import ClueGame.Playables.Entities.Card.Collection.CollectionType;
import ClueGame.Playables.Entities.Player.HumanPlayer;
import ClueGame.Playables.Entities.Player.LocationDTO;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Services.PlayablesServiceCollection;

public class BoardMouseListener implements MouseListener {

	private ArrayList<CellView> _cellViews;
	
	private GuessPanel _guessView;

	private Movement _movement;
	
	public BoardMouseListener() {
		_movement = Movement.getInstance();
		_guessView = GuessPanel.getInstance();
		
	}
	
	public void updateCellViews(ArrayList<CellView> cellViews) {
		_cellViews = cellViews;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent event) {

		CellView clickedCell = null;

		for (CellView cellView : _cellViews) {
			if (cellView.clicked(event.getPoint().x, event.getPoint().y)) {
				clickedCell = cellView;
			}
		}
		
		////////////////////////////////////////
		// Close Guess Panel if it is Showing //
		////////////////////////////////////////
		if (_guessView.isVisible()) {
			_guessView.hideGuess();
			
			
		} else if (clickedCell != null) {

			Player currentPlayer = PlayablesServiceCollection.PlayerService.getCurrentPlayer();
			
			PlayerMovementContext context = _movement.getPlayersMovementContext(currentPlayer);
			
			Room currentRoom = context.getRoom();
			BoardCell currentCell = context.getCell();
			
			
			//////////////////////////////////////////////////////////
			// Handle Player Movements and Perform Movement Actions //
			//////////////////////////////////////////////////////////
			if (clickedCell.isTarget()) {

				handleTargetCellMovement(currentPlayer, clickedCell.getCell());

			} else if (clickedCellIsInSameRoomAsTarget(clickedCell) != null) {
				
				BoardCell targetCell = clickedCellIsInSameRoomAsTarget(clickedCell).getCell();
				
				handleTargetCellMovement(currentPlayer, targetCell);

			} else if (clickedCell.getCell().getRoomId() == currentRoom.getSymbol() && currentCell.isRoomCenter()){
				
				currentPlayer.setTurnLock(false);

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

	
	//////////////////////////////////////////////////////////
	// Handle Player Movements and Perform Movement Actions //
	//////////////////////////////////////////////////////////
	private void handleTargetCellMovement(Player currentPlayer, BoardCell targetCell) {
		
		currentPlayer.setTurnLock(false);

		Room targetRoom = BoardServiceCollection.RoomService.getRoomFromCell(targetCell);

		((HumanPlayer) currentPlayer).moveToTarget(
				new LocationDTO(targetRoom.getName(), targetCell.getRow(), targetCell.getColumn()));
		
		if (targetCell.isRoomCenter()) _guessView.showGuess(CollectionType.SUGGESTION);
	}
	
	
	/////////////////////////////////////////////////////////////////////
	// Check if Target Cell Shares the Room With a Target, Center cell //
	/////////////////////////////////////////////////////////////////////
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
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
}

