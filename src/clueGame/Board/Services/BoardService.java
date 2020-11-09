package clueGame.Board.Services;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import Exceptions.BadConfigFormatException;
import SeedWork.ISingleton;
import clueGame.Board.Entities.Board.Board;
import clueGame.Board.Entities.Cell.BoardCell;
import clueGame.Board.Entities.Room.Room;
import clueGame.Playables.Entities.Card.Card;
import clueGame.Playables.Entities.Player.Player;
import clueGame.Playables.Entities.Player.Guess.Solution;

public class BoardService implements ISingleton<BoardService> {
	
	private static BoardService instance = new BoardService();
	
	private Board _board;
	
	private RoomService _roomService;
	private CellService _cellService;
	
	private InitBoardConfiguration _initConfigurationService;
	
	private BoardService() { 
		
		_board = Board.getInstance();
		
		_roomService = BoardServiceCollection.RoomService;
		_cellService = BoardServiceCollection.CellService;
		
		_initConfigurationService = BoardServiceCollection.InitConfigurationService;
		
	}
	
	//////////////////////
	// Board Entity API //
	//////////////////////
	public static BoardService getInstance(){
	   return instance;
	}
	
	public void playGameWithPlayers(ArrayList<Player> players) {
		_board.assignPlayersToBoard(players);
		
	}

	public void playGameWithCards(ArrayList<Card> cards) {
		_board.assignCardsToBoard(cards);
	}

	public void playGameWithSolution(Solution solution) {
		_board.assignSolutionToBoard(solution);
		
	}

	public Solution getSolution() {
		return _board.getSolution();
	}

	public ArrayList<Player> getPlayers() {
		return _board.getPlayers();
	}
	
	public ArrayList<Card> getCards() {
		return _board.getCards();
	}

	public void addRooms(ArrayList<Room> rooms) {
		_board.addRooms(rooms);
	}
	
	public void addCells(ArrayList<BoardCell> cells) {
		_board.addCells(cells);
	}
	
	public void addCellGrid(ArrayList<ArrayList<BoardCell>> grid) {
		_board.addCellGrid(grid);
	}
}
