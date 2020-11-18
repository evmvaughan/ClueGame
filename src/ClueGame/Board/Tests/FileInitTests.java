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

public class FileInitTests {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 26;
	public static final int NUM_COLUMNS = 25;

	// NOTE: I made Board static because I only want to set it up one
	// time (using @BeforeAll), no need to do setup before each test.

	@BeforeAll
	public static void setUp() {
		BoardServiceCollection.initialize();

		try {
			BoardServiceCollection.InitConfigurationService.InitializeBoard("ClueLayout.csv", "ClueSetup.txt");		
			
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
		assertEquals("Cuba", BoardServiceCollection.RoomService.getRoom('C').getName() );
		assertEquals("Bahamas", BoardServiceCollection.RoomService.getRoom('B').getName() );
		assertEquals("Aruba", BoardServiceCollection.RoomService.getRoom('A').getName() );
		assertEquals("Sicily", BoardServiceCollection.RoomService.getRoom('S').getName() );
		assertEquals("Water", BoardServiceCollection.RoomService.getRoom('W').getName() );
		assertEquals("Fiji", BoardServiceCollection.RoomService.getRoom('F').getName());
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
		BoardCell cell = BoardServiceCollection.CellService.getCell(8, 5);
		assertTrue(cell.isDoorway());	
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = BoardServiceCollection.CellService.getCell(4, 3);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = BoardServiceCollection.CellService.getCell(4,19 );
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = BoardServiceCollection.CellService.getCell(14, 11);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		// Test that walkways are not doors
		cell = BoardServiceCollection.CellService.getCell(10, 9);
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
		Assert.assertEquals(26, numDoors);// that might be wrong
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRooms() {
		// just test a standard room location
		BoardCell cell = BoardServiceCollection.CellService.getCell( 2, 2);
		Room room = BoardServiceCollection.RoomService.getRoomFromCell( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Cuba" ) ;
		assertFalse( cell.isLabel() );
		assertFalse( cell.isRoomCenter() ) ;
		assertFalse( cell.isDoorway()) ;

		// this is a label cell to test
		cell = BoardServiceCollection.CellService.getCell(16, 1);
		room = BoardServiceCollection.RoomService.getRoomFromCell( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Bahamas" ) ;
		assertTrue( cell.isLabel() );
//		System.out.println(room.getLabelCell() + "Cell2: " + cell);
		assertTrue( room.getLabelCell() == cell );
		
		// this is a room center cell to test
		cell = BoardServiceCollection.CellService.getCell(11, 20);
		room = BoardServiceCollection.RoomService.getRoomFromCell( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Fiji" ) ;
		assertTrue( cell.isRoomCenter() );
		assertTrue( room.getCenterCell() == cell );
		
		// this is a secret passage test
		cell = BoardServiceCollection.CellService.getCell(1, 21);
		room = BoardServiceCollection.RoomService.getRoomFromCell( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Iceland" ) ;
		assertTrue( cell.getSecretPassage() == 'J' );
		
		// test a walkway
		cell = BoardServiceCollection.CellService.getCell(4, 5);
		room = BoardServiceCollection.RoomService.getRoomFromCell( cell ) ;
		// Note for our purposes, walkways and closets are rooms
		assertTrue( room != null );
		assertEquals( room.getName(), "Water" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
		
		// test a closet
		cell = BoardServiceCollection.CellService.getCell(10, 11);
		room = BoardServiceCollection.RoomService.getRoomFromCell( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Unused" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
		
	}
	
	@Test
	public void testspecial() {
		BoardCell cell = BoardServiceCollection.CellService.getCell( 2, 1);
		assertTrue(cell.isLabel());
		cell = BoardServiceCollection.CellService.getCell(1, 2);
		assertTrue(cell.isRoomCenter());
		cell = BoardServiceCollection.CellService.getCell(0, 1);
		assertEquals(cell.getSecretPassage(),'M');
	}
}
