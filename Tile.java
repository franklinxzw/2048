// Zuhra Bholat
// Jiayi Luo
// Vedaank Tiwari
// Franklin Wang
import java.awt.*;
import javax.swing.*;

public class Tile
{
	//Attributes
	private Image image;
	private Location loc;
	
	//Constructors
	public Tile(Image img, Location loc)
	{
		image = img;
		this.loc = loc;
	}
	
	public Tile(String path, Location loc)
	{
		this(new ImageIcon(path).getImage(), loc);	
			//easiest way to make an image from a file path in Swing
		this.loc = loc;
	}
	
	//Getter Methods
	public Location getLocation()
	{
		return loc;
	} 
			
	public Image getImage()
	{
		return image;
	}
	
	//Setter Method
	public void setImage(Image img)
	{
		image = img;
	}
}