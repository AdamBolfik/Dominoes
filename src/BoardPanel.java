import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class BoardPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private GameState game;
	ArrayList<Dom>	boardLeft,
		  			boardRight,
		  			hand1,
		  			hand2;
	AdamBoneYardRep boneyard;
	Rectangle[] 	rects1 = new Rectangle[7],
					rects2 = new Rectangle[7];
	int widthScale,
		heightScale,
		widthPos,
		heightPos,
		imgLeft,
		imgRight;
	String orientation,
		   imgDir;
	boolean nextLayer;
	
	public BoardPanel() {
		game = GameState.getInstance();
		
		setBorder(BorderFactory.createLineBorder(Color.black));
		setBackground(new Color(50, 50, 50));
		widthScale = super.getWidth() / 15;
		heightScale = super.getHeight() / 40;
		
		init();
	
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(game.getStrategies()[game.getCurrentPlayer()].getName() == "Human"){
					for(int i = 0; i < rects1.length; i++){
						if(rects1[i].contains(e.getX(), e.getY())){
							if(e.getButton() == MouseEvent.BUTTON1){
								if(hand1.get(i) != null){
//									boardLeft.add(hand1.get(i));
									game.getBoard().addDom(0, hand1.get(i));	
									hand1.set(i, null);
								}
								else{
//									hand1.set(i, game.getBoneyard().removeDom(0));
								}
							}
							else{
								if(hand1.get(i) != null){
//									boardRight.add(hand1.get(i));
//									game.getBoard().getDoms().add(hand1.get(i));
//									hand1.set(i, null);
								}
							}
						}
					}
					for(int i = 0; i < rects2.length; i++){
						if(rects2[i].contains(e.getX(), e.getY())){
							if(e.getButton() == MouseEvent.BUTTON1){
								if(hand2.get(i) != null){
//									boardLeft.add(hand2.get(i));
									game.getBoard().addDom(0, hand2.get(i));
									hand2.set(i, null);
								}
								else{
//									hand2.set(i, game.getBoneyard().removeDom(0));
								}
							}
							else{
								if(hand2.get(i) != null){
//									boardRight.add(hand2.get(i));
//									game.getBoard().getDoms().add(hand2.get(i));
//									hand2.set(i, null);
								}
							}
						}
					}
					if(boardLeft.size() > 0 && boardRight.size() == 0)
						boardRight.add(0, boardLeft.get(0));
					if(boardRight.size() > 0 && boardLeft.size() == 0)
						boardLeft.add(0, boardRight.get(0));
					revalidate();
					repaint();
				}
			}
		});
		revalidate();
	}
	
	public void init(){
		boardLeft 	= new ArrayList<Dom>();
	  	boardRight  = new ArrayList<Dom>();
	  	hand1 		= new ArrayList<Dom>();
	  	hand2		= new ArrayList<Dom>();
		boneyard 	= new AdamBoneYardRep();
		
		hand1 = game.getHand(0).getDoms();
		hand2 = game.getHand(1).getDoms();
		
		if(game.getBoard().getDoms().size() > 0){
			boardRight.add(game.getBoard().getDoms().get(0));
			boardLeft.add(game.getBoard().getDoms().get(0));
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		widthScale = super.getWidth() / 15;
		heightScale = super.getHeight() / 40;
		g.setColor(Color.white);
		
		// Draw player areas and scores
		g.drawRect(widthScale*2, heightScale*34, widthScale*11, super.getHeight());
		g.drawRect(widthScale*2, 0, widthScale*11, heightScale*7);
		g.drawString("Player: 1", (widthScale * 2) + 5, heightScale * 35);
		g.drawString("Score: " + game.getScore(0), (widthScale * 2) + 5, heightScale * 36);
		g.drawString("Player: 2", (widthScale * 2) + 5, heightScale * 1);
		g.drawString("Score: " + game.getScore(1), (widthScale * 2) + 5, heightScale * 2);

		for(int i = 0; i < 7; i++){
			rects1[i] = new Rectangle(widthScale*(i+4), heightScale*35, 42, 70);
			rects2[i] = new Rectangle(widthScale*(i+4), heightScale, 42, 70);
		}
		
		try{
			// Draw Player 1 Hand
			for(int i = 0; i < 7; i++){
				if(i < hand1.size() && hand1.get(i) != null){
					imgDir = "vertical/" + hand1.get(i).getLeft() + "_" + hand1.get(i).getRight() + ".png";
					g.drawImage(ImageIO.read(getClass().getResource(imgDir)), widthScale*(i+4), heightScale*35, this);
				}
				else{
					g.drawRoundRect(widthScale*(i+4), heightScale*35, 42, 70, 7, 7);
				}
			}
			// Draw Player 2 Hand
			for(int i = 0; i < 7; i++){
				if(i < hand2.size() && hand2.get(i) != null){
					imgDir = "vertical/" + hand2.get(i).getLeft() + "_" + hand2.get(i).getRight() + ".png";
					g.drawImage(ImageIO.read(getClass().getResource(imgDir)), widthScale*(i+4), heightScale, this);
				}
				else{
					g.drawRoundRect(widthScale*(i+4), heightScale, 42, 70, 7, 7);
				}
			}
			// Draw left side of board
			nextLayer = false;
			widthPos = getWidth()/2 - 35;
			heightPos = getHeight()/2;
			int j = 0;
			for(int i = 0; i < boardLeft.size(); i++){
				if(widthPos > 150 && !nextLayer){
					orientation = "horizontal/";
					imgLeft = boardLeft.get(i).getLeft();
					imgRight = boardLeft.get(i).getRight();
					if(i != 0)
						widthPos -= 74;
				}
				else{
					if(!nextLayer){
						orientation = "vertical/";
						imgLeft = boardLeft.get(i).getLeft();
						imgRight = boardLeft.get(i).getRight();
						widthPos -= 45;
						nextLayer = true;
					}
					else{
						orientation = "horizontal/";
						imgLeft = boardLeft.get(i).getRight();
						imgRight = boardLeft.get(i).getLeft();
						widthPos += 74 * j;
						j = 1;
						heightPos = getHeight()/2 + 74;
					}
				}
				if(boardLeft.get(i) != null){
					imgDir = orientation + imgLeft + "_" + imgRight + ".png";
					g.drawImage(ImageIO.read(getClass().getResource(imgDir)), widthPos, heightPos, this);
				}
			}
			// Draw right side of board
			nextLayer = false;
			widthPos = getWidth()/2 - 35;
			heightPos = getHeight()/2;
			j = 0;
			for(int i = 0; i < boardRight.size(); i++){
				if(widthPos < (getWidth() - 200) && !nextLayer){
					orientation = "horizontal/";
					imgLeft = boardRight.get(i).getLeft();
					imgRight = boardRight.get(i).getRight();
					if(i != 0)
						widthPos += 74;
				}
				else{
					if(!nextLayer){
						orientation = "vertical/";
						imgLeft = boardRight.get(i).getLeft();
						imgRight = boardRight.get(i).getRight();
						widthPos += 74;
						heightPos -= 30;
						nextLayer = true;
					}
					else{
						orientation = "horizontal/";
						imgLeft = boardRight.get(i).getRight();
						imgRight = boardRight.get(i).getLeft();
						if(j == 0)
							widthPos -= 30;
						widthPos -= 74 * j;
						j = 1;
						heightPos = getHeight()/2 - 75;
					}
				}
				if(boardRight.get(i) != null){
					imgDir = orientation + imgLeft + "_" + imgRight + ".png";
					g.drawImage(ImageIO.read(getClass().getResource(imgDir)), widthPos, heightPos, this);
				}
			}
		} catch(IOException e) { System.out.print(e); }
	}
}