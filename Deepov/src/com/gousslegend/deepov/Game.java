package com.gousslegend.deepov;

import com.gousslegend.deepov.board.Board;
import com.gousslegend.deepov.board.ListBoard;
import com.gousslegend.deepov.board.MapBoard;

public class Game
{
	private Board board;
	private Player whitePlayer;
	private Player blackPlayer;

	public Game()
	{
		this(true);
	}
	
	public Game(boolean showBoard)
	{
		//board = new ListBoard();
		board = new MapBoard();
		board.setupBoard();
		
		if(showBoard)
		{
			System.out.print(board);
		}
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
		Game game = new Game();
		System.out.println(game.perft(5));
	}

	public int perft(int depth)
	{
		int nMoves, i;
		int nodes = 0;

		Color color = Color.BLACK;
		if (depth % 2 == 1)
		{
			color = Color.WHITE;
		}
		if (depth == 0)
		{
			return 1;
		}

		MoveList moveList = board.generateMoves(color);
		nMoves = moveList.size();

		for (i = 0; i < nMoves; i++)
		{
			board.executeMove(moveList.getList().get(i));
			nodes += perft(depth - 1);
			board.undoMove(moveList.getList().get(i));
		}
		
		return nodes;
	}
}