package com.bbd.tictactoe.playTictactoe;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;
public class Asserting {
	@Test
	public void test1(){
		assertEquals("\tTic Tac Toe \n\tUse Numbers only (1 - 9)\n\tDo not choose blocks With X nor O\n", client.rules());
	}
	
	@Test
	public void settingBlockX() {
		assertEquals(1,client.setBlock(3, " X "));
	}
	@Test
	public void settingBlockO() {
		assertEquals(1,client.setBlock(2, " O "));
	}
	@Test(expected = IOException.class)
	public void testSocket() throws UnknownHostException, IOException{
		client.connector = new Socket("Wrong",9999);
	}
}
