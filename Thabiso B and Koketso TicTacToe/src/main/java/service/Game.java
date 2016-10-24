package service;
import java.net.*; 
import java.io.*; 


public class Game  
{ 
	private boolean running; 
	private boolean isHost;
	private boolean isWinner;
	private boolean isLoser;
	private boolean isDraw = false;
	private boolean myTurn; 
	private int availableMoves; 
	private Socket gameSocket; 
	private BufferedReader response; 
	private DataOutputStream client; 

	private String grid[][]; 

	public Game(){
		this.grid = new String[4][4]; 
		for(int i = 0; i < 4; i++)  
		{ 
			for(int j = 0; j < 4; j++)  
			{ 
				this.grid[i][j] = " ";
			} 
		} 

	}
	public Game(Socket connection, boolean isHost) throws Exception  
	{ 
		this.grid = new String[4][4]; 
		for(int i = 0; i < 4; i++)  
		{ 
			for(int j = 0; j < 4; j++)  
			{ 
				this.grid[i][j] = " ";
			} 
		} 

		this.running = true; 
		this.isHost = isHost; 
		this.myTurn = isHost; 
		this.availableMoves = 3*3; 


		this.gameSocket = connection; 
		this.response = new BufferedReader(new InputStreamReader(this.gameSocket.getInputStream())); 
		this.client = new DataOutputStream(this.gameSocket.getOutputStream()); 
	} 


	public boolean performMove(int x, int y) throws Exception  
	{ 
		if(this.moveEmpty(x,y))  
		{ 
			this.grid[x][y] = this.myTurn ? "X" : "O";              
			this.sendMessage(x + " " + y); 
			this.myTurn = !this.myTurn; 
			this.printBoard(); 
			this.availableMoves--; 

			if(this.availableMoves <= 0) 
				this.draw(); 

			return true; 
		} 
		return false; 
	} 


	public boolean moveEmpty(int x, int y)  
	{ 
		if(this.checkBounds(x,y)) 
			return this.grid[x][y].equals(" "); 


		return false; 
	} 


	public boolean checkBounds(int x, int y)  
	{ 
		return x >= 1 && x <= 3 && y >= 1 && y <= 3; 
	} 


	public boolean isRunning()  
	{ 
		return this.running; 
	} 

	public String valueAt(int col, int row) {
		return grid[col][row];
	}
	
	// winning combinations
	public boolean hasFullColumnOfWin() {
		return ((grid[1][1].equals("X") && grid[1][2].equals("X") && grid[1][3].equals("X"))
				|| (grid[2][1].equals("X") && grid[2][2].equals("X") && grid[2][3].equals("X"))
			    || (grid[3][1].equals("X") && grid[3][2].equals("X") && grid[3][3].equals("X")));
	} 
	
	public boolean hasFullRowOfWin() {
		return ((grid[1][1].equals("X") && grid[2][1].equals("X") && grid[3][1].equals("X"))
				|| (grid[1][2].equals("X") && grid[2][2].equals("X") && grid[3][2].equals("X"))
			    || (grid[1][3].equals("X") && grid[2][3].equals("X") && grid[3][3].equals("X")));
	}  
	
	public boolean hasFullDiagonalOfWin() {
		return ((grid[1][1].equals("X") && grid[2][2].equals("X") && grid[3][3].equals("X"))
				|| (grid[1][3].equals("X") && grid[2][2].equals("X") && grid[3][1].equals("X")));
	}
	
	// losing combinations
	public boolean hasFullColumnOfLose() {
		return ((grid[1][1].equals("O") && grid[1][2].equals("O") && grid[1][3].equals("O"))
				|| (grid[2][1].equals("O") && grid[2][2].equals("O") && grid[2][3].equals("O"))
			    || (grid[3][1].equals("O") && grid[3][2].equals("O") && grid[3][3].equals("O")));
	}
	
	
	public boolean hasFullRowOfLose() {
		return ((grid[1][1].equals("O") && grid[2][1].equals("O") && grid[3][1].equals("O"))
				|| (grid[1][2].equals("O") && grid[2][2].equals("O") && grid[3][2].equals("O"))
			    || (grid[1][3].equals("O") && grid[2][3].equals("O") && grid[3][3].equals("O")));
	}
	
	public boolean hasFullDiagonalOfLose() {
		return ((grid[1][1].equals("O") && grid[2][2].equals("O") && grid[3][3].equals("O"))
				|| (grid[1][3].equals("O") && grid[2][2].equals("O") && grid[3][1].equals("O")));
	}
	
	public boolean checkWinner()  throws Exception  
	{ 
		if(hasFullColumnOfWin() == true)
				isWinner= true;
		
		else if(hasFullRowOfWin() == true)
				isWinner= true;
		
		else if(hasFullDiagonalOfWin() == true)
				isWinner= true;
		
		else 
				isWinner = false; 
		
		return isWinner;
			
	} 
	
	public boolean checkLoser()  throws Exception  
	{ 		
		if(hasFullColumnOfLose() == true)
				isLoser= true;
		
		else if(hasFullRowOfLose() == true)
				isLoser= true;
		
		else if(hasFullDiagonalOfLose() == true)
				isLoser= true;
		
		else 
				isLoser = false; 
		
		return isLoser;
			
	} 
	
	public void loser() throws Exception
	{
		if(isWinner == false) 
			System.out.println("You lost!");  

		this.gameOver(); 
		this.exit(); 
	}

	public void winner() throws Exception  
	{ 
		if(isWinner == true) 
			System.out.println("You won!");  

		this.gameOver(); 
		this.exit(); 
	} 

	public boolean draw() throws Exception  
	{ 
		if(isWinner == false && isLoser == false)
		{
			isDraw = true;
			System.out.println("Draw - No available moves...");
		}
		this.gameOver(); 
		this.exit();
		
		return isDraw;
	} 	
	
	public boolean myTurn()  
	{ 
		return myTurn; 
	} 

	public void gameOver()  
	{ 
		this.running = false; 
	} 


	public void printBoard()  
	{ 
		for(int i = 1; i <= 3; i++)  
		{ 
			if(i == 1)
			System.out.println("    1    2    3");
			
			if(i == 1)
			System.out.print("1");
			
			if(i == 2)
				System.out.print("2");
			
			if(i == 3)
				System.out.print("3");
			
			for(int j = 1; j <= 3; j++)  	
			{
				System.out.print("  [" + this.grid[j][i] + "]"); 
			} 
			System.out.println(); 
		} 
	} 


	public void exit() throws Exception  
	{ 
		this.gameSocket.close();
		System.out.println("Connection has been terminated...");
		System.exit(0); 
	} 

	public String readMessage() throws Exception  
	{ 
		return this.response.readLine(); 
	} 


	public void sendMessage(String s) throws Exception  
	{ 
		this.client.writeBytes(s+"\n"); 
	} 
	
	public void setGrid(int x,int y,String value){
		this.grid[x][y] = value;
	}
}