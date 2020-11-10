package clueGame.GameEngine.ControlPanel;

public class ComputerPlayerView {

	private String _name;
	private String _playerColor;
	
	public ComputerPlayerView(String name, String playerColor) {
		_name = name;
		_playerColor = playerColor;
		
	}

	public String getName() {
		return _name;
	}
	
	public String getColor() {
		return _playerColor;
	}
	
}
