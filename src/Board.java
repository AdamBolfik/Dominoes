import java.util.ArrayList;

// As Stated

public class Board extends Clutch{
	
	protected ClutchRep rep;
	private int leftEnd,
			   rightEnd;
	
	public Board() {
		rep = new AdamBoardRep();
	}
	
	public void addDom(int loc, Dom dom){
		if(loc == Loc.RIGHT){
			rep.addDom(Loc.RIGHT, dom);
			rightEnd = dom.getRight();
		}
		else{
			rep.addDom(Loc.LEFT, dom);
			leftEnd = dom.getLeft();
		}
		rightEnd = rep.getDoms().get(rep.getDoms().size() - 1).getRight();
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
	
	public int getLeftEnd(){
		return leftEnd;
	}
	
	public int getRightEnd(){
		return rightEnd;
	}

}
