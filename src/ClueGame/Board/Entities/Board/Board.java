package ClueGame.Board.Entities.Board;
import java.util.ArrayList;


import ClueGame.Board.Entities.Cell.BoardCell;
import ClueGame.Board.Entities.Room.Room;
import ClueGame.Playables.Entities.Card.Card;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Entities.Player.Guess.Solution;
import SeedWork.IEntity;
import SeedWork.ISingleton;

public class Board implements ISingleton<Board>, IEntity {
	
	private static Board instance = new Board();
	
	private ArrayList<Room> _rooms;
	private ArrayList<BoardCell> _cells;
	private ArrayList<ArrayList<BoardCell>> _cellGrid;
	private ArrayList<Player> _players;
	private ArrayList<Card> _cards;
	
	private Solution _solution;
	
	private Board() {}

	public static Board getInstance(){
		return instance;
	}
	
	///////////////////////
	// All Board Setters //
	///////////////////////
	public void addRooms(ArrayList<Room> rooms) {
		_rooms = rooms;
	}
	
	public void addCells(ArrayList<BoardCell> cells) {
		_cells = cells;
	}
	
	public void addCellGrid(ArrayList<ArrayList<BoardCell>> boardCellGrid) {
		_cellGrid = boardCellGrid;
	}
	
	public void assignPlayersToBoard(ArrayList<Player> players) {
		_players = players;
	}

	public void assignCardsToBoard(ArrayList<Card> cards) {
		_cards = cards;
	}
	
	public void assignSolutionToBoard(Solution solution) {
		_solution = solution;
		
	}
	
	
	///////////////////////
	// All Board Getters //
	///////////////////////
	public ArrayList<Room> getRooms() {
		return _rooms;
	}
	
	public ArrayList<BoardCell> getCells() {
		return _cells;
	}
	
	public ArrayList<ArrayList<BoardCell>> getCellGrid() {
		return _cellGrid;
	}

	public Solution getSolution() {
		return _solution;
	}
	
	public ArrayList<Player> getPlayers() {
		return _players;
	}
	
	public ArrayList<Card> getCards() {
		return _cards;
	}

}
