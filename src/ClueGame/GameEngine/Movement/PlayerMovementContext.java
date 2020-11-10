package ClueGame.GameEngine.Movement;

import java.util.HashSet;
import java.util.Set;

import ClueGame.Board.Entities.Cell.BoardCell;
import ClueGame.Board.Entities.Room.Room;
import ClueGame.Playables.Entities.Player.Player;

public class PlayerMovementContext {
	
	private BoardCell _cell;
	private Room _room;
	private Player _player;
	
	private Set<BoardCell> _cellHistory;
	private Set<Room> _roomHistory;
	
	public PlayerMovementContext(Player player, Room room, BoardCell cell) {
		_cell = cell;
		_room = room;
		_player = player;
		
		_cellHistory = new HashSet<BoardCell>();
		_roomHistory = new HashSet<Room>();
		
	}
	
	public BoardCell getCell() {
		return _cell;
	}
	
	public Room getRoom() {
		return _room;
	}
	
	public Player getPlayer() {
		return _player;
	}

	public void addVisitedCell(BoardCell cell) {
		_cellHistory.add(cell);
	}

	public void addVisitedRoom(Room room) {
		_roomHistory.add(room);
	}
	
	public Set<BoardCell> getCellHistory() {
		return _cellHistory;
	}
	
	public Set<Room> getRoomHistory() {
		return _roomHistory;
	}
}
