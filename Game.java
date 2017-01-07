// Zuhra Bholat
// Jiayi Luo
// Vedaank Tiwari
// Franklin Wang
// Game- class that runs the 2048 game
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Game implements KeyListener
{
	//Attributes
	private JFrame frame;						//The window
	private ImagePanel imagePanel;				
	private ArrayList<Tile> tiles;	
	private double xMax;						//The width of the panel
	private double yMax;						//The height of the panel
	private Matrix matrix;
	
	//Constructor
	public Game()
	{
		setup();
		init();
		newGame();
	}
	
	//post: initializes/resets Matrix and ArrayList
	public void setup()
	{
		matrix  = new Matrix();
		tiles = new ArrayList<Tile>();
	}
	
	//post: sets up window
	public void init()
	{	
		imagePanel = new ImagePanel("Images\\ScoreBackgroundHD.png");	
		Dimension d = imagePanel.getSize();	
		yMax = d.getHeight();		
		xMax = d.getWidth();
		frame = new JFrame("2048");
		frame.addKeyListener(this);
		frame.setSize((int)d.getWidth(), (int)d.getHeight() + 25);	
		frame.add(imagePanel);
		frame.setVisible(true);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//end the program when you hit X
	}

	//post: starts a new game	
	public void newGame()
	{
		// creates a pop up box and asks the user to press enter to start the game
		Object[] options = {"Start Game"};
		String message = "HOW TO PLAY: Use your arrow keys to move the tiles. When two tiles with the same number touch, they merge into one! Click 'N' to Restart!";
		int response = JOptionPane.showOptionDialog(null, message,
		"2048", JOptionPane.DEFAULT_OPTION,
		JOptionPane.QUESTION_MESSAGE,null, options, options[0]);
		matrix.setup();
		imagePanel.updateImages(matrix);
	}
	
	//pre: keyCode is a valid input for a keyboard input
	//post: starts a new game
	public void restartGame(int keyCode)
	{
		// restarts game if n is pressed
		if(keyCode == KeyEvent.VK_N)
    	{
    		frame.dispose();
    		setup();
			init();
			newGame();
    	}
	}

	//post: updates images according to user input
	public void keyPressed(KeyEvent e)
    {
    	int keyCode = e.getKeyCode();
    	restartGame(keyCode);
    	// only updates the imagePanel is game is not over
    	if (matrix.gameOver() == false)
    	{
    		matrix.act(keyCode);
    		imagePanel.updateImages(matrix);
			System.out.println("Moves Taken:" + "\t" + matrix.getMovesTaken());
			System.out.println();
	   	}
	   	// creates a pop up box telling the user that the game is over, if user presses enter, game restarts
    	else
    	{
			String message = "Game Over... Your Score Is: " + matrix.getScore();
			int response = JOptionPane.showOptionDialog(null, message,
			"2048", JOptionPane.DEFAULT_OPTION,
			JOptionPane.QUESTION_MESSAGE,null, null, null);
    		restartGame(KeyEvent.VK_N);
    		return;
    	}
    }
    
    //Do nothing
    public void keyReleased(KeyEvent e)
    {
    	
    }
    
    //Do nothing
    public void keyTyped(KeyEvent e)
    {
    	
    }

	//Main Method
	public static void main(String[] args)
	{
		Game a = new Game();
	}
}