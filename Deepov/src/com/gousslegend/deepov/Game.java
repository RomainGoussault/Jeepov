package com.gousslegend.deepov;

public class Game
{
	private Board board;
	private Player whitePlayer;
	private Player blackPlayer;

	public Game()
	{
		board = new Board();
		board.setupBoard();
		String boardRepresentation = board.getRepresentation();
		System.out.print(boardRepresentation);
	}

	public Player getWhitePlayer()
	{
		return whitePlayer;
	}

	public void setWhitePlayer(Player whitePlayer)
	{
		this.whitePlayer = whitePlayer;
	}

	public Player getBlackPlayer()
	{
		return blackPlayer;
	}

	public void setBlackPlayer(Player blackPlayer)
	{
		this.blackPlayer = blackPlayer;
	}

	public Board getBoard()
	{
		return board;
	}
	
	public static void main(String[] args)
	{
		new Game();
	}
}