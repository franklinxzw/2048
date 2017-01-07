// Zuhra Bholat
// Jiayi Luo
// Vedaank Tiwari
// Franklin Wang
// Matrix - logical operator for 2048, underlying matrix
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Matrix
{
	//Attributes
	private int[][] matrix2048;
  	private final Location[][] LOCS;
  	private final Location[][] ANIMATIONLOCS;
  	private final Location[] SCORELOCS;
	public static int sideLength = 4;
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private ArrayList<Tile> scoreTiles = new ArrayList<Tile>();
	private long score = 0;
	private int movesTaken = 0;
	
	//Constructor
	public Matrix()
	{
		matrix2048 = new int[sideLength][sideLength];
		LOCS = new Location[sideLength][sideLength];
		ANIMATIONLOCS = new Location[148][148];
		SCORELOCS = new Location[6];
		fillLocations();
		fillScoreLocations();
	}
	
	//post: returns matrix
	public int[][] getMatrix()
	{
		return matrix2048;
	}
	
	//post: returns score
	public long getScore()
	{
		return score;
	}
	
	//post: returns number of moves taken
	public int getMovesTaken()
	{
		return movesTaken;
	}	
	
	//post: returns
	public ArrayList<Tile> getTiles()
	{
		ArrayList<Tile> returnTiles = new ArrayList<Tile>();
		returnTiles.addAll(tiles);
		tiles.clear();
		return returnTiles;
	}
	
	// post: returns the tiles for displaying the score
	public ArrayList<Tile> getScoreTiles()
	{
		ArrayList<Tile> returnTiles = new ArrayList<Tile>();
		returnTiles.addAll(scoreTiles);
		scoreTiles.clear();
		return returnTiles;
	}
	
	//post: fills a matrix with Locations
	private void fillLocations()
	{
		int rIndex = 0;
  		int cIndex = 0;
  		for(int row = 20; row <= 755; row += 245)
  		{
  			for(int col = 120; col <=855; col += 245)
  			{
  				LOCS[rIndex][cIndex] = new Location(row, col);
  				cIndex++;
  			}
  			cIndex = 0;
  			rIndex++;
  		}
	}
	
	// post: fills the locations of the score tiles
	private void fillScoreLocations()
	{
		int index = 0;
  		int col = 0;
  		for(int row = 880; row <= 980; row += 20)
  		{
			SCORELOCS[index] = new Location(row, col);
  			index++;
  		}
	}
	
	//post: reads through Locations in the matrix and sets Tiles accordingly
	private void convertToTiles()
	{
		for(int row = 0; row < sideLength; row++)
  			for(int col = 0; col < sideLength; col++)
  				if(matrix2048[row][col] != 0)
  				{
  					String fileName = "Images\\" + Integer.toString(matrix2048[row][col]) + ".png";
  					Location tileLoc = LOCS[row][col];
   					Tile tile = new Tile(fileName, tileLoc);
   					tiles.add(tile);
  				}
	}
	
	// post: converts score to tiles
	private void convertScoreToTiles()
	{
		for(int i = 0; i < Long.toString(score).length(); i++)
		{
			String fileName = "Images\\" + "Score" + Long.toString(score).charAt(i) + ".png";
			Location scoreLoc = SCORELOCS[i];
			Tile tile = new Tile(fileName, scoreLoc);
			scoreTiles.add(tile);
		}
	}
	
	//post: sets up for beginning of game
	public void setup()
	{
		spawn();
		spawn();
		convertToTiles();
	}
        
    //post: moves the Tiles according to user input
    public void act(int keyCode)
    {	
    	// reads user input
    	int [][] previous = arrayCanSpawn();
    	if(keyCode == KeyEvent.VK_LEFT)
			left();
		else if(keyCode == KeyEvent.VK_UP)
			up();
		if(keyCode == KeyEvent.VK_DOWN)
			down();
		else if(keyCode == KeyEvent.VK_RIGHT)
			right();
		if(canSpawn(previous))
			spawn();
		convertToTiles();
		convertScoreToTiles();
    }
    
    //post: returns whether the game is over
    public boolean gameOver()
    {
    	boolean down = canDown();
    	boolean up = canUp();
    	boolean right = canRight();
    	boolean left = canLeft();
    	if((down == false && up == false) && (right == false && left == false))
    		return true;
    	return false;
    }
    
    // stores previous array for canSpawn
    private int[][] arrayCanSpawn()
    {
    	int[][] previous = new int[getMatrix().length][getMatrix().length];
    	for(int r = 0; r < previous.length; r++)
			for(int c = 0; c < previous.length; c++)
				previous[r][c] = getMatrix()[r][c];
		return previous;
    }
    
    // post: checks if a new tile can be spawned
    private boolean canSpawn(int [][] previous)
    {
    	for(int r = 0; r < getMatrix().length; r++)
			for(int c = 0; c < getMatrix().length; c++)
				if(previous[r][c] != getMatrix()[r][c])
					return true;
		return false;
    }
    
    //post: places a new Tile in a random empty location, either a 2 or a 4
	private void spawn()
	{
		ArrayList<Location> emptyLocs = new ArrayList<Location>();
		for(int row = 0; row < sideLength; row++)
			for(int col = 0 ; col < sideLength; col++)
				if(matrix2048[row][col] == 0)
				{
					Location empty = new Location(row, col);
					emptyLocs.add(empty);
				}		
		int tile = 0;
		if(Math.random() > 0.1) // 2 more likely to spawn than 4
			tile = 2;
		else
			tile = 4;
		if(emptyLocs.size() > 0)
		{
			int index = (int)(Math.random() * emptyLocs.size());
			matrix2048[emptyLocs.get(index).getX()][emptyLocs.get(index).getY()] = tile;
		}
	}
   
   	// CODE GUIDE:
   	// all can+Direction() methods use the canShift+Direction() methods and canMerge+Direction()
   	// methods to return a boolean if a certain action is possible
   	
   	// all Direction() methods use the shift+Direction() methods and merge+Direction() to decide
   	// a certain action
   	
    //post: checks if any move down is possible (only used for gameOver method)
   	private boolean canDown()
   	{
   		boolean merge = canMergeDown();
   		boolean shift = canShiftDown();
   		if(merge == false && shift == false)
   			return false;
   		return true;
   	}
    
    //post: moves all Tiles down
    private void down()
    {
      	System.out.println("DOWN!");
    	if(canShiftDown() == true)
    		shiftDown();
    	if(canMergeDown() == true) 
      		mergeDown();
    	movesTaken++;
    }
    
    //post: returns if merging down is possible
    private boolean canMergeDown()
	{
    	for(int r = 0; r < sideLength; r++)
			for(int c = sideLength - 1; c > 0; c--)
				if(matrix2048[r][c] == matrix2048[r][c - 1])
					return true;
		return false;
	}

	//post: merges Tiles down
    private void mergeDown()
    {
    	for(int r = 0; r < sideLength; r++)
			for(int c = sideLength - 1; c > 0; c--)
				if(matrix2048[r][c] == matrix2048[r][c - 1])
				{
					matrix2048[r][c] *= 2;
					matrix2048[r][c - 1] = 0;
					score += matrix2048[r][c];
					c--;
				}
		if(canShiftDown())
			shiftDown();
    }

    //post: returns if shifting Tiles down is possible
    private boolean canShiftDown()
	{
    	for(int r = 0; r < sideLength; r++)
			for(int c = sideLength - 1; c > 0; c--)
				if(matrix2048[r][c] == 0 &&  matrix2048[r][c - 1] != 0)
					return true;	
		return false;
	}
    
    //post: shifts Tiles down
	private void shiftDown()
	{
    	for(int i = 0; i < 3; i++)
	    	for(int r = 0; r < sideLength; r++)
				for(int c = sideLength - 1; c > 0; c--)
					if(matrix2048[r][c] == 0 &&  matrix2048[r][c - 1] != 0)
					{
						int value = matrix2048[r][c - 1];
						matrix2048[r][c] = value;
						matrix2048[r][c - 1] = 0;
					}
	}
		
	//post: checks if any move up is possible (only used for gameOver method)
	private boolean canUp()
   	{
   		boolean merge = canMergeUp();
   		boolean shift = canShiftUp();
   		if(merge == false && shift == false)
   			return false;
   		return true;
   	}
   	
	//post: moves all Tiles up
	private void up()
    {
   		System.out.println("UP!");
		if(canShiftUp() == true)
			shiftUp();
		if(canMergeUp() == true)
			mergeUp();
		movesTaken++;
    }
    
    //post: returns if merging up is possible
    private boolean canMergeUp()
	{
       	for(int r = 0; r < sideLength; r++)
			for(int c = 0; c < sideLength - 1; c++)
				if(matrix2048[r][c] == matrix2048[r][c + 1])
					return true;
		return false;		
	}
	
    //post: merges Tiles up
    private void mergeUp()
    {
       	for(int r = 0; r < sideLength; r++)
			for(int c = 0; c < sideLength - 1; c++)
				if(matrix2048[r][c] == matrix2048[r][c + 1])
				{
					matrix2048[r][c] *= 2;
					matrix2048[r][c + 1] = 0;
					score += matrix2048[r][c];
					c++;
				}	
		if(canShiftUp())
			shiftUp();
    }
    
    //post: returns if shifting Tiles up is possible
    private boolean canShiftUp()
    {
		for(int r = 0; r < sideLength; r++)
			for(int c = 0; c < sideLength - 1; c++)
				if(matrix2048[r][c] == 0 && matrix2048[r][c + 1] != 0)
					return true;
		return false;
    }
    
    //post: shifts Tiles down
    private void shiftUp()
    {
		for(int i = 0; i < 3; i++)
			for(int r = 0; r < sideLength; r++)
				for(int c = 0; c < sideLength - 1; c++)
					if(matrix2048[r][c] == 0 && matrix2048[r][c + 1] != 0)
					{
						int value = matrix2048[r][c + 1];
						matrix2048[r][c] = value;
						matrix2048[r][c + 1] = 0;
					}
    }
    
    //post: checks if any move right is possible (only used for gameOver method)
    private boolean canRight()
   	{
   		boolean merge = canMergeRight();
   		boolean shift = canShiftRight();
   		if(merge == false && shift == false)
   			return false;
   		return true;
   	}
   	
    //post: moves all Tiles down
   	private void right()
    {
    	System.out.println("RIGHT!");
    	if(canShiftRight() == true)
    		shiftRight();
    	if(canMergeRight() == true)
    		mergeRight();
    	movesTaken++;
    }
    
    //post: returns if merging right is possible
    private boolean canMergeRight()
    {
    	for(int c = 0; c < sideLength; c++)
			for(int r = sideLength - 1; r > 0; r--)
				if(getMatrix()[r][c] == getMatrix()[r - 1][c])
					return true;
		return false;
    }
    
    //post: merges Tiles right
    private void mergeRight()
    {
    	for(int c = 0; c < sideLength; c++)
			for(int r = sideLength - 1; r > 0; r--)
				if(getMatrix()[r][c] == getMatrix()[r - 1][c])
				{
					matrix2048[r][c] *= 2;
					matrix2048[r - 1][c] = 0;
					score += matrix2048[r][c];
					r--;	
				}
		if(canShiftRight())
			shiftRight();
    }
    
    //post: returns if shifting Tiles right is possible
    private boolean canShiftRight()
    {
		for(int i = 0; i < 3; i++)
			for(int c = 0; c < sideLength; c++)
				for(int r = sideLength - 1; r > 0; r--)
					if(getMatrix()[r][c] == 0 && getMatrix()[r - 1][c] != 0)
						return true;
		return false;
    }
    
    //post: shifts Tiles down
    private void shiftRight()
    {
		for(int i = 0; i < 3; i++)
			for(int c = 0; c < sideLength; c++)
				for(int r = sideLength - 1; r > 0; r--)
					if(getMatrix()[r][c] == 0 && getMatrix()[r - 1][c] != 0)
					{
						int value = matrix2048[r - 1][c]; 
						matrix2048[r][c] = value;
						matrix2048[r - 1][c] = 0;
					}
    }
    
    //post: checks if any move left is possible (only used for gameOver method)
    private boolean canLeft()
   	{
   		boolean merge = canMergeLeft();
   		boolean shift = canShiftLeft();
   		if(merge == false && shift == false)
   			return false;
   		return true;
   	}
   	
    //post: moves all Tiles down
    private void left()
    {
    	System.out.println("LEFT!");
    	if(canShiftLeft() == true)	
    		shiftLeft();
    	if(canMergeLeft() == true)
    		mergeLeft();
    	movesTaken++;
    }
    
    //post: returns if merging left is possible
    private boolean canMergeLeft()
    {
    	for(int c = 0; c < sideLength; c++)
			for(int r = 0; r < sideLength - 1; r++)
				if(getMatrix()[r][c] == getMatrix()[r + 1][c])
					return true;
		return false;
    }
    
    //post: merges Tiles left
    private void mergeLeft()
    {
    	for(int c = 0; c < sideLength; c++)
			for(int r = 0; r < sideLength - 1; r++)
				if(getMatrix()[r][c] == getMatrix()[r + 1][c])
				{
					matrix2048[r][c] *= 2;
					matrix2048[r + 1][c] = 0;
					score += matrix2048[r][c];
					r++;
				}
		if(canShiftLeft())
			shiftLeft();
    }
    
    //post: returns if shifting Tiles left is possible
    private boolean canShiftLeft()
    {
		for(int c = 0; c < sideLength; c++)
			for(int r = 0; r < sideLength - 1; r++)
				if(getMatrix()[r][c] == 0 && getMatrix()[r + 1][c] != 0)
					return true;
		return false;
    }
    
    //post: shifts Tiles down
    private void shiftLeft()
    {
		for(int i = 0; i < 3; i++)
			for(int c = 0; c < sideLength; c++)
				for(int r = 0; r < sideLength - 1; r++)
					if(getMatrix()[r][c] == 0 && getMatrix()[r + 1][c] != 0)
					{
						int value = matrix2048[r + 1][c];
						matrix2048[r][c] = value;
						matrix2048[r + 1][c] = 0;
					}
    }
}