package clueGame.Board.Entities.Room;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import clueGame.Board.Entities.Cell.BoardCell;

public class Room {
	
	private String _name;
	private char _label;	
	
	private Set<BoardCell> _roomCells;
	
	private BoardCell _labelCell;
	private BoardCell _centerCell;
	
	private Set<BoardCell> _doorCells;

	public Room(String name,char label) {
		this._name=name;
		this._label=label;
		
		_roomCells = new HashSet<BoardCell>();
		_doorCells = new HashSet<BoardCell>();
	}
	
	public char getSymbol() {
		return _label;
	}
	
	public String getName() {
		return _name;
	}

	public Set<BoardCell> getDoors() {
		return _doorCells;
	}
	
	public boolean isEquivalentRoomByName(String name) {
		return this._name == name;
	}

	public boolean isEquivalentRoomByID(char label) {
		return this._label == label;
	}

	public char getLabel() {
		return this._label;
	}
	
	public BoardCell getLabelCell() {
		return _labelCell;
	}
	
	public BoardCell getCenterCell() {
		return _centerCell;
	}

	public void AddRoomCell(BoardCell cell) {
		
		_roomCells.add(cell);

		if (cell.isLabel())
			_labelCell = cell;
		
		if (cell.isRoomCenter())
			_centerCell = cell;
		
		if (cell.isDoorway())
			_doorCells.add(cell);
			
	}
	
	@Override
	public String toString() {
		return "BoardRoom [room name: " + _name +  " label=" + _label + "]";
	}

}
