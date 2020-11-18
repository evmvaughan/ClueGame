package ClueGame.Board.Services;

import java.io.BufferedReader;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ClueGame.Board.Entities.Cell.*;
import ClueGame.Board.Infrastructure.CellStorage;
import Exceptions.BadConfigFormatException;
import Exceptions.CouldNotCreateEntityException;
import Exceptions.EntityNotFoundException;
import SeedWork.ISingleton;

public class CellService implements ISingleton<CellService> {

	private static CellService instance = new CellService();
	
	private CellStorage _cellStorage;

	private CellService() {
		_cellStorage = CellStorage.getInstance();
	}

	public static CellService getInstance() {
		return instance;
	}
	
	////////////////////
	// Board Cell API //
	////////////////////
	public void pruneStorage() {
		_cellStorage.clear();
	}

	public void createCell(int row, int col) {
		BoardCell cell = new BasicCell(row, col);
		try {
			_cellStorage.addOne(cell);
		} catch (CouldNotCreateEntityException e) {
			System.out.println("Could not create cell: " + row + "," + col);
		}
	}

	public BoardCell getCell(int row, int col) {
		try {
			return _cellStorage.getOne(row, col);
		} catch (EntityNotFoundException e) {
			System.out.println("Could not retreive cell: " + row + "," + col);
		}
		return null;
	}
	
	public ArrayList<ArrayList<BoardCell>> getCellGrid() {
		return _cellStorage.getCellGrid();
	}

	public ArrayList<BoardCell> getAll() {
		return _cellStorage.getAll();
	}
	
	public Set<BoardCell> getTargets() {
		return _cellStorage.getTargetCells();
	}
	
	public int getNumRows() {
		return getCellGrid().size();
	}
	
	public int getNumColumns() {
		return getCellGrid().get(0).size();
	}
	
	///////////////////////////////////////////////////////////////////
	// Create cells from a configuration file. This will also assign // 
	// cell information, build doors, and catch any exceptions		 //
	///////////////////////////////////////////////////////////////////
	
	public void createCellsFromLayoutFile(File layoutConfigurationFile)
			throws BadConfigFormatException, FileNotFoundException {

		try {

			@SuppressWarnings("resource")
			BufferedReader configurationBuffer = new BufferedReader(new FileReader(layoutConfigurationFile));

			String configurationLine = "";

			int row = 0;
			int col = 0;

			int lastConfigurationRowLength = -1;

			while ((configurationLine = configurationBuffer.readLine()) != null) {

				String[] commalessLine = new String[configurationLine.length()];
				commalessLine = configurationLine.split(","); // Removes commas from each line
				
				if (lastConfigurationRowLength != commalessLine.length && lastConfigurationRowLength != -1) // Make sure each row is the same length
					throw new BadConfigFormatException("File format does not meet specifications!");

				lastConfigurationRowLength = commalessLine.length;

				ArrayList<BoardCell> currentBoardCellRow = new ArrayList<BoardCell>();

				for (int columnIterator = 0; columnIterator < commalessLine.length; columnIterator++) {

					col = columnIterator;

					BoardCell currentCell = assignCellAttributes(commalessLine[columnIterator], row, col);
					currentBoardCellRow.add(currentCell);
					
					_cellStorage.addOne(currentCell);
					
				}
				
				_cellStorage.addRowOfCells(currentBoardCellRow);
				row++;
			}
			configurationBuffer.close();

			assignDoorsToRooms();
		} catch (CouldNotCreateEntityException e) {
			System.out.println(e.getMessage());
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
		}
	}
	
	///////////////////////////////////////////////////////////////////
	// Set the attributes of each cell during the creation process	 //
	///////////////////////////////////////////////////////////////////

	private BoardCell assignCellAttributes(String description, int row, int col) throws BadConfigFormatException {
		
		if (description.length() < 1) { // Empty Cell
			throw new BadConfigFormatException("Cell was incorrectly formatted!");
		}

		if (description.length() > 1) {	// Special cell case
			
			if (description.contains("#")) {	// Cell is a label
				
				RoomCell cell = new RoomCell(row, col);
				cell.setAsLabelCell();
				cell.setRoomId( description.charAt(0));
				cell.setIsRoom();
				
				return cell;
				
			} else if (description.contains("*")) {		// Cell is the center of a room
				
				RoomCell cell = new RoomCell(row, col);
				cell.setAsCenterCell();	
				cell.setRoomId( description.charAt(0));
				cell.setIsRoom();

				return cell;
				
			} else if (description.contains(">") || 
					description.contains("<") ||
					description.contains("^") || 
					description.contains("v")) {	// Cell is a doorway
				
				DoorwayCell cell = new DoorwayCell(row, col);
				cell.setIsRoom();
				cell.setRoomId( description.charAt(0));
				
				char doorDirection = description.charAt(1);
				
				switch (doorDirection) {
				case '>': 
					cell.setDoorwayDirection(DoorDirection.RIGHT);
					break;
				case '<': 
					cell.setDoorwayDirection(DoorDirection.LEFT);
					break;
				case '^': 
					cell.setDoorwayDirection(DoorDirection.UP);
					break;
				default: 
					cell.setDoorwayDirection(DoorDirection.DOWN);
					break;
				}	
				
				return cell;
			} else {		// Cell is a secret passage
				
				RoomCell cell = new RoomCell(row, col);
				cell.setIsRoom();
				cell.setRoomId( description.charAt(0));
				cell.setSecretPassage( description.charAt(1));
				
				return cell;
			}
		} 
		
		if (description.contains("X")) {
			
			BasicCell cell = new BasicCell(row, col); // Cell is not special. Assign room character IDs and perform last checks
			cell.setRoomId(description.charAt(0));
			
			cell.setUnused();
			
			return cell;
			
		} else if (!description.contains("W")) {
			
			RoomCell cell = new RoomCell(row, col);	// Cell is a room cell. Create accordingly
			cell.setRoomId(description.charAt(0));
			
			cell.setIsRoom();
			
			return cell;
		}
		
		BasicCell cell = new BasicCell(row, col);	// Cell is just walkway. Create a normal instance
		cell.setRoomId(description.charAt(0));
		
		return cell;
	}
	
	
	///////////////////////////////////////////////////////////////////
	// Assign doors to rooms after they have been created and stored //
	///////////////////////////////////////////////////////////////////
	
	private void assignDoorsToRooms() throws EntityNotFoundException {
		
		for (BoardCell cell : _cellStorage.getAll()) {
			if (cell.isDoorway()) {
				
				char newRoomId = 0;
				
				switch (cell.getDoorDirection()) {
				case RIGHT:
					
					newRoomId = _cellStorage.getOne(cell.getRow(), cell.getColumn() + 1).getRoomId();

					cell.setRoomId(newRoomId);
					
					break;
					
				case LEFT:
					
					newRoomId = _cellStorage.getOne(cell.getRow(), cell.getColumn() - 1).getRoomId();

					cell.setRoomId(newRoomId);
					
					break;
					
				case DOWN:
					
					newRoomId = _cellStorage.getOne(cell.getRow() + 1, cell.getColumn()).getRoomId();

					cell.setRoomId(newRoomId);
					
					break;
					
				case UP:
					
					newRoomId = _cellStorage.getOne(cell.getRow() - 1, cell.getColumn()).getRoomId();

					cell.setRoomId(newRoomId);
					
					break;

				default:					
					break;
				}	
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////
	//  Build the adjacency lists from the input cell				 //
	///////////////////////////////////////////////////////////////////

	public Set<BoardCell> getAdjList(BoardCell cell) { return getAdjList(cell.getRow(), cell.getColumn()); }
	
	public Set<BoardCell> getAdjList(int row, int col) {
		
		int maxRows = getCellGrid().size() - 1;
		int maxCols = getCellGrid().get(0).size() - 1;
		
		Set<BoardCell> adjacencyList =new HashSet<BoardCell>();
		
		//first check if the board cell is in a room
		BoardCell currentCell = getCell(row, col);
		
		if (currentCell instanceof RoomCell) { //we have to be in a room
						
			if (currentCell.isRoomCenter()) {// check if its a room center or a secret passage
				
				if (currentCell.getSecretPassage() != '!') {//secret passage
					
					adjacencyList.add(_cellStorage.getCenterCell(currentCell.getRoomId()));
					
				}else {//we still need to add the other secret passage
					
					if (_cellStorage.getMatchingPassage(currentCell.getRoomId()) != null) {
						
						BoardCell matchingPassage = _cellStorage.getMatchingPassage(currentCell.getRoomId());
						
						adjacencyList.add(_cellStorage.getCenterCell(matchingPassage.getRoomId()));
					}
				}
			}
			
			//now we add the doors
			
			ArrayList<BoardCell> doorCells = _cellStorage.getRoomDoors(currentCell.getRoomId());
			
			for (BoardCell cell : doorCells)
				adjacencyList.add(cell);

			
		}else {//we just add a maximum of 4 up, down left and right
			if (row != 0) {//can go up
				
				if (isValidMovement(row - 1, col)) {
					
					adjacencyList.add(getCell(row - 1, col));
					
				}
			}
			if(col != 0) {//can go left
				
				if(isValidMovement(row, col - 1)) {
					
					adjacencyList.add(getCell(row, col - 1));
					
				}
			}
			if (row != maxRows) {//can go down
				
				if (isValidMovement(row + 1, col)) {
					
					adjacencyList.add(getCell(row + 1, col));
					
				}
			}
			if(col != maxCols) {//can go right
				
				if(isValidMovement(row, col + 1)) {
					
					adjacencyList.add(getCell(row, col + 1));
					
				}
			}
			if (currentCell.isDoorway()) {
				
				adjacencyList.add(_cellStorage.getCenterCell(currentCell.getRoomId()));
				
			}
		}
				
		return adjacencyList;
	}
	
	/////////////////////////////////////////////////////////////////////////
	// Calculate target cells based on a starting cell and number of steps //
	/////////////////////////////////////////////////////////////////////////
	
	public void calcTargets(BoardCell startCell, int steps) {
		
		Set<BoardCell> visited = new HashSet<BoardCell>();
		Set<BoardCell> targetCells = new HashSet<BoardCell>();
		
		findAllTargets(startCell, steps, visited, targetCells);	
		
		_cellStorage.setTargetCells(targetCells);
		
	}

	private void findAllTargets(BoardCell currentCell, int pathLength, Set<BoardCell> visited, Set<BoardCell> targetCells) {
		
		Set<BoardCell> adjacencyList = getAdjList(currentCell);
		
		visited.add(currentCell);
		
		Iterator<BoardCell> AdjacentCellIterator = adjacencyList.iterator();
		
		while (AdjacentCellIterator.hasNext()) {
			BoardCell adjascentCell = AdjacentCellIterator.next();
			
			if (!visited.contains(adjascentCell) && !(getCell(adjascentCell.getRow(), adjascentCell.getColumn()).isOccupied())) {
				visited.add(adjascentCell);
							
				if (pathLength == 1 || getCell(adjascentCell.getRow(), adjascentCell.getColumn()) instanceof RoomCell) {
					
					targetCells.add(adjascentCell);
					
				} else {
					
					findAllTargets(adjascentCell, pathLength-1, visited, targetCells);
				}
				
				visited.remove(adjascentCell);
				
			} else if (getCell(adjascentCell.getRow(), adjascentCell.getColumn()).isRoomCenter() &&
					pathLength == 1) {
				
				targetCells.add(adjascentCell);
			}
		}		
	}
	
	///////////////////////////////////////////////////////////////////
	// Check that the adjacent movement is valid					 //
	///////////////////////////////////////////////////////////////////
	
	private boolean isValidMovement(int row, int col) {
		
		BoardCell cell = getCell(row, col);
		
		return cell instanceof BasicCell 
				&& cell.isUseable()
				&& !cell.isOccupied()
				|| cell.isDoorway();
	}
}
