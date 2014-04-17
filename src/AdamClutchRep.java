import java.util.ArrayList;


public abstract class AdamClutchRep implements ClutchRep {

	protected ArrayList<Dom> dominoes = new ArrayList<Dom>();
	
	public AdamClutchRep() {
		
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
		Dom d = dominoes.get(location);
		dominoes.remove(location);
		return d;
	}

	@Override
	public ArrayList<Dom> getDoms() {
		return dominoes;
	}
}
