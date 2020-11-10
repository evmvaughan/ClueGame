package ClueGame.Board.Services;

import java.io.File;

import java.io.FileNotFoundException;

import ClueGame.Board.Entities.Cell.BoardCell;
import ClueGame.Board.Entities.Room.Room;
import Exceptions.BadConfigFormatException;
import SeedWork.ISingleton;

public class InitBoardConfiguration implements ISingleton<InitBoardConfiguration> {
	
	private static InitBoardConfiguration instance = new InitBoardConfiguration();
	
	private static RoomService _roomService;
	private static CellService _cellService;
	private static BoardService _boardService;
	
	private InitBoardConfiguration() {
		
		_roomService = BoardServiceCollection.RoomService;
		_cellService = BoardServiceCollection.CellService;
		_boardService = BoardServiceCollection.BoardService;
		
	}

	public static InitBoardConfiguration getInstance(){
	   return instance;
	}
	
	final String dataPath = "data/";
	
	private String _setupConfiguration;
	private String _layoutConfiguration;
	
	public void setConfigFiles(String layoutConfiguration, String setupConfiguration) {
		
		_layoutConfiguration = dataPath + layoutConfiguration;
		_setupConfiguration = dataPath + setupConfiguration;
		
	}
	
	/////////////////////////////////////////////////////////////
	// Initialize configurations and build game board entities //
	/////////////////////////////////////////////////////////////
	public void InitializeBoard(String layoutConfiguration, String setupConfiguration) throws FileNotFoundException, BadConfigFormatException {
		
		setConfigFiles(layoutConfiguration, setupConfiguration);
		
		loadSetupConfig();
		loadLayoutConfig();
		validateRoomsAndCells();
		
		buildGameBoard();
		
	}
	
	///////////////////////////////////////////////////////////////////////////
	// Load setup configuration and forward file to Room Service for parsing //
	///////////////////////////////////////////////////////////////////////////
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		
		System.out.println("Loading setup config: " + _setupConfiguration);
		
		File setupConfigurationFile = new File(_setupConfiguration);
		
		_roomService.createRoomsFromConfigFile(setupConfigurationFile);
				
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Load layout configuration and forward file to Cell Service for parsing //
	////////////////////////////////////////////////////////////////////////////
	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException {
		
		System.out.println("Loading layout config: " + _layoutConfiguration);
		
		File layoutConfigurationFile = new File(_layoutConfiguration);
		
		_cellService.createCellsFromLayoutFile(layoutConfigurationFile);
	}
	
	///////////////////////////////////////////////////////////
	// Perform a simple room and board cell match validation //
	///////////////////////////////////////////////////////////
	public void validateRoomsAndCells() throws BadConfigFormatException {
		
		boolean foundMatch = false;
		
		for (BoardCell cell : _cellService.getAll()) {
			for (Room room : _roomService.getAll()) {
				if (cell.getRoomId() == room.getLabel()) {
					foundMatch = true;
				}
			}
			if (!foundMatch) throw new BadConfigFormatException("Rooms and Cells do not match!");
		}
	}
	
	////////////////////////////////////////////////////////
	// Build Game Board, Rooms, Cells, Cards, and Players //
	////////////////////////////////////////////////////////
	
	public void buildGameBoard() throws BadConfigFormatException, FileNotFoundException {

		System.out.println("Building gameboard");
		
		for (Room room : _roomService.getBoardRooms()) {
			
			char currentRoomLabel = room.getLabel();
						
			for (BoardCell cell : _cellService.getAll()) {
				
				if (cell.getRoomId() == currentRoomLabel) {
					
					room.AddRoomCell(cell);
					
				}
			}
		}
		
		_boardService.addRooms(_roomService.getAll());
		_boardService.addCells(_cellService.getAll());
		_boardService.addCellGrid(_cellService.getCellGrid());
	}
}
