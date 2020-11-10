package ClueGame.Board.Entities.Cell;

public class DoorwayCell extends BoardCell {
	
	private DoorDirection _doorDirection;
	
	public DoorwayCell(int row, int col) {
		
		super(row, col);		

	}

	public void setDoorwayDirection(DoorDirection doorDirection) {
		
		_doorDirection = doorDirection;
		
	}

	@Override
	public String toString() {
		return super.toString() + " Doorway Direction: " + _doorDirection.toString();
	}
	
	@Override
	public DoorDirection getDoorDirection() {
		return _doorDirection;
	}
}
