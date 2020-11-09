package clueGame.Playables.Services;

import java.io.File;
import java.io.FileNotFoundException;

import Exceptions.BadConfigFormatException;
import SeedWork.ISingleton;
import clueGame.Board.Services.BoardServiceCollection;
import clueGame.Board.Services.InitBoardConfiguration;

public class InitPlayableConfiguration implements ISingleton<InitPlayableConfiguration> {
	
	private static InitPlayableConfiguration instance = new InitPlayableConfiguration();
	
	private InitPlayableConfiguration() {}

	public static InitPlayableConfiguration getInstance(){
	   return instance;
	}
	
	final String dataPath = "data/";
	
	private String _setupConfiguration;
	
	public void setConfigFile(String setupConfiguration) {
		
		_setupConfiguration = dataPath + setupConfiguration;
		
	}
	
	//////////////////////////////////////////////////////////////////////////
	// Load the setup configuration and initialize player and card creation //
	//////////////////////////////////////////////////////////////////////////
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
				
		File setupConfigurationFile = new File(_setupConfiguration);
		
		PlayablesServiceCollection.PlayerService.createPlayersFromConfigFile(setupConfigurationFile);
		PlayablesServiceCollection.CardService.createCardsFromConfigFile(setupConfigurationFile);		
	}

	////////////////////////////////////////////////
	// Initialize playable entities configuration //
	////////////////////////////////////////////////
	public void initializePayables(String setupConfiguration) throws FileNotFoundException, BadConfigFormatException {
		
		setConfigFile(setupConfiguration);
		
		loadSetupConfig();
	}
}