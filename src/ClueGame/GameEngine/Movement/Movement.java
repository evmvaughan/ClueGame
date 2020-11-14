package ClueGame.GameEngine.Movement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import ClueGame.Board.Entities.Cell.BoardCell;
import ClueGame.Board.Entities.Room.Room;
import ClueGame.Board.Services.BoardServiceCollection;
import ClueGame.Board.Services.CellService;
import ClueGame.Board.Services.RoomService;
import ClueGame.Playables.Entities.Player.ComputerPlayer;
import ClueGame.Playables.Entities.Player.LocationDTO;
import ClueGame.Playables.Entities.Player.Player;

public class Movement {

	private static Movement instance = new Movement();
	
	private ArrayList<PlayerMovementContext> _playerMovementContexts;
	
	private CellService _cellService;
	private RoomService _roomService;

	private Movement() {
		_cellService = BoardServiceCollection.CellService;
		_roomService = BoardServiceCollection.RoomService;
		_playerMovementContexts = new ArrayList<PlayerMovementContext>();
	}

	public static Movement getInstance() {
		return instance;
	}
	
	/////////////////////////////////////////////////////////////
	// Initialize player starting position on board and create //
	// a new player movement context that holds movement info. //
	/////////////////////////////////////////////////////////////
	public void initializePlayerAtLocation(Player player, int row, int column) {
		
		BoardCell cell = _cellService.getCell(row, column);
		Room room = _roomService.getRoomFromCell(cell);
		
		player.updateLocation(new LocationDTO(room.getName(), row, column));
		
		PlayerMovementContext playerMovementContext = new PlayerMovementContext(player, room, cell);
		
		_playerMovementContexts.add(playerMovementContext);
	}

	//////////////////////////////////////////////////////////
	// Update player movement contexts after a player moves //
	//////////////////////////////////////////////////////////
	public void updatePlayerMovementContext(Player player, LocationDTO newLocation) {
				
		PlayerMovementContext selectedContext = null;
		
		for (PlayerMovementContext movementContext : _playerMovementContexts) {
			
			if (player == movementContext.getPlayer()) {
				selectedContext = movementContext;
				break;
			}
		}
		
		BoardCell cell = _cellService.getCell(newLocation.getCurrentRow(), newLocation.getCurrentColumn());
		Room room = _roomService.getRoomFromName(newLocation.getCurrentRoom());
		
		selectedContext.addVisitedCell(cell);
		selectedContext.addVisitedRoom(room);
	}

	///////////////////////////////////////////////////////////////////////////////////
	// Fetch the movement context for the specified player and generate a new target //
	///////////////////////////////////////////////////////////////////////////////////
	public void getSelectedTargetsForPlayer(ComputerPlayer player, int step) {
		
		PlayerMovementContext playerMovementContext = null;
		
		for (PlayerMovementContext movementContext : _playerMovementContexts) {
			
			if (player == movementContext.getPlayer()) {
				playerMovementContext = movementContext;
				break;
			}
		}

		BoardCell targetCell = getTargetCellFromMovementContext(playerMovementContext, step);
		Room targetRoom = _roomService.getRoomFromCell(targetCell);
		
		player.setTarget(targetCell, targetRoom);
	}
	
	////////////////////////////////////////////////////////////////////////////////
	// Generate a target cell and room given a player's movement context and step //
	////////////////////////////////////////////////////////////////////////////////
	private BoardCell getTargetCellFromMovementContext(PlayerMovementContext playerMovementContext, int step) {
		
		BoardCell currentCell = playerMovementContext.getCell();
		_cellService.calcTargets(currentCell, step);
		Set<BoardCell> targets = _cellService.getTargets();
		
		ArrayList<BoardCell> randomTargets = new ArrayList<BoardCell>();
		
		Iterator<BoardCell> cellIterator = targets.iterator();
		
		while (cellIterator.hasNext()) {
			
			BoardCell cell = cellIterator.next();
			randomTargets.add(cell);
			
			if (cell.isRoomCenter() && isUniqueRoomForPlayer(playerMovementContext, _roomService.getRoomFromCell(cell))) {
				return cell; // Return unique room center
			}
		}
		
		BoardCell randomCell = randomTargets.get(new Random().nextInt(randomTargets.size())); // Get a random cell from targets
		
		return randomCell;
	}
	
	/////////////////////////////////////////////////////////////
	// Check for room uniqueness given player movement context //
	/////////////////////////////////////////////////////////////
	private boolean isUniqueRoomForPlayer(PlayerMovementContext playerMovementContext, Room room) {
		
		Iterator<Room> roomIterator = playerMovementContext.getRoomHistory().iterator();
		
		while (roomIterator.hasNext()) {
			
			if (room == roomIterator.next()) {
				return false;
			}
		}
		
		return true;
	}

	public ArrayList<PlayerMovementContext> getPlayerMovementContexts() {
		return _playerMovementContexts;
	}

}
