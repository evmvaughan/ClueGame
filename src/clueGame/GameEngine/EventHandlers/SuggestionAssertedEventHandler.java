package clueGame.GameEngine.EventHandlers;

import SeedWork.IEventHandler;
import clueGame.Board.Services.BoardService;
import clueGame.Board.Services.BoardServiceCollection;
import clueGame.Playables.Entities.Player.Player;
import clueGame.Playables.Entities.Player.Guess.Suggestion;
import clueGame.Playables.Events.PlayablesInitializedEvent;
import clueGame.Playables.Services.Dealer;
import clueGame.Playables.Services.PlayablesServiceCollection;

@SuppressWarnings("hiding")
public class SuggestionAssertedEventHandler<SuggestionAssertedEvent> implements IEventHandler<SuggestionAssertedEvent> {
	
	private Dealer _dealer;

	public SuggestionAssertedEventHandler() { 
		_dealer = PlayablesServiceCollection.Dealer;
	}
	
	public void Handle(SuggestionAssertedEvent event) {
		
		Suggestion suggestion = ((clueGame.Playables.Events.SuggestionAssertedEvent) event).getSuggestion();
		Player currentPlayer = ((clueGame.Playables.Events.SuggestionAssertedEvent) event).getPlayer();
		
		_dealer.distributeSuggestion(suggestion, currentPlayer);
	}
}



