package ClueGame.Playables.Infrastructure;

import java.util.ArrayList;

import ClueGame.Playables.Entities.Player.Player;
import Exceptions.CouldNotCreateEntityException;
import SeedWork.ILocalStorage;

public class PlayerStorage implements ILocalStorage<Player> {

	private ArrayList<Player> _players;
			
	private static PlayerStorage instance = new PlayerStorage();
	

	private PlayerStorage() { 
		_players = new ArrayList<Player>();
	}
	
	public static PlayerStorage getInstance() {
		return instance;
	}

	public ArrayList<Player> getAll() {
		return _players;
	}

	public void addOne(Player player) throws CouldNotCreateEntityException {
		
		if (player == null) throw new CouldNotCreateEntityException("Could not create player");
		
		_players.add(player);
		
	}

	public void setAll(ArrayList<Player> players) {
		_players = players;
	}

	public void clear() {
		_players.clear();
	}

	public Player getOne(String name) {
		for (Player player : _players) 
			if (player.getName().equals(name)) return player;
		return null;
	}
}