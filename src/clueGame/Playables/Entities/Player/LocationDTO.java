package clueGame.Playables.Entities.Player;

public class LocationDTO {
	
	private String _currentRoom;
	
	private int _currentRow;
	private int _currentColumn;
	
	public LocationDTO(String room, int row, int column) {
		_currentRoom = room;
		_currentRow = row;
		_currentColumn = column;
	}
	
	public LocationDTO(int row, int column) {
		_currentRoom = "";
		_currentRow = row;
		_currentColumn = column;
	}
	
	public int getCurrentRow() {
		return _currentRow;
	}
	
	public int getCurrentColumn() {
		return _currentColumn;
	}
	
	public String getCurrentRoom() {
		return _currentRoom;
	}
}
