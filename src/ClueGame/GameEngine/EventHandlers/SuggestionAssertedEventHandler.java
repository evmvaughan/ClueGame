package ClueGame.GameEngine.EventHandlers;

import ClueGame.Playables.Entities.Player.Player;
import ClueGame.Playables.Entities.Player.Guess.Suggestion;
import ClueGame.Playables.Services.Dealer;
import ClueGame.Playables.Services.PlayablesServiceCollection;
import SeedWork.IEventHandler;

@SuppressWarnings("hiding")
public class SuggestionAssertedEventHandler<SuggestionAssertedEvent> implements IEventHandler<SuggestionAssertedEvent> {
	
	private Dealer _dealer;

	public SuggestionAssertedEventHandler() { 
		_dealer = PlayablesServiceCollection.Dealer;
	}
	
	public void Handle(SuggestionAssertedEvent event) {
		
		Suggestion suggestion = ((ClueGame.Playables.Events.SuggestionAssertedEvent) event).getSuggestion();
		Player currentPlayer = ((ClueGame.Playables.Events.SuggestionAssertedEvent) event).getPlayer();
		
		Player suggestedPlayer = PlayablesServiceCollection.PlayerService.getPlayerByName(suggestion.getPersonCard().getName());
		
		suggestedPlayer.moveToTarget(currentPlayer.getLocation());
				
		_dealer.distributeSuggestion(suggestion, currentPlayer);
	}
}



