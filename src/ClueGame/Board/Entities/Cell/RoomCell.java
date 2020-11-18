package ClueGame.Board.Entities.Cell;

public class RoomCell extends BoardCell{
	
	private boolean _isCenterCell; 
	private boolean _isLabelCell; 
	
	private char _secretPassageDestination;
	
	public RoomCell(int row, int col) {
		
		super(row, col);
		
		_secretPassageDestination = '!';
		
	}
	
	public void setAsCenterCell() {
		_isCenterCell = true;
	}

	public void setAsLabelCell() {
		_isLabelCell = true;
		
	}
	
	public void setSecretPassage(char secretPassageDestination) {
		_secretPassageDestination = secretPassageDestination;
	}
	
	@Override
	public boolean isLabel() {
		return _isLabelCell;
	}
	
	@Override
	public boolean isRoomCenter() {
		return _isCenterCell;
	}


	@Override
	public char getSecretPassage() {
		return _secretPassageDestination;
	}
	

}
