package ClueGame.Board.Entities.Cell;

import java.util.Set;

import SeedWork.IEntity;

public abstract class BoardCell implements IEntity {
				
	private int _row;
	private int _col;
		
	private char _roomId;
	
	private Set<BoardCell> _adjacencyList;
	private boolean _isOccupied;
	private boolean _isRoom;
	
	public BoardCell(int row, int col) {
		
		this._row = row;
		this._col = col;
		
	}
	
	public void setRoomId(char roomId) {
		_roomId = roomId;
	}

	public void setOccupied(boolean occupied) {
		this._isOccupied = occupied;
	}
	
	public void setIsRoom() {
		this._isRoom = true;
	}
	
	public boolean isEquivalentCell(int row, int col) {
		return this._row == row && this._col == col;
	}
	
	public void setAdjacencyList(Set<BoardCell> adjacencyList) {
		_adjacencyList = adjacencyList;
	}
	
	public Set<BoardCell> getAdjacencyList() {
		return _adjacencyList;
	}

	public boolean isDoorway() {
		return this instanceof DoorwayCell;
	}

	public boolean IsRoom() {
		
		return _isRoom;
		
	}

	public char getRoomId() {
		return _roomId;
	}

	public int getRow() {
		return _row;
	}
	
	public int getColumn() {
		return _col;
	}

	public boolean isOccupied() {
		return _isOccupied;
	}
	
	public DoorDirection getDoorDirection() {
		
		if (this instanceof DoorwayCell) {
			return this.getDoorDirection();
		}
		
		return DoorDirection.NONE;
	}

	public boolean isLabel() {
		
		if (this instanceof RoomCell) {
			return this.isLabel();
		}
		
		return false;
	}

	public boolean isRoomCenter() {
		
		if (this instanceof RoomCell) {
			return this.isRoomCenter();
		}
		
		return false;
	}
	
	public char getSecretPassage() {
		
		if (this instanceof RoomCell) {
			return this.getSecretPassage();
		}
		
		return '!';
	}
	
	public boolean isUseable() {
		
		if (this instanceof BasicCell) {
			return this.isUseable();
		}
		
		return true;
	}
	
	
	@Override
	public String toString() {
		return "BoardCell [roomID: " + _roomId +  " row=" + _row + ", col=" + _col + ", isOccupied=" + this._isOccupied + ", isRoom=" + this._isRoom + " isCenterCell=" + this.isRoomCenter() + "]";
	}
}
