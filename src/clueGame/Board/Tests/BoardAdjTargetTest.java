package clueGame.Board.Tests;

import static org.junit.Assert.assertEquals;



import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Exceptions.BadConfigFormatException;
import clueGame.Board.Entities.Cell.BoardCell;
import clueGame.Board.Services.BoardServiceCollection;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
//	private static ServiceCollection _serviceCollection;
	
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

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyRooms()
	{
		
		// Testing room Iceland
		Set<BoardCell> testList = BoardServiceCollection.CellService.getAdjList(18, 11);
		assertEquals(7, testList.size());
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(14, 11)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(18, 16)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(20, 6)));

		
		// Testing room Bahamas
		testList = BoardServiceCollection.CellService.getAdjList(15, 1);
	    assertEquals(1, testList.size());
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(15, 3)));
		
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		// Testing Aruba door
		Set<BoardCell> testList = BoardServiceCollection.CellService.getAdjList(8, 5);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(7, 5)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(8, 6)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(9, 5)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(8, 2)));
			
		// Testing Sicily door
		testList = BoardServiceCollection.CellService.getAdjList(19, 6);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(18, 11)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(19, 5)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(18, 6)));
	}
	
	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test left edge by Bahamas room
		Set<BoardCell> testList = BoardServiceCollection.CellService.getAdjList(17, 0);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(17, 1)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(18, 0)));
		
		// Test bottom edge under Madagascare room
		testList = BoardServiceCollection.CellService.getAdjList(25, 21);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(25, 20)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(25, 22)));

		// Test random walkway
		testList = BoardServiceCollection.CellService.getAdjList(7, 15);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(8, 15)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(6, 15)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(7, 16)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(7, 14)));
	
	}
	
	
	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInFijiRoom() {
		// test a roll of 1
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(11, 20), 1);
		Set<BoardCell> targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(8, 21)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(11, 16)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(15, 21)));	
		
		// test a roll of 3
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(11, 20), 3);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(6, 21)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(16, 22)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(12, 15)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(10, 15)));	
		
	}
	
	@Test
	public void testTargetsInGrenadaRoom() {
		
		// test a roll of 1
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(2, 13), 1);
		Set<BoardCell> targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(7, 12)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(7, 11)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(7, 10)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(2, 7)));	

		
		// test a roll of 3
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(2, 11), 3);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(19, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(4, 7)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(8, 10)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(8, 13)));
		
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		
		// test a roll of 1, at door
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(15, 3), 1);
		Set<BoardCell> targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(15, 1)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(14, 3)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(15, 4)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(16, 3)));	
		
		// test a roll of 3
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(15, 3), 3);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(15, 6)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(13, 2)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(13, 4)));

	}

	@Test
	public void testTargetsInWalkway1() {
		
		// test a roll of 1
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(13, 8), 1);
		Set<BoardCell> targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(13, 7)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(12, 8)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(13, 9)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(14, 8)));	

		
		// test a roll of 3
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(11, 2), 3);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(11, 5)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(10, 4)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(8, 2)));

	}

	@Test
	public void testTargetsOccupied() {
		
		// test a roll of 2 with occupied nearby
		BoardServiceCollection.CellService.getCell(12, 6).setOccupied(true);
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(12, 5), 2);
		BoardServiceCollection.CellService.getCell(12, 6).setOccupied(false);
		Set<BoardCell> targets = BoardServiceCollection.CellService.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(13, 6)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(11, 6)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(12, 3)));	
	
		// we want to make sure we can get into a room, even if flagged as occupied
		BoardServiceCollection.CellService.getCell(4, 3).setOccupied(true);
		BoardServiceCollection.CellService.getCell(1, 2).setOccupied(true);
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(4, 2), 1);
		BoardServiceCollection.CellService.getCell(4, 3).setOccupied(false);
		BoardServiceCollection.CellService.getCell(1, 2).setOccupied(false);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(5, 2)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(4, 1)));	
		
		// check leaving a room with a blocked doorway. Only has one door so they must use secret passage.
		BoardServiceCollection.CellService.getCell(4, 3).setOccupied(true);
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(1, 2), 2);
		BoardServiceCollection.CellService.getCell(4, 3).setOccupied(false);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(22, 21)));

	}
}
