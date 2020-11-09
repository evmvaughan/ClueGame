package clueGame.Board.Tests;

/*
 * This program tests that, when loading config files, exceptions 
 * are thrown appropriately.
 */

import java.io.FileNotFoundException;

import org.junit.Test;
import Exceptions.BadConfigFormatException;
import clueGame.Board.Services.BoardServiceCollection;

public class ExceptionTests {

	@Test(expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		BoardServiceCollection.initialize();
		
		BoardServiceCollection.InitConfigurationService.InitializeBoard("ClueLayoutBadColumns.csv", "ClueSetup.txt");		
			

	}

	// Test that an exception is thrown for a config file that specifies
	// a room that is not in the legend. See first test for other important
	// comments.
	@Test(expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {		
		BoardServiceCollection.initialize();

		BoardServiceCollection.InitConfigurationService.InitializeBoard("ClueLayoutBadRoom.csv", "ClueSetup.txt");	
	}

	// Test that an exception is thrown for a config file with a room type
	// that is not Card or Other
	@Test(expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		BoardServiceCollection.initialize();

		BoardServiceCollection.InitConfigurationService.InitializeBoard("ClueLayout.csv", "ClueSetupBadFormat.txt");	
		
	}

}
