import java.util.ArrayList;


public class AdamBoardRep extends AdamClutchRep{

	public AdamBoardRep() {
		
	}
	
	@Override
	public void addDom(int location, Dom d) {
		if(location == Loc.RIGHT)
			dominoes.add(d);
		else
			dominoes.add(Loc.LEFT, d);
	}

	@Override
	public Dom removeDom(int location) {
		return dominoes.remove(location);
	}

	@Override
	public ArrayList<Dom> getDoms() {
		return dominoes;
	}

}
