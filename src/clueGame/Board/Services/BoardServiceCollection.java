package clueGame.Board.Services;

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
		RoomService = clueGame.Board.Services.RoomService.getInstance();
		CellService = clueGame.Board.Services.CellService.getInstance();
		BoardService = clueGame.Board.Services.BoardService.getInstance();
		InitConfigurationService = clueGame.Board.Services.InitBoardConfiguration.getInstance();

				
		pruneStorage();
	}
	
	private static void pruneStorage() {
		RoomService.pruneStorage();
		CellService.pruneStorage();
	}
	
}
