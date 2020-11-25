package ClueGame.GameEngine.EventHandlers;

import ClueGame.GameEngine.GameEngine;
import ClueGame.Playables.Entities.Player.Guess.Accusation;
import ClueGame.Playables.Services.Dealer;
import ClueGame.Playables.Services.PlayablesServiceCollection;
import SeedWork.IEventHandler;

@SuppressWarnings("hiding")
public class AccusationAssertedEventHandler<AccusationAssertedEvent> implements IEventHandler<AccusationAssertedEvent> {
	
	private Dealer _dealer;

	public AccusationAssertedEventHandler() { 
		_dealer = PlayablesServiceCollection.Dealer;
	}
	
	public void Handle(AccusationAssertedEvent event) {
		
		Accusation accusation = ((ClueGame.Playables.Events.AccusationAssertedEvent) event).getAccusation();
		
		if (_dealer.receivedValidAccusation(accusation)) {
			System.out.println("Player wins!");
		} else {
			GameEngine.getInstance().endGame();
		}
	}
}
