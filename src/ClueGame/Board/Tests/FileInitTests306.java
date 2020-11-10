package ClueGame.Board.Tests;

/*
 * This program tests that config files are loaded properly.
 */

// Doing a static import allows me to write assertEquals rather than
// Assert.assertEquals
import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ClueGame.Board.Entities.Cell.BoardCell;
import ClueGame.Board.Entities.Cell.DoorDirection;
import ClueGame.Board.Entities.Room.Room;
import ClueGame.Board.Services.BoardServiceCollection;
import Exceptions.BadConfigFormatException;

public class FileInitTests306 {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLUMNS = 24;

	// NOTE: I made Board static because I only want to set it up one
	// time (using @BeforeAll), no need to do setup before each test.
	@BeforeAll
	public static void setUp() {

		BoardServiceCollection.initialize();

		try {
			BoardServiceCollection.InitConfigurationService.InitializeBoard("ClueLayout306.csv", "ClueSetup306.txt");		
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testRoomLabels() {
		// To ensure data is correctly loaded, test retrieving a few rooms
		// from the hash, including the first and last in the file and a few others
		assertEquals("Conservatory", BoardServiceCollection.RoomService.getRoom('C').getName() );
		assertEquals("Ballroom", BoardServiceCollection.RoomService.getRoom('B').getName() );
		assertEquals("Billiard Room", BoardServiceCollection.RoomService.getRoom('R').getName() );
		assertEquals("Dining Room", BoardServiceCollection.RoomService.getRoom('D').getName() );
		assertEquals("Walkway", BoardServiceCollection.RoomService.getRoom('W').getName() );
	}

	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, BoardServiceCollection.CellService.getNumRows());
		assertEquals(NUM_COLUMNS, BoardServiceCollection.CellService.getNumColumns());
	}

	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus
	// two cells that are not a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		BoardCell cell = BoardServiceCollection.CellService.getCell(8, 7);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = BoardServiceCollection.CellService.getCell(7, 12);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = BoardServiceCollection.CellService.getCell(4, 8);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = BoardServiceCollection.CellService.getCell(16, 9);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		// Test that walkways are not doors
		cell = BoardServiceCollection.CellService.getCell(12, 14);
		assertFalse(cell.isDoorway());
	}
	

	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row = 0; row < BoardServiceCollection.CellService.getNumRows(); row++)
			for (int col = 0; col < BoardServiceCollection.CellService.getNumColumns(); col++) {
				BoardCell cell = BoardServiceCollection.CellService.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(17, numDoors);
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRooms() {
		// just test a standard room location
		BoardCell cell = BoardServiceCollection.CellService.getCell( 23, 23);
		Room room = BoardServiceCollection.RoomService.getRoomFromCell( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Kitchen" ) ;
		assertFalse( cell.isLabel() );
		assertFalse( cell.isRoomCenter() ) ;
		assertFalse( cell.isDoorway()) ;

		// this is a label cell to test
		cell = BoardServiceCollection.CellService.getCell(2, 19);
		room = BoardServiceCollection.RoomService.getRoomFromCell( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Lounge" ) ;
		assertTrue( cell.isLabel() );
		assertTrue( room.getLabelCell() == cell );
		
		// this is a room center cell to test
		cell = BoardServiceCollection.CellService.getCell(20, 11);
		room = BoardServiceCollection.RoomService.getRoomFromCell( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Ballroom" ) ;
		assertTrue( cell.isRoomCenter() );
		assertTrue( room.getCenterCell() == cell );
		
		// this is a secret passage test
		cell = BoardServiceCollection.CellService.getCell(3, 0);
		room = BoardServiceCollection.RoomService.getRoomFromCell( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Study" ) ;
		assertTrue( cell.getSecretPassage() == 'K' );
		
		// test a walkway
		cell = BoardServiceCollection.CellService.getCell(5, 0);
		room = BoardServiceCollection.RoomService.getRoomFromCell( cell ) ;
		// Note for our purposes, walkways and closets are rooms
		assertTrue( room != null );
		assertEquals( room.getName(), "Walkway" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
		
		// test a closet
		cell = BoardServiceCollection.CellService.getCell(24, 18);
		room = BoardServiceCollection.RoomService.getRoomFromCell( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Unused" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
		
	}

}
