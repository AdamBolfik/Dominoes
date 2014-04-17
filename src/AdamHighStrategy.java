import java.util.Collections;
import java.util.Comparator;


public class AdamHighStrategy implements Strategy {

	private GameState game;
	
	public AdamHighStrategy() {
		game = GameState.getInstance();
		
		Collections.sort(game.getHand(game.getCurrentPlayer()).getDoms(), new Comparator<Dom>() {
		    public int compare(Dom a, Dom b) {
		    	int i = (a.points() < b.points()) ? -1 : 1;
		        return i;
		    }
		});
	}

	@Override
	public String getName() {
		return "AdamHigh";
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
