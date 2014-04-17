
public class AdamStrategy implements Strategy {

	private GameState game;
	
	public AdamStrategy() {
		game = GameState.getInstance();
	}

	@Override
	public String getName() {
		return "Adam";
	}

	@Override
	public boolean playTile() {
		int left  = game.getBoard().getLeftEnd(),
			right = game.getBoard().getRightEnd(),
			player = game.getCurrentPlayer();
		
		if(game.getHand(player).getDoms().size() == 0){
			game.setWhoDominoed(player);
		}
		
		for(int i = 0; i < game.getHand(player).getDoms().size(); i++){
			Dom d = game.getHand(player).getDoms().get(i);
			if(d.getRight() == left){
				game.getBoard().addDom(Loc.LEFT, game.getHand(player).getDoms().remove(i));
				return true;
			}
			else if(d.getLeft() == left){
				game.getHand(player).getDoms().get(i).flip();
				game.getBoard().addDom(Loc.LEFT, game.getHand(player).getDoms().remove(i));
				return true;
			}
			else if(d.getRight() == right){
				game.getHand(player).getDoms().get(i).flip();
				game.getBoard().addDom(Loc.RIGHT, game.getHand(player).getDoms().remove(i));
				return true;
			}
			else if(d.getLeft() == right){
				game.getBoard().addDom(Loc.RIGHT, game.getHand(player).getDoms().remove(i));
				return true;
			}
		}
		return false;
	}

}
