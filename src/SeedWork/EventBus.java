package SeedWork;

import clueGame.GameEngine.EventHandlers.*;
import clueGame.Playables.Events.AccusationAssertedEvent;
import clueGame.Playables.Events.PlayablesInitializedEvent;
import clueGame.Playables.Events.PlayerMovedToSelectedTargetEvent;
import clueGame.Playables.Events.PlayerSelectingTargetsEvent;



public class EventBus implements ISingleton<EventBus> {

	public static EventBus instance = new EventBus();
	
	private EventBus() {
		initializeHandlers();
	}
	
	public static EventBus getInstance() {
		return instance;
	}
	
	private PlayablesInitializedEventHandler<PlayablesInitializedEvent> _playablesInitializedEventHandler;
	private AccusationAssertedEventHandler<AccusationAssertedEvent> _accusationAssertedEventEventHandler;
	private PlayerSelectingTargetsEventHandler<PlayerSelectingTargetsEvent> _playerSelectingTargetsEventHandler;
	private PlayerMovedToSelectedTargetEventHandler<PlayerMovedToSelectedTargetEvent> _playerMovedToSelectedTargetEventHandler;

	///////////////////////////////////
	// Initialize All Event Handlers //
	///////////////////////////////////
	public void initializeHandlers() {
		_playablesInitializedEventHandler = new PlayablesInitializedEventHandler<PlayablesInitializedEvent>();
		_accusationAssertedEventEventHandler = new AccusationAssertedEventHandler<AccusationAssertedEvent>();
		_playerSelectingTargetsEventHandler = new PlayerSelectingTargetsEventHandler<PlayerSelectingTargetsEvent>();
		_playerMovedToSelectedTargetEventHandler = new PlayerMovedToSelectedTargetEventHandler<PlayerMovedToSelectedTargetEvent>();
	}
	
	//////////////////////////////////////////////////////////////
	// Forward type-checked events to their respective handlers //
	//////////////////////////////////////////////////////////////
	public void Publish(IEvent event) {
		
		if (event instanceof PlayablesInitializedEvent) {
			_playablesInitializedEventHandler.Handle((PlayablesInitializedEvent) event);
		}	
		
		if (event instanceof AccusationAssertedEvent) {
			_accusationAssertedEventEventHandler.Handle((AccusationAssertedEvent) event);
		}	
		
		if (event instanceof PlayerSelectingTargetsEvent) {
			_playerSelectingTargetsEventHandler.Handle((PlayerSelectingTargetsEvent) event);
		}
		
		if (event instanceof PlayerMovedToSelectedTargetEvent) {
			_playerMovedToSelectedTargetEventHandler.Handle((PlayerMovedToSelectedTargetEvent) event);
		}
	}
}
