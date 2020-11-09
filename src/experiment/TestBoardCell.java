package experiment;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class TestBoardCell {
	
	public int x;
	public int y;
	
	private boolean isOccupied;
	private boolean isRoom;

	
	private Set<TestBoardCell> adjacencyList;
	
	
	public TestBoardCell(int x, int y) {
		
		this.x = x;
		this.y = y;
		
		adjacencyList = new HashSet<TestBoardCell>();
	}

	public Set<TestBoardCell> getAdjList() {
				
		if (y!=3) {//we can go down
			adjacencyList.add(new TestBoardCell(x, y+1));
		}
		
		if (x!=3) {//we can go right
			adjacencyList.add(new TestBoardCell(x+1, y));
		}
		
		if (y!=0) {//we can go left
			adjacencyList.add(new TestBoardCell(x, y-1));
		}
		
		if (x!=0) {//we cannot go up
			adjacencyList.add(new TestBoardCell(x-1, y));
		}
		
		return adjacencyList;
		
	}
	
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public boolean getIsRoom() {
		
		return isRoom;
	}
	
	public void setIsOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean getIsOccupied() {
		return isOccupied;
	}
	
	
	// Generate custom hash code for cell comparison
	@Override
	public int hashCode(){
		return x + y;
	}
	
	// Override equals for cell comparison
	@Override
	public boolean equals(Object obj) {
				
		if(obj instanceof TestBoardCell){
			TestBoardCell toCompare = (TestBoardCell) obj;
			    
			return toCompare.x == this.x && toCompare.y == this.y;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "TestBoardCell [x=" + x + ", y=" + y + ", isOccupied=" + isOccupied + ", isRoom=" + isRoom + "]";
	}

}
