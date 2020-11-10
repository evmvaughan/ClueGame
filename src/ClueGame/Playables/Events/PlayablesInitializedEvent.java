package ClueGame.Playables.Events;

import java.util.ArrayList;

import ClueGame.Playables.Entities.Card.Card;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Entities.Player.Guess.Solution;
import SeedWork.IEvent;

public class PlayablesInitializedEvent implements IEvent {
	
	private ArrayList<Player> _players; 
	private ArrayList<Card> _cards; 
	
	private Solution _solution;
	
	public PlayablesInitializedEvent(ArrayList<Player> players, ArrayList<Card> cards, Solution solution) {
		_players = players;
		_cards = cards;
		_solution = solution;
	}
	
	public ArrayList<Player> getPlayers() {
		return _players;
	}
	
	public ArrayList<Card> getCards() {
		return _cards;
	}
	public Solution getAnswer() {
		return _solution;
	}
}
