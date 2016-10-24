package com.bbd.tictactoe.playTictactoe;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
/**
 * 
 * @author Thabiso Phetla & Sibongile Mbewe
 *The tic tac toe runs with i a server that logs and provides some data validation
 */
public class Server {
	static ServerSocket listener;
	static Socket clientSocket;
	static int PORT = 9090;
	static Scanner receiveValue;
	static PrintStream p;
	static int position;
	static int[] Occupied ={0,0,0,0,0,0,0,0,0}; 
	public static void main(String[] args) throws IOException {
		try {
			listener = new ServerSocket(PORT);	//creating a serverSocket for port = 9090
			System.out.println("The server is on and listening at port "+PORT);
			clientSocket = listener.accept();
			System.out.println("Server has accepted connection from client "+clientSocket.toString());
			validateRange();
		} catch ( BindException e) {
			System.err.println("Port "+PORT+" is not available");
		}
	}
	public static void validateRange() throws IOException
	{
		DataInputStream din = new DataInputStream(clientSocket.getInputStream());
		DataOutputStream dout = new DataOutputStream(clientSocket.getOutputStream());

		int i=0,moveCount=1;
		int blockNumber = 0;

		String msgin="";
		Date date = new Date();
		while(!msgin.equalsIgnoreCase("end"))
		{
			try {
				int flagNumOccupied = 1;
				msgin = din.readUTF();
				blockNumber = Integer.parseInt(msgin);

				if (blockNumber < 1 || blockNumber > 9){
					dout.writeUTF("10");
					System.out.println("Number out of bound -_-");
				}
				else{

					for(int j = 0;j<9;j++){
						if(Occupied[j] == blockNumber){
							dout.writeUTF("11");
							System.err.println("Block occupied -_-");
							flagNumOccupied = 0;
							break;
						}	
					}
					if(	flagNumOccupied == 1){
						Occupied[i] = blockNumber;
						System.out.println("Move "+ moveCount +" has been validated "+date.toString());
						dout.writeUTF(""+blockNumber);
						moveCount++;
					}
				}
				dout.flush();
			} catch (java.net.SocketException e) {
				System.out.println("Match is finished!!!!!");
				msgin = "end";
				clientSocket.close();
				listener.close();
				System.out.println("Server connection disabled "+clientSocket.toString());
			}catch(NumberFormatException e){
				System.err.println("Next time use Numbers only... ");
			}
		}
	}
}
