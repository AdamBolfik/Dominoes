// As Stated

public class Dom {
	
	private int left,
				right;
	
	public Dom(int left, int right) {
		this.left = left;
		this.right = right;
	}
	
	public int getLeft(){
		return left;
	}
	
	public int getRight(){
		return right;
	}
	
	public boolean isDouble(){
		if(left == right)
			return true;
		else
			return false;
	}
	
	public boolean isBlank(){
		if(left == 0 && right == 0)
			return true;
		else
			return false;
	}
	
	public int points(){
		return left + right;
	}
	
	public void flip(){
		int temp = left;
		left = right;
		right = temp;
	}

	// REMOVE
	public String toString(){
		return left + ", " + right;
	}
}
