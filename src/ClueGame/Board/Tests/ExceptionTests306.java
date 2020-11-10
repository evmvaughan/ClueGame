package ClueGame.Board.Tests;

/*
 * This program tests that, when loading config files, exceptions 
 * are thrown appropriately.
 */

import java.io.FileNotFoundException;

import org.junit.Test;

import ClueGame.Board.Services.BoardServiceCollection;
import Exceptions.BadConfigFormatException;

public class ExceptionTests306 {

	// Test that an exception is thrown for a config file that does not

	
	@Test(expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		BoardServiceCollection.initialize();

		BoardServiceCollection.InitConfigurationService.InitializeBoard("ClueLayoutBadColumns306.csv", "ClueSetup306.txt");
	}

	// Test that an exception is thrown for a config file that specifies
	// a room that is not in the legend. See first test for other important
	// comments.
	@Test(expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {	
		BoardServiceCollection.initialize();

		BoardServiceCollection.InitConfigurationService.InitializeBoard("ClueLayoutBadRoom306.csv", "ClueSetup306.txt");
	}

	// Test that an exception is thrown for a config file with a room type
	// that is not Card or Other
	@Test(expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {		
		BoardServiceCollection.initialize();

		BoardServiceCollection.InitConfigurationService.InitializeBoard("ClueLayout306.csv", "ClueSetupBadFormat306.txt");
	}

}
