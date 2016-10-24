package com.bbd.tictactoe.playTictactoe;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
/**
 * 
 * @author Thabiso Phetla & Sibongile Mbewe
 * The tic tac toe runs with a server that logs and provides some data validation
 */
public class client {
	static String[] blocks = {" 1 "," 2 "," 3 "," 4 "," 5 "," 6 "," 7 "," 8 "," 9 "};
	static int blockNum = 1;
	static int counting=0;
	static Socket connector;
	static int testedNumber;

	public static void main(String[] args) {
		try {
			@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);
			System.out.print("Enter the Port Number: ");
			int port = input.nextInt();
			System.out.print("Enter host name: ");
			String ip = input.next();
			connector = new Socket(ip,port);
			System.out.println(rules());
			board();
			String msgin = "",msgout = "";
			DataInputStream din = new DataInputStream(connector.getInputStream());
			DataOutputStream dout = new DataOutputStream(connector.getOutputStream());

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			int testedNumIsWrongX = 1;
			int testedNumIsWrongO = 1;
		
			while(!msgin.equalsIgnoreCase("end")){
				do{
					System.out.print("\nPlayer X block you want 1 - 9 : ");
					msgout = br.readLine();
					dout.writeUTF(msgout);
					msgin = din.readUTF();
					testedNumber = Integer.parseInt(msgin);

					if(testedNumber == 10||testedNumber == 11){
						if(testedNumber == 10)
							System.err.println("ERROR Invalid option 1 - 9 : ");
						else if (testedNumber == 11)
							System.err.println("Block occupied");
						testedNumIsWrongX = 0;
					}
					else{
						if(	setBlock(testedNumber-1," X ")== 0){
							testedNumIsWrongX = 0;
							System.err.println("Block has been occupied ");
						}
						else{
							setBlock(testedNumber-1," X ");
							testedNumIsWrongX = 1;
							board();
						}
					}
					gameStatus(" X ");
				}while(testedNumIsWrongX == 0);

				do{
					System.out.print("\nPlayer O block you want 1 - 9 : ");
					msgout = br.readLine();
					dout.writeUTF(msgout);
					msgin = din.readUTF();
					testedNumber = Integer.parseInt(msgin);
					if(testedNumber == 10||testedNumber == 11){
						if(testedNumber == 10)
							System.err.println("ERROR Invalid option 1 - 9 : ");
						else if (testedNumber == 11)
							System.err.println("Block occupied");
						testedNumIsWrongO = 0;
					}
					else{
						if(	setBlock(testedNumber-1," O ")== 0){
							testedNumIsWrongO = 0;
							System.err.println("Block has been occupied ");
						}
						else{
							setBlock(testedNumber-1," O ");
							testedNumIsWrongO = 1;
							board();
						}
					}
					gameStatus(" O ");
				}while(testedNumIsWrongO == 0);
			}
		} catch (UnknownHostException e) {
			System.err.println("The Host Is Unknown");
		} catch (IOException e) {
			System.err.println("\n\tCould not find any server to connect to");
		}
	}

	public static int setBlock(int num,String Symbol){
		if(blocks[num] == " X " || blocks[num] == " O "){
			return 0;
		}
		else{
			blocks[num] = Symbol;
			counting++;
			return 1;
		}
	}
	public static String rules(){
		String menu = "\tTic Tac Toe \n\tUse Numbers only (1 - 9)\n\tDo not choose blocks With X nor O\n";
		return menu;
	}
	public static void gameStatus(String sign){
		if(blocks[0] == sign && blocks[1] == sign && blocks[2] == sign ){
			System.out.println("\nPlayer "+sign+"has won!!!!!");
			System.exit(1);
		}
		else if(blocks[3] == sign && blocks[4] == sign && blocks[5] == sign ){
			System.out.println("\nPlayer "+sign +"has won!!!!!");
			System.exit(1);
		}
		else if(blocks[6] == sign && blocks[7] == sign && blocks[8] == sign ){
			System.out.println("\nPlayer "+sign+"has won!!!!!");
			System.exit(1);
		}
		else  if(blocks[0] == sign && blocks[3] == sign && blocks[6] == sign ){
			System.out.println("\nPlayer "+sign+"has won!!!!!");
			System.exit(1);
		}
		else  if(blocks[1] == sign && blocks[4] == sign && blocks[7] == sign ){
			System.out.println("\nPlayer "+sign+"has won!!!!!");
			System.exit(1);
		}
		else if(blocks[2] == sign && blocks[5] == sign && blocks[8] == sign ){
			System.out.println("\nPlayer "+sign+"has won!!!!!");
			System.exit(1);
		}
		else if(blocks[0] == sign && blocks[4] == sign && blocks[8] == sign ){
			System.out.println("\nPlayer "+sign+"has won!!!!!");
			System.exit(1);
		}
		else if(blocks[2] == sign && blocks[4] == sign && blocks[6] == sign ){
			System.out.println("\nPlayer "+sign+"has won!!!!!");
			System.exit(1);
		}
		else{
			if (counting == 9){
				System.out.println("\n Well played it's a draw");
				System.exit(1);
			}
		}
	}

	public static void board()
	{
		for (int i = 0; i < blocks.length; i++) {
			System.out.print(blocks[i]);
			if(i == 2 || i == 5){
				System.out.println();
				System.out.println("-----------");
			}
			if(i != 2 && i != 5 && i!=8 ){
				System.out.print("|");
			}
		}
	}
}
