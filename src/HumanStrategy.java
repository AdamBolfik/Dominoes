
public class HumanStrategy implements Strategy {

	private GameState game;
	
	public HumanStrategy() {
		game = GameState.getInstance();
	}

	@Override
	public String getName() {
		return "Human";
	}

	@Override
	public boolean playTile() {

		return false;
	}

}
