package SeedWork;

import java.util.ArrayList;

import Exceptions.CouldNotCreateEntityException;

public interface ILocalStorage<T> {
		
	ArrayList<T> getAll();
	void addOne(T entity) throws CouldNotCreateEntityException;
	void setAll(ArrayList<T> entities);
	
}
