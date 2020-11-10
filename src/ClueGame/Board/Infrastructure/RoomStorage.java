package ClueGame.Board.Infrastructure;

import java.util.ArrayList;

import ClueGame.Board.Entities.Room.*;
import Exceptions.EntityNotFoundException;
import SeedWork.ILocalStorage;

public class RoomStorage implements ILocalStorage<Room> {
	
	private ArrayList<Room> _rooms;
	
	private static RoomStorage instance = new RoomStorage();

	private RoomStorage() { _rooms = new ArrayList<Room>(); }

	public static RoomStorage getInstance(){
	   return instance;
	}
	
	public ArrayList<Room> getAll() {
		return _rooms;
	}
	
	public Room getOne(String name) throws EntityNotFoundException {
		for (Room room : _rooms) {
			if (room.getName().equals(name)) {
				return room;
			}
		}
		
		throw new EntityNotFoundException("Could not find room with name: " + name);	
	}
	
	public void setAll(ArrayList<Room> entities) {
		_rooms = entities;
	}


	public void addOne(Room entity) {
		_rooms.add(entity);
		
	}

	public Room getOneByLabel(char label) throws EntityNotFoundException {
		
		for (Room room : _rooms) {
			if (room.isEquivalentRoomByID(label)) {
				return room;
			}
		}
		
		throw new EntityNotFoundException("Could not find room with label: " + label);	
	}

	public void clear() {
		_rooms.clear();
	}
}
