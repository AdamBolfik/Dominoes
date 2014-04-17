
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Display extends JFrame{

	private static final long serialVersionUID = 1L;
	protected HashMap<Dom, BufferedImage> hDoms = new HashMap<Dom, BufferedImage>();
	protected HashMap<Dom, BufferedImage> vDoms = new HashMap<Dom, BufferedImage>();
	public BoardPanel bPanel;
				   	
	public Display() {
		setTitle("Dominoes -- Box Game --");
		setSize(new Dimension(900, 600));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setVisible(true);	
	}
	
	public void restart(){
		if(bPanel != null)
			this.remove(bPanel);
		bPanel = new BoardPanel();
		add(bPanel);
		revalidate();
	}
}
