package ClueGame.Board.Entities.Cell;

public class BasicCell extends BoardCell {

	private boolean _unused;
	
	public BasicCell(int row, int col) {
		
		super(row, col);
		
	}

	public void setUnused() {
		
		_unused = true;
		
	}
	
	@Override
	public boolean isUseable() {
		return !_unused;
	}
	
}
