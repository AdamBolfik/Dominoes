import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class GameMgr {
	
	public int[] players;
	private static GameState game;
	private static Board board;
	private static BoneYard boneyard;
	private static Hand hand1;
	private static Hand hand2;
	private static Dom[] h1,
				  		 h2;
	private static Strategy[] strategies = new Strategy[2];
	private static Iterator<Dom> boneIter;
	private static Display display;
	private static int t1, t2 = 0;
	
	public static void main(String[] args){
		game = GameState.getInstance();
		
		game.setNumPlayers(2);
		int[] scores = {0, 0};
		game.setScores(scores);
		
		display = new Display();
		
		for(int i = 0; i < 1000; i++){
			game.setStatus(Status.INTRO_STATE);
			init();
			playFirstTile();
			display.restart();
			game.setStatus(Status.PLAY_STATE);
			playGame();
		}
		
		System.out.print(t1 + " " + t2);
		
	}
	
	private static void init(){
		game.setCurrentPlayer(0);
		board = new Board();
		game.setBoard(board);
		boneyard = new BoneYard();
		fillBoneYard(boneyard);
		hand1 = new Hand();
		hand2 = new Hand();
		h1 = new Dom[7];
		h2 = new Dom[7];
		Collections.shuffle(boneyard.getDoms());
		boneIter = boneyard.getDoms().iterator();
		drawDoms(7, h1);
		drawDoms(7, h2);
		for(int i = 0; i < h1.length; i++){
			hand1.addDom(i, h1[i]);
			hand2.addDom(i, h2[i]);
		}
		game.setHand(0, hand1);
		game.setHand(1, hand2);
		
		strategies[0] = new AdamStrategy();
		strategies[1] = new CalumStrategy();
		game.setStrategies(strategies);
	}
	
	private static boolean playFirstTile(){
		ArrayList<Integer> player1Doubles = new ArrayList<Integer>(),
					   	   player2Doubles = new ArrayList<Integer>();
		ArrayList<Dom> 	   player1Doms	  = new ArrayList<Dom>(),
					   	   player2Doms	  = new ArrayList<Dom>();
		int[] 		   	   player1Pips 	  = new int[7],
					   	   player2Pips 	  = new int[7];
		player1Doms = game.getHand(0).getDoms();
		player2Doms = game.getHand(1).getDoms();
		
		// Find doubles in both hands
		for(int i = 0; i < player1Doms.size(); i++){ 
			if(player1Doms.get(i).isDouble())
				player1Doubles.add(i);
			if(player2Doms.get(i).isDouble())
				player2Doubles.add(i);
		}
		
		// No Doubles
		if(player1Doubles.size() == 0 && player2Doubles.size() == 0){
			for(int i = 0; i < player1Doms.size(); i++){ 
				player1Pips[i] = player1Doms.get(i).points();
				player2Pips[i] = player2Doms.get(i).points();
			}
			
		}
		// Player 1  or Player 2 has doubles
		else{ 
			int max1 = -1,
				index1 = -1;
			for(int i = 0; i < player1Doubles.size(); i++){
				if(player1Doms.get(player1Doubles.get(i)).points() > max1){
					max1 = player1Doms.get(player1Doubles.get(i)).points();
					index1 = player1Doubles.get(i);
				}
			}
			
			
			int max2 = -1,
				index2 = -1;
			for(int i = 0; i < player2Doubles.size(); i++){
				if(player2Doms.get(player2Doubles.get(i)).points() > max2){
					max2 = player2Doms.get(player2Doubles.get(i)).points();
					index2 = player2Doubles.get(i);
				}
			}
			
			if(max1 > max2)
				game.getBoard().addDom(0, game.getHand(0).getDoms().remove(index1));
			else if(max2 > max1)
				game.getBoard().addDom(0, game.getHand(1).getDoms().remove(index2));
			else{
				int heavy1 = game.getHand(0).getDoms().get(index1).getLeft() > game.getHand(0).getDoms().get(index1).getRight()?
						game.getHand(0).getDoms().get(index1).getLeft() : game.getHand(0).getDoms().get(index1).getRight();
				int	heavy2 = game.getHand(1).getDoms().get(index2).getLeft() > game.getHand(1).getDoms().get(index2).getRight()?
						game.getHand(1).getDoms().get(index2).getLeft() : game.getHand(1).getDoms().get(index2).getRight();
				System.out.println(heavy1 + " " + heavy2);
				if(heavy1 > heavy2)
					game.getBoard().addDom(0, game.getHand(0).getDoms().remove(index1));
				else
					game.getBoard().addDom(0, game.getHand(1).getDoms().remove(index2));
			}
		}
		
		if(game.getHand(0).getDoms().size() < game.getHand(1).getDoms().size())
			game.setCurrentPlayer(1);
		else
			game.setCurrentPlayer(0);
		
		return true;
	}
	
	private static void playGame(){
		boolean play0 = false,
				play1 = true;
		Dom left, right;
		
		while(game.getStatus() == Status.PLAY_STATE){
			
			left = game.getBoard().getDoms().get(0);
			right = game.getBoard().getDoms().get(game.getBoard().getDoms().size() - 1);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			play1 = strategies[game.getCurrentPlayer()].playTile();
			
			if(left != game.getBoard().getDoms().get(0))
				display.bPanel.boardLeft.add(game.getBoard().getDoms().get(0));
			else if(right != game.getBoard().getDoms().get(game.getBoard().getDoms().size() - 1))
				display.bPanel.boardRight.add(game.getBoard().getDoms().get(game.getBoard().getDoms().size() - 1));
			
			display.bPanel.repaint();
			
			game.setCurrentPlayer(nextPlayer());
			
			if(!play0 && !play1){
				game.setStatus(Status.DONE_STATE);
			}
			else{
				play1 = play0;
			}
		}
		
		int pips0 = 0,
			pips1 = 0;
		for(Dom d: game.getHand(0).getDoms()){
			pips0 += d.points();
		}
		for(Dom d: game.getHand(1).getDoms()){
			pips1 += d.points();
		}
		if(pips0 < pips1){
			game.setScore(0, pips1 + game.getScore(0));
			game.setWinner(0);
			System.out.println(1);
			t1 += 1;
		}
		else if(pips1 < pips0){
			game.setScore(1, pips0 + game.getScore(1));
			game.setWinner(1);
			System.out.println(2);
			t2 += 1;
		}
		else{
			System.out.println("TIE");
		}
	}

	private static void drawDoms(int num_doms, Dom[] doms){
		for(int i = 0; i < num_doms; i++){
			doms[i] = boneIter.next();
	    	boneIter.remove();
		}
	}
	
	public static int nextPlayer(){
		return (game.getCurrentPlayer() + 1) % game.getNumPlayers();
	}

	
	private static void fillBoneYard(BoneYard boneyard){
		boneyard.addDom(0, new Dom(0, 0));
		boneyard.addDom(1, new Dom(0, 1));
		boneyard.addDom(2, new Dom(0, 2));
		boneyard.addDom(3, new Dom(0, 3));		
		boneyard.addDom(4, new Dom(0, 4));		
		boneyard.addDom(5, new Dom(0, 5));
		boneyard.addDom(6, new Dom(0, 6));
		boneyard.addDom(7, new Dom(1, 1));
		boneyard.addDom(8, new Dom(1, 2));
		boneyard.addDom(9, new Dom(1, 3));
		boneyard.addDom(10, new Dom(1, 4));
		boneyard.addDom(11, new Dom(1, 5));
		boneyard.addDom(12, new Dom(1, 6));
		boneyard.addDom(13, new Dom(2, 2));
		boneyard.addDom(14, new Dom(2, 3));
		boneyard.addDom(15, new Dom(2, 4));
		boneyard.addDom(16, new Dom(2, 5));
		boneyard.addDom(17, new Dom(2, 6));
		boneyard.addDom(18, new Dom(3, 3));
		boneyard.addDom(19, new Dom(3, 4));
		boneyard.addDom(20, new Dom(3, 5));
		boneyard.addDom(21, new Dom(3, 6));
		boneyard.addDom(22, new Dom(4, 4));
		boneyard.addDom(23, new Dom(4, 5));
		boneyard.addDom(24, new Dom(4, 6));
		boneyard.addDom(25, new Dom(5, 5));
		boneyard.addDom(26, new Dom(5, 6));
		boneyard.addDom(27, new Dom(6, 6));
		game.setBoneyard(boneyard);
	}
}
