package clueGame.Board.Services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Exceptions.NoMatchingPassageException;
import Exceptions.BadConfigFormatException;
import Exceptions.EntityNotFoundException;
import SeedWork.ISingleton;
import clueGame.Board.Entities.Cell.BoardCell;
import clueGame.Board.Entities.Room.Room;
import clueGame.Board.Infrastructure.RoomStorage;

public class RoomService implements ISingleton<RoomService> {
	
	private static RoomService instance = new RoomService();
	
	private RoomStorage _roomStorage;

	private RoomService() { _roomStorage = RoomStorage.getInstance(); }
	
	public static RoomService getInstance(){
	   return instance;
	}
	
	//////////////////////
	// Room Service API //
	//////////////////////
	public void pruneStorage() {
		_roomStorage.clear();
	}

	public ArrayList<Room> getBoardRooms() {
		return _roomStorage.getAll();	
	}

	public Room getRoomFromCell(BoardCell cell) {
		
		for (Room room : _roomStorage.getAll())
			if (room.getLabel() == cell.getRoomId()) return room;
		
		return null;
	}

	public Room getRoom(char label) {
		try {
			return _roomStorage.getOneByLabel(label);
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public ArrayList<Room> getAll() {
		return _roomStorage.getAll();
	}
	
	public Room getRoomFromName(String roomName) {
		try {
			return _roomStorage.getOne(roomName);
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	//////////////////////////////////////////////////////////
	// Create room entities from config file and store them //
	//////////////////////////////////////////////////////////
	
	public void createRoomsFromConfigFile(File setupConfigurationFile) throws FileNotFoundException, BadConfigFormatException {
		
		String configurationLine;
				
		try {
			BufferedReader configurationBuffer = new BufferedReader(new FileReader(setupConfigurationFile));
		
			while((configurationLine = configurationBuffer.readLine()) != null) {
						
				String roomName = "";
				
				if (configurationLine.indexOf("Room") == 0 || configurationLine.indexOf("Space") == 0) {
					for (int j = configurationLine.indexOf(',')+2; configurationLine.charAt(j)!=',';j++) {
						
						roomName += configurationLine.charAt(j);
						
					}

					_roomStorage.addOne(new Room(roomName, configurationLine.charAt(configurationLine.length()-1)));
				}
			}
			
		configurationBuffer.close();		

		if (_roomStorage.getAll().size() != 11) {
			
			throw new BadConfigFormatException("File format does not meet specifications!");
			
		}
		} catch(IOException e) {
			
			System.out.println("File reader error.");
			
		}
	}
}
