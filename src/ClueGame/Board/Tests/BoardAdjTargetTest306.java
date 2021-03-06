package ClueGame.Board.Tests;

import static org.junit.Assert.assertEquals;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ClueGame.Board.Entities.Cell.BoardCell;
import ClueGame.Board.Services.BoardServiceCollection;
import Exceptions.BadConfigFormatException;

public class BoardAdjTargetTest306 {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	
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

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, the study that only has a single door but a secret room
		Set<BoardCell> testList = BoardServiceCollection.CellService.getAdjList(2, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(4, 6)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(20, 19)));
		
		// now test the ballroom (note not marked since multiple test here)
		testList = BoardServiceCollection.CellService.getAdjList(20, 11);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(16, 9)));
		
		// one more room, the kitchen
		testList = BoardServiceCollection.CellService.getAdjList(20, 19);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(17, 18)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(2, 2)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = BoardServiceCollection.CellService.getAdjList(11, 1);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(14, 2)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(11, 2)));

		testList = BoardServiceCollection.CellService.getAdjList(19, 5);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(21, 2)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(18, 5)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(19, 6)));
		
		testList = BoardServiceCollection.CellService.getAdjList(19, 7);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(18, 7)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(19, 6)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(20, 7)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(20, 11)));
	}
	
	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = BoardServiceCollection.CellService.getAdjList(24, 14);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(23, 14)));
		
		// Test near a door but not adjacent
		testList = BoardServiceCollection.CellService.getAdjList(18, 4);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(18, 3)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(17, 4)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(18, 5)));

		// Test adjacent to walkways
		testList = BoardServiceCollection.CellService.getAdjList(19, 6);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(19, 5)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(19, 7)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(18, 6)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(20, 6)));

		// Test next to closet
		testList = BoardServiceCollection.CellService.getAdjList(9,14);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(9, 15)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(8, 14)));
		assertTrue(testList.contains(BoardServiceCollection.CellService.getCell(10, 14)));
	
	}
	
	
	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInDiningRoom() {
		// test a roll of 1
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(12, 20), 1);
		Set<BoardCell> targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(8, 17)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(12, 15)));	
		
		// test a roll of 3
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(12, 20), 3);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(6, 17)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(8, 19)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(11, 14)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(14, 15)));	
		
		// test a roll of 4
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(12, 20), 4);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(17, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(3, 20)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(7, 17)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(12, 14)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(15, 15)));	
	}
	
	@Test
	public void testTargetsInKitchen() {
		// test a roll of 1
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(20, 19), 1);
		Set<BoardCell> targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(17, 18)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(2, 2)));	
		
		// test a roll of 3
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(20, 19), 3);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(17, 20)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(16, 19)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(17, 16)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(2, 2)));	
		
		// test a roll of 4
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(20, 19), 4);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(16, 18)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(18, 16)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(16, 16)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(2, 2)));	
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(8, 17), 1);
		Set<BoardCell> targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(12, 20)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(7, 17)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(8, 18)));	
		
		// test a roll of 3
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(8, 17), 3);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(12, 20)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(3, 20)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(7, 17)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(7, 19)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(9, 15)));	
		
		// test a roll of 4
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(8, 17), 4);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(12, 20)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(3, 20)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(10, 15)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(6, 17)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(5, 16)));	
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(11, 2), 1);
		Set<BoardCell> targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(11, 1)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(11, 3)));	
		
		// test a roll of 3
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(11, 2), 3);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(14, 2)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(8, 2)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(11, 5)));	
		
		// test a roll of 4
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(11, 2), 4);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(14, 2)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(8, 2)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(11, 6)));	
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(13, 7), 1);
		Set<BoardCell> targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(13, 6)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(12, 7)));	
		
		// test a roll of 3
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(13, 7), 3);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(15, 6)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(14, 7)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(11, 8)));	
		
		// test a roll of 4
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(13, 7), 4);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(14, 2)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(15, 9)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(11, 5)));	
	}

	@Test
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		BoardServiceCollection.CellService.getCell(15, 7).setOccupied(true);
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(13, 7), 4);
		BoardServiceCollection.CellService.getCell(15, 7).setOccupied(false);
		Set<BoardCell> targets = BoardServiceCollection.CellService.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(14, 2)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(15, 9)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(11, 5)));	
		assertFalse( targets.contains( BoardServiceCollection.CellService.getCell(15, 7))) ;
		assertFalse( targets.contains( BoardServiceCollection.CellService.getCell(17, 7))) ;
	
		// we want to make sure we can get into a room, even if flagged as occupied
		BoardServiceCollection.CellService.getCell(12, 20).setOccupied(true);
		BoardServiceCollection.CellService.getCell(8, 18).setOccupied(true);
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(8, 17), 1);
		BoardServiceCollection.CellService.getCell(12, 20).setOccupied(false);
		BoardServiceCollection.CellService.getCell(8, 18).setOccupied(false);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(7, 17)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(8, 16)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(12, 20)));	
		
		// check leaving a room with a blocked doorway
		BoardServiceCollection.CellService.getCell(12, 15).setOccupied(true);
		BoardServiceCollection.CellService.calcTargets(BoardServiceCollection.CellService.getCell(12, 20), 3);
		BoardServiceCollection.CellService.getCell(12, 15).setOccupied(false);
		targets= BoardServiceCollection.CellService.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(6, 17)));
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(8, 19)));	
		assertTrue(targets.contains(BoardServiceCollection.CellService.getCell(8, 15)));

	}
}
