package service;

import java.net.*;
import java.io.*;
public class titactoe {
	public static void main(String[] args) throws Exception  
	{ 
		BufferedReader localInput = new BufferedReader(new InputStreamReader(System.in)); 
		boolean isListener = false; 

		System.out.print("Enter server ip (leave blank to host): "); 
		String host = localInput.readLine(); 


		// i am host 
		if(host.equals(""))  
			isListener = true; 


		int port = 14; 
		boolean canPlay = true; 


		// initialize server and client to null 
		ServerService server = null; 
		ClientService client = null; 


		if(isListener)  
		{ 
			// is host 
			server = new ServerService(port); 
			if(server.hasConnection())  
			{ 
				// if a client has connected, can start game 
				canPlay = true; 
			} else  
			{ 
				// client connection failed, abort 
				System.out.println("Connection to peer failed!"); 
				return; 
			} 
		} else  
		{ 
			// is client, has connected to server 
			client = new ClientService(host, port); 
			canPlay = true; 
		} 


		if(canPlay)  
		{ 
			// am i using client or server socket 
			Socket gameSocket = (isListener ? server.connection() : client.connection());    


			// initialize new game with the correct socket and host status 
			Game game = new Game(gameSocket, isListener); 


			// while game is in progress, and no winner is chosen 
			while(game.isRunning())  
			{ 
				// if my turn, ask for a valid move 
				if(game.myTurn())  
				{ 
					boolean res = false; 
					do  
					{ 
						System.out.print("Enter your move (x y): "); 
						String move[] = localInput.readLine().split(" "); 
						int x = Integer.parseInt(move[0]); 
						int y = Integer.parseInt(move[1]); 
						res = game.performMove(x,y);
						if(!res)
							System.out.println("Invalid Move!");
					} while(!res);
					// a valid move has been performed, wait for response 
					System.out.println("Waiting for opponent..."); 
				} 


				// not my turn, waiting for clients move 
				else if(!game.myTurn())  
				{ 
					String move[] = game.readMessage().split(" "); 
					int x = Integer.parseInt(move[0]); 
					int y = Integer.parseInt(move[1]); 
					game.performMove(x,y);
				} 

					

				// has any player won? 
				game.checkWinner();
				
				if(game.checkWinner() == true) {
					game.winner();
				}
				else if(game.checkLoser() == true)
					game.loser();
			} 
			game.exit(); 
		} 
	} 


}
