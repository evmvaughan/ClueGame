package clueGame.Playables.Services;

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
		PlayerService = clueGame.Playables.Services.PlayerService.getInstance();
		CardService = clueGame.Playables.Services.CardService.getInstance();
		Dealer = clueGame.Playables.Services.Dealer.getInstance();
		InitConfigurationService = clueGame.Playables.Services.InitPlayableConfiguration.getInstance();

		pruneStorage();
	}
	
	private static void pruneStorage() {
		PlayerService.pruneStorage();
		CardService.pruneStorage();
	}
	
}
