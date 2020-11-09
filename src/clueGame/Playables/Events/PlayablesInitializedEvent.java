package clueGame.Playables.Events;

import java.util.ArrayList;

import SeedWork.IEvent;
import clueGame.Playables.Entities.Card.Card;
import clueGame.Playables.Entities.Player.Player;
import clueGame.Playables.Entities.Player.Guess.Solution;

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
