package clueGame.GameEngine.EventHandlers;

import SeedWork.IEventHandler;
import clueGame.Board.Services.BoardService;
import clueGame.Board.Services.BoardServiceCollection;
import clueGame.Playables.Events.PlayablesInitializedEvent;

@SuppressWarnings("hiding")
public class PlayablesInitializedEventHandler<PlayablesInitializedEvent> implements IEventHandler<PlayablesInitializedEvent> {
	
	private BoardService _boardService;

	public PlayablesInitializedEventHandler() { 
		_boardService = BoardServiceCollection.BoardService;
	}
	
	public void Handle(PlayablesInitializedEvent event) {
		_boardService.playGameWithPlayers(((clueGame.Playables.Events.PlayablesInitializedEvent) event).getPlayers());
		_boardService.playGameWithCards(((clueGame.Playables.Events.PlayablesInitializedEvent) event).getCards());
		_boardService.playGameWithSolution(((clueGame.Playables.Events.PlayablesInitializedEvent) event).getAnswer());
		
		// Update player initial location and sync with board cells
	}
}
