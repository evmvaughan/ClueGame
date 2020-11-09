package clueGame.Board.Infrastructure;

import java.util.ArrayList;

import java.util.Set;

import Exceptions.CouldNotCreateEntityException;
import Exceptions.EntityNotFoundException;
import Exceptions.NoMatchingPassageException;
import SeedWork.IEntity;
import SeedWork.ILocalStorage;
import SeedWork.ISingleton;
import clueGame.Board.Entities.Cell.*;

public class CellStorage implements ILocalStorage<BoardCell> {

	private Set<BoardCell> _targetCells;
	private ArrayList<BoardCell> _boardCells;
	private ArrayList<ArrayList<BoardCell>> _cellGrid;
	
	private static CellStorage instance = new CellStorage();

	private CellStorage() { 
		
		_boardCells = new ArrayList<BoardCell>();
		_cellGrid = new ArrayList<ArrayList<BoardCell>>();

	}

	public static CellStorage getInstance(){
	   return instance;
	}
	
	public ArrayList<ArrayList<BoardCell>> getCellGrid() {
		return _cellGrid;
	}
	
	public ArrayList<BoardCell> getAll() {
		return _boardCells;
	}
	
	public BoardCell getOne(int row, int col) throws EntityNotFoundException {
		
		for (BoardCell cell : _boardCells) {
			if (cell.isEquivalentCell(row, col)) {
				return cell;
			}
		}
		
		throw new EntityNotFoundException("Could not find cell with row: " + row + " and colomn: " + col);	
	}
	
	public void setAll(ArrayList<BoardCell> entities) {
		_boardCells = entities;
	}


	public void addOne(BoardCell cell) throws CouldNotCreateEntityException {
		
		if (cell == null) throw new CouldNotCreateEntityException("Could not create cell");
		
		_boardCells.add(cell);
		
	}
	
	
	public void addRowOfCells(ArrayList<BoardCell> cells) throws CouldNotCreateEntityException {
		
		if (cells == null || cells.size() < 1) throw new CouldNotCreateEntityException("Could not create row of cells");
		
		_cellGrid.add(cells);
	}

	public void clear() {
		_boardCells.clear();
		_cellGrid.clear();
	}

	public BoardCell getCenterCell(char roomId) {
		
		for (BoardCell cell : _boardCells) {
			if (cell.getRoomId() == roomId && cell.isRoomCenter())
				return cell;
		}
		
		return null;
	}

	public BoardCell getMatchingPassage(char roomId) {
		
		BoardCell passageCell = null;
		
		for (BoardCell cell : _boardCells) {
			if (cell.getRoomId() == roomId && cell.getSecretPassage() != '!')
				 passageCell = cell;
		}
		
		if (passageCell == null) {
			return null;
		}
		
		for (BoardCell cell : _boardCells) {
			if (cell.getRoomId() == passageCell.getSecretPassage() && cell.getSecretPassage() == roomId)
				return cell;
		}
		
		return null;
		
	}

	public ArrayList<BoardCell> getRoomDoors(char roomId) {
		
		ArrayList<BoardCell> doorCells = new ArrayList<BoardCell>();
		
		for (BoardCell cell : _boardCells) {
			if (cell.getRoomId() == roomId && cell.isDoorway())
				doorCells.add(cell);
		}
		
		return doorCells;
	}
	
	public void setTargetCells(Set<BoardCell> targetCells) {
		_targetCells = targetCells;
	}
	
	public Set<BoardCell> getTargetCells() {
		return _targetCells;
	}
	

}
