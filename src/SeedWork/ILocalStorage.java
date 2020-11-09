package SeedWork;

import java.util.ArrayList;

import Exceptions.CouldNotCreateEntityException;
import Exceptions.EntityNotFoundException;
import clueGame.Board.Entities.Cell.BoardCell;

public interface ILocalStorage<T> {
		
	ArrayList<T> getAll();
	void addOne(T entity) throws CouldNotCreateEntityException;
	void setAll(ArrayList<T> entities);
	
}
