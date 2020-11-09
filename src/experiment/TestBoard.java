package experiment;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
public class TestBoard {
	
	private Set<TestBoardCell> cellSet;
	private Set<TestBoardCell> targetCells;
	private Set<TestBoardCell> visited;
	
	private TestBoardCell[][] cellArray;
	
	
	public TestBoard() {
		
		cellSet = new HashSet<TestBoardCell>();
		cellArray = new TestBoardCell[4][4];
		
		for(int row = 0; row <4; row++) {
			for(int col = 0; col < 4; col++) {
				
				TestBoardCell cell = new TestBoardCell(row, col);
				
				cellSet.add(cell);
				cellArray[row][col] = cell;
			}
		}
	}
	
	public void calcTargets(TestBoardCell startCell, int step) {
		
		visited = new HashSet<TestBoardCell>();
		targetCells = new HashSet<TestBoardCell>();
		
		findAllTargets(startCell, step);		
	}
	
	private void findAllTargets(TestBoardCell currentCell, int pathLength) {
		
		Set<TestBoardCell> adjacencyList = currentCell.getAdjList();
		
		visited.add(currentCell);
		
		Iterator<TestBoardCell> AdjacentCellIterator = adjacencyList.iterator();
		
		while (AdjacentCellIterator.hasNext()) {
			TestBoardCell adjascentCell = AdjacentCellIterator.next();
			
			if (!visited.contains(adjascentCell) && !(getCell(adjascentCell.x, adjascentCell.y).getIsOccupied())) {
				visited.add(adjascentCell);
				
				if (pathLength == 1 || getCell(adjascentCell.x, adjascentCell.y).getIsRoom()) {
					targetCells.add(adjascentCell);
				} else {
					findAllTargets(adjascentCell, pathLength-1);
				}
				visited.remove(adjascentCell);
			}
		}
	}
	
	public Set<TestBoardCell> getTargets() {
		return targetCells;
	}
	
	public TestBoardCell getCell(int row, int col) {	
		
		Iterator<TestBoardCell> cellIterator = cellSet.iterator();
		
		while (cellIterator.hasNext()) {
			TestBoardCell cell = cellIterator.next();

			if (cell.equals(new TestBoardCell(row, col))) {
				return cell;
			}
		}
		
		return cellArray[row][col];
		
	}

}
