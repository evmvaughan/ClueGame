package ClueGame.Board.Services;

import java.util.ArrayList;

import ClueGame.Board.Entities.Board.Board;
import ClueGame.Board.Entities.Cell.BoardCell;
import ClueGame.Board.Entities.Room.Room;
import ClueGame.Playables.Entities.Card.Card;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Entities.Player.Guess.Solution;
import SeedWork.ISingleton;

public class BoardService implements ISingleton<BoardService> {
	
	private static BoardService instance = new BoardService();
	
	private Board _board;
	
	private BoardService() { 
		
		_board = Board.getInstance();
		
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
