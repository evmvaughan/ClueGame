package ClueGame.Board.Services;

public class BoardServiceCollection {
	

	public static InitBoardConfiguration InitConfigurationService;
	public static RoomService RoomService;
	public static CellService CellService;
	public static BoardService BoardService;
		
	//////////////////////////////////////
	// Build list of singleton services //
	//////////////////////////////////////
	
	// Order DOES matter for proper dependency
	public static void initialize() {
		RoomService = ClueGame.Board.Services.RoomService.getInstance();
		CellService = ClueGame.Board.Services.CellService.getInstance();
		BoardService = ClueGame.Board.Services.BoardService.getInstance();
		InitConfigurationService = ClueGame.Board.Services.InitBoardConfiguration.getInstance();

				
		pruneStorage();
	}
	
	private static void pruneStorage() {
		RoomService.pruneStorage();
		CellService.pruneStorage();
	}
	
}
