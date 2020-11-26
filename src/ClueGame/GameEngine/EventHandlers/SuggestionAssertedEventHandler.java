package ClueGame.GameEngine.EventHandlers;

import ClueGame.GameEngine.Panels.ClueGameUI;
import ClueGame.Playables.Entities.Card.Card;
import ClueGame.Playables.Entities.Player.ComputerPlayer;
import ClueGame.Playables.Entities.Player.HumanPlayer;
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
				
		Card disprovedCard = _dealer.distributeSuggestion(suggestion, currentPlayer);
		
		ClueGameUI.getInstance().updateGuess(suggestion);
		
		if (disprovedCard != null) {			
			
			if (currentPlayer instanceof HumanPlayer) {
				currentPlayer.addSeenCard(disprovedCard);
				ClueGameUI.getInstance().updateDisprovedCard(disprovedCard);
				ClueGameUI.getInstance().updateSeenCards();
			} else {
				ClueGameUI.getInstance().updateDisprovedCard(null);
			}
		} else {
			if (currentPlayer instanceof ComputerPlayer) {
				((ComputerPlayer)currentPlayer).setSuggestionAsAccusation(suggestion);
			}
		}
	}
}



