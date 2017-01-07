// Zuhra Bholat
// Jiayi Luo
// Vedaank Tiwari
// Franklin Wang
// ImagePanel -  creates ImagePanel for game to play
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class ImagePanel extends JPanel 
{
  	//Attributes
  	private Image background;				
  	private ArrayList<Tile> foreground;
  	private ArrayList<Tile> tiles;
  	
  	//Constructors
  	// initializes private variables and sets the foreground to an arrayList of Tiles
  	public ImagePanel(String img) 
  	{
  		this(new ImageIcon(img).getImage());	
		foreground = new ArrayList<Tile>();
  	}
  	
	// initializes private variables and sets the foreground to an arrayList of Tiles;
	// also sets the background to the image in the parameter
  	public ImagePanel(Image img)
  	{
    	background = img;
    	Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));	
    	setPreferredSize(size);
    	setMinimumSize(size);
    	setMaximumSize(size);
    	setSize(size);
    	foreground = new ArrayList<Tile>();		
  	}
  	
 	//post: updates images
 	public void updateImages(Matrix matrix)
  	{
  		ArrayList<Tile> totalTiles = new ArrayList<Tile>();
  		totalTiles.addAll(matrix.getTiles());
  		totalTiles.addAll(matrix.getScoreTiles());
  		foreground = totalTiles;
  		repaint();
  	}
  	
  	//post: redraws background and foreground
  	public void paintComponent(Graphics g) 
  	{
    	g.drawImage(background, 0, 0, null); 
    	for(Tile img : foreground)	
    	{
    		Location loc = img.getLocation();
    		g.drawImage(img.getImage(), loc.getX(), loc.getY(), null);
    	}
  	}
}