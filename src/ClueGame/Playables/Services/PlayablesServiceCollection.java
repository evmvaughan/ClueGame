package ClueGame.Playables.Services;

public class PlayablesServiceCollection {

		
	////////////////////////////////////////////////////////////////////////////
	// Build list of singleton services for interacting with playable objects //
	////////////////////////////////////////////////////////////////////////////
	
	public static InitPlayableConfiguration InitConfigurationService;
	public static PlayerService PlayerService;
	public static CardService CardService;
	public static Dealer Dealer;


	// Order DOES matter for correct dependency
	public static void initialize() {
		PlayerService = ClueGame.Playables.Services.PlayerService.getInstance();
		CardService = ClueGame.Playables.Services.CardService.getInstance();
		Dealer = ClueGame.Playables.Services.Dealer.getInstance();
		InitConfigurationService = ClueGame.Playables.Services.InitPlayableConfiguration.getInstance();

		pruneStorage();
	}
	
	private static void pruneStorage() {
		PlayerService.pruneStorage();
		CardService.pruneStorage();
	}
	
}
