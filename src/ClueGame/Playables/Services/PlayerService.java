package ClueGame.Playables.Services;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ClueGame.Playables.Entities.Player.ComputerPlayer;
import ClueGame.Playables.Entities.Player.HumanPlayer;
import ClueGame.Playables.Entities.Player.LocationDTO;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Infrastructure.PlayerStorage;
import Exceptions.BadConfigFormatException;
import Exceptions.CouldNotCreateEntityException;
import SeedWork.ISingleton;

public class PlayerService implements ISingleton<PlayerService> {
	
	private static PlayerService instance = new PlayerService();
	
	private PlayerStorage _playerStorage;
	
	private PlayerService() {
		_playerStorage = PlayerStorage.getInstance();
	}

	public static PlayerService getInstance(){
	   return instance;
	}
	
	////////////////////////
	// Player Service API //
	////////////////////////
	public void pruneStorage() {
		_playerStorage.clear();
	}
	
	public ArrayList<Player> getPlayers() {
		return _playerStorage.getAll();
	}

	public void updatePlayerLocation(String playerName, String roomName, int row, int column) {
		
		Player player = _playerStorage.getOne(playerName);
		
		player.updateLocation(new LocationDTO(roomName, row, column)); 
	}
	
	public Player getPlayerByName(String name) {
		return _playerStorage.getOne(name);
	}

	/////////////////////////////////////////////////////////////////////////
	// Create a map of all players with an index for tracking player order //
	/////////////////////////////////////////////////////////////////////////
	public Map<Integer, Player> getPlayerReal() {
		
		Map<Integer, Player> playerMap = new HashMap<Integer, Player>();
		
		int playerIndex = 0;
		
		for (Player player : getPlayers()) {
			playerMap.put(playerIndex, player);
			playerIndex++;
		}
		
		return playerMap;
	}
	
	/////////////////////////////////////////////////////////////////
	// Parse setup configuration file and create/store new players //
	/////////////////////////////////////////////////////////////////
	public void createPlayersFromConfigFile(File setupConfigurationFile) throws FileNotFoundException, BadConfigFormatException {
		
		String configurationLine;
				
		try {
			@SuppressWarnings("resource")
			BufferedReader configurationBuffer = new BufferedReader(new FileReader(setupConfigurationFile));
		
			while((configurationLine = configurationBuffer.readLine()) != null) {
				
				if (configurationLine.indexOf("Player") == 0) {
					
					String[] playerDescription = configurationLine.split(", ");
					
					if (playerDescription.length > 4) throw new BadConfigFormatException("File format does not meet specifications!");
					
					String playerType = playerDescription[1];
					String playerName = playerDescription[2];
					String playerID = playerDescription[3];

					switch(playerType) {
					case "PC":
						
						_playerStorage.addOne(new HumanPlayer(playerName, playerID));
						break;
						
					default:
						_playerStorage.addOne(new ComputerPlayer(playerName, playerID));
						break;

					}
				}
			}
			
		configurationBuffer.close();		

		if (_playerStorage.getAll().size() != 6) {
			
			throw new BadConfigFormatException("File format does not meet specifications!");
			
		}
		} catch(IOException e) {
			System.out.println("File reader error.");
		} catch (CouldNotCreateEntityException e) {
			System.out.println(e.getMessage());
		}
	}	
}