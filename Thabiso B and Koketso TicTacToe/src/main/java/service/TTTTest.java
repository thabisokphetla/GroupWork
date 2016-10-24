package service;

import static org.junit.Assert.*;

import java.net.Socket;

import org.junit.Test;


import service.Game;
import service.ServerService;
public class TTTTest {
	private Socket connection; 
	private int port; 
	private Game game;

	@Test
	public void checkBounsReturnsTrue()
	{
		this.game = new Game();
		assertEquals(true, game.checkBounds(1, 1));
	}
	@Test
	public void checkBoundsReturnsFalse()
	{
		this.game = new Game();
		assertEquals(false, game.checkBounds(4, 1));
	}
	@Test
	public void performMoveReturnsTrue(){
		this.game = new Game();
		assertEquals(true, game.moveEmpty(1, 1));
	}
	
	@Test
	public void hasFullColumnsWinRetrunsTrue()
	{
		this.game = new Game();
		game.setGrid(1, 1, "X");
		game.setGrid(1, 2, "X");
		game.setGrid(1, 3, "X");
		assertEquals(true, game.hasFullColumnOfWin());
		
	}
	@Test
	public void hasFullColumnsLooseReturnsFalse(){
		this.game = new Game();
		game.setGrid(1, 1, "O");
		game.setGrid(1, 2, "O");
		game.setGrid(1, 3, "O");
		assertEquals(true, game.hasFullColumnOfLose());
		
	}
	@Test
	public void hasFullRowWinReturnsTrue(){
		this.game = new Game();
		game.setGrid(1, 1, "X");
		game.setGrid(2, 1, "X");
		game.setGrid(3, 1, "X");
		assertEquals(true, game.hasFullRowOfWin());
	}
	
	@Test
	public void hasFullRowLooseReturnsFalse(){
		this.game = new Game();
		game.setGrid(1, 1, "O");
		game.setGrid(2, 1, "O");
		game.setGrid(3, 1, "O");
		assertEquals(true, game.hasFullRowOfLose());
	}
	@Test
	public void hasFullDiagonalWinReturnsTrue(){
		this.game = new Game();
		game.setGrid(1, 1, "X");
		game.setGrid(2, 2, "X");
		game.setGrid(3, 3, "X");
		assertEquals(true, game.hasFullDiagonalOfWin());
	}
	@Test
	public void hasFullDiagonalLoseReturnsFalse(){
		this.game = new Game();
		game.setGrid(1, 1, "O");
		game.setGrid(2, 2, "O");
		game.setGrid(3, 3, "O");
		assertEquals(true, game.hasFullDiagonalOfLose());
	}
	@Test
	public void testRun() throws Exception
	{
		this.game = new Game();
		game.gameOver();
		
		assertEquals(false, game.isRunning());
		
	}
}