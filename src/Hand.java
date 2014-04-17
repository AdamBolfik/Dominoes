import java.util.ArrayList;

// As Stated

public class Hand extends Clutch{

	protected ClutchRep rep;
	
	public Hand() {
		rep = new AdamHandRep();
	}
	
	public void addDom(int loc, Dom dom){
		if(loc == Loc.RIGHT)
			rep.addDom(Loc.RIGHT, dom);
		else
			rep.addDom(Loc.LEFT, dom);
	}
	
	public ArrayList<Dom> getDoms(){
		return rep.getDoms();
	}
	
	public Dom removeDom(int loc){
		return rep.removeDom(loc);
	}
	
	public ClutchRep getRep(){
		return rep;
	}
	
	public void setRep(ClutchRep rep){
		this.rep = rep;
	}
}
