package ClueGame.GameEngine.EventHandlers;

import ClueGame.Board.Services.BoardService;
import ClueGame.Board.Services.BoardServiceCollection;
import ClueGame.Playables.Events.PlayablesInitializedEvent;
import SeedWork.IEventHandler;

@SuppressWarnings("hiding")
public class PlayablesInitializedEventHandler<PlayablesInitializedEvent> implements IEventHandler<PlayablesInitializedEvent> {
	
	private BoardService _boardService;

	public PlayablesInitializedEventHandler() { 
		_boardService = BoardServiceCollection.BoardService;
	}
	
	public void Handle(PlayablesInitializedEvent event) {
		_boardService.playGameWithPlayers(((ClueGame.Playables.Events.PlayablesInitializedEvent) event).getPlayers());
		_boardService.playGameWithCards(((ClueGame.Playables.Events.PlayablesInitializedEvent) event).getCards());
		_boardService.playGameWithSolution(((ClueGame.Playables.Events.PlayablesInitializedEvent) event).getAnswer());
		
		// Update player initial location and sync with board cells
	}
}
