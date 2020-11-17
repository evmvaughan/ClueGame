package ClueGame.GameEngine.Tests;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ClueGame.Board.Entities.Cell.BoardCell;
import ClueGame.Board.Entities.Room.Room;
import ClueGame.Board.Services.BoardServiceCollection;
import ClueGame.GameEngine.GameEngine;
import ClueGame.GameEngine.Movement.Movement;
import ClueGame.Playables.Entities.Player.ComputerPlayer;
import ClueGame.Playables.Entities.Player.LocationDTO;
import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Services.PlayablesServiceCollection;

public class GameAITests {

	@BeforeAll
	public static void setUp() {

		GameEngine gameEngine = GameEngine.getInstance();

		gameEngine.initializeAll();
		
	}

	@Test
	public void testTargetsNearRoom() {
				
		ArrayList<Player> players = PlayablesServiceCollection.PlayerService.getPlayers();

		ComputerPlayer player = (ComputerPlayer) players.get(0);
		
		Movement playerMovements = GameEngine.Movement;
		
		playerMovements.updatePlayerMovementContext(player, new LocationDTO("Water", 4, 4));
		
		player.selectTargetFromRollOf(2);
		
		LocationDTO newLocation = player.moveToTarget();
		
		assertTrue(newLocation.getCurrentRow() == 1 && newLocation.getCurrentColumn() == 2); // Center of unique room
		
		
	}
	
	@Test
	public void testTargetsWithNoRooms() {
				
		ArrayList<Player> players = PlayablesServiceCollection.PlayerService.getPlayers();

		ComputerPlayer player = (ComputerPlayer) players.get(1);
		
		Movement playerMovements = GameEngine.Movement;
		
		playerMovements.updatePlayerMovementContext(player, new LocationDTO("Water", 11, 6));
//		initializePlayerAtLocation(player, 11, 6);
		
		player.selectTargetFromRollOf(1);
		
		LocationDTO newLocation = player.moveToTarget();
		
		 //Location falls within these random targets
		assertTrue(newLocation.getCurrentRow() == 12 && newLocation.getCurrentColumn() == 6
				|| newLocation.getCurrentRow() == 10 && newLocation.getCurrentColumn() == 6
				|| newLocation.getCurrentRow() == 11 && newLocation.getCurrentColumn() == 7
				|| newLocation.getCurrentRow() == 11 && newLocation.getCurrentColumn() == 5);
		
	}
	
	@Test
	public void testTargetsWithSeenRooms() {
				
		ArrayList<Player> players = PlayablesServiceCollection.PlayerService.getPlayers();

		ComputerPlayer player = (ComputerPlayer) players.get(2);
		
		Movement playerMovements = GameEngine.Movement;
		
		playerMovements.updatePlayerMovementContext(player, new LocationDTO("Aruba", 8, 2)); // Inform the game that the player has seen Aruba
		
		BoardCell targetCell = BoardServiceCollection.CellService.getCell(12, 1);
		Room targetRoom = BoardServiceCollection.RoomService.getRoomFromCell(targetCell);
		
		LocationDTO targetLocation = new LocationDTO(targetRoom.getName(), targetCell.getRow(), targetCell.getColumn());
		
		player.setTargetLocation(targetLocation);
		
		player.moveToTarget();
		
		player.selectTargetFromRollOf(2);
		
		LocationDTO newLocation = player.moveToTarget();
		
		 //Location falls within these random targets including seen room center
		assertTrue(newLocation.getCurrentRow() == 8 && newLocation.getCurrentColumn() == 2
				|| newLocation.getCurrentRow() == 11 && newLocation.getCurrentColumn() == 0
				|| newLocation.getCurrentRow() == 13 && newLocation.getCurrentColumn() == 0
				|| newLocation.getCurrentRow() == 13 && newLocation.getCurrentColumn() == 2
				|| newLocation.getCurrentRow() == 11 && newLocation.getCurrentColumn() == 2
				|| newLocation.getCurrentRow() == 12 && newLocation.getCurrentColumn() == 3);
		
	}
}
