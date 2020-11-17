package ClueGame.GameEngine.Commands;

import SeedWork.ICommand;

public class UserInteractionCommand implements ICommand {

	private String _type;
	
	public UserInteractionCommand(String type) {
		_type = type;
	}
	
	@Override
	public String getType() {
		return _type;
	}
	
}
