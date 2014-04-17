// As Stated

import java.util.ArrayList;

public abstract class Clutch implements ClutchRep{

	protected ClutchRep rep;
	
	public Clutch() {
		
	}

	@Override
	public void addDom(int location, Dom d) {
		rep.addDom(location, d);
	}

	@Override
	public Dom removeDom(int location) {
		return rep.removeDom(location);
	}

	@Override
	public ArrayList<Dom> getDoms() {
		return rep.getDoms();
	}
	
	public ClutchRep getRep(){
		return rep;
	}
	
	public void setRep(ClutchRep c){
		rep = c;
	}

}
