package ClueGame.Board.Events;

import java.util.ArrayList;

import ClueGame.Board.Entities.Board.Board;
import ClueGame.Board.Entities.Cell.BoardCell;
import ClueGame.Board.Entities.Room.Room;
import SeedWork.IEvent;

public class BoardInitializedEvent implements IEvent {
	
	private Board _board; 
	private ArrayList<BoardCell> _cells; 
	private ArrayList<Room> _rooms; 
		
	public BoardInitializedEvent(Board board, ArrayList<BoardCell> cells, ArrayList<Room> rooms) {
		_board = board;
		_cells = cells;
		_rooms = rooms;
	}
	
	public  Board getBoard() {
		return _board;
	}
	
	public ArrayList<BoardCell> getCells() {
		return _cells;
	}
	public ArrayList<Room> getRooms() {
		return _rooms;
	}
}
