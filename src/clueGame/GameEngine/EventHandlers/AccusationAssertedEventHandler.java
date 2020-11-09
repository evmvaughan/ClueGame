package clueGame.GameEngine.EventHandlers;

import SeedWork.IEventHandler;
import clueGame.Playables.Entities.Player.Guess.Accusation;
import clueGame.Playables.Services.Dealer;
import clueGame.Playables.Services.PlayablesServiceCollection;

@SuppressWarnings("hiding")
public class AccusationAssertedEventHandler<AccusationAssertedEvent> implements IEventHandler<AccusationAssertedEvent> {
	
	private Dealer _dealer;

	public AccusationAssertedEventHandler() { 
		_dealer = PlayablesServiceCollection.Dealer;
	}
	
	public void Handle(AccusationAssertedEvent event) {
		
		Accusation accusation = ((clueGame.Playables.Events.AccusationAssertedEvent) event).getAccusation();
		
		if (_dealer.receivedValidAccusation(accusation)) {
			// Players wins
		}
	}
}
