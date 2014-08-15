package com.gousslegend.deepov;

import com.gousslegend.deepov.board.ArrayBoard;
import com.gousslegend.deepov.board.Board;

public class Game
{
	private Board myBoard;
	private Player whitePlayer;
	private Player blackPlayer;

	public Game()
	{
		this(true);
	}
	
	public Game(boolean showBoard)
	{
		//board = new ListBoard();
		//myBoard = new MapBoard();
		myBoard = new ArrayBoard();
		myBoard.setupBoard();
		
		if(showBoard)
		{
			System.out.print(myBoard);
		}
	}
	
	public Game(boolean showBoard, String fen)
	{
		//myBoard = new MapBoard(fen);
		myBoard = new ArrayBoard(fen);
		
		if(showBoard)
		{
			System.out.print(myBoard);
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
		return myBoard;
	}

	public void setBoard(Board board)
	{
		myBoard = board;
	}

	public static void main(String[] args)
	{
		Game game = new Game();
		System.out.println(game.perft(5));
	}

	public int divide(int depth)
	{
		int nMoves, i;
		int nodes = 0;
		int nodeTotal = 0;

		if (depth == 0)
		{
			return 1;
		}

		MoveList moveList = myBoard.generateMoves();
		nMoves = moveList.size();

		for (i = 0; i < nMoves; i++)
		{
			Move move = moveList.getList().get(i);
			System.out.println("");
			myBoard.executeMove(move);
			nodes = perft(depth - 1);
			System.out.println(move.toShortString() + " " + nodes);
			nodeTotal += nodes;
			myBoard.undoMove(move);
		}
		
		System.out.println("Total nodes: " + nodeTotal);
		System.out.println("Total moves : " + nMoves);
		return nodes; 
	}
	
	public int perft(int depth)
	{
		int nMoves, i;
		int nodes = 0;

		if (depth == 0)
		{
			return 1;
		}

		MoveList moveList = myBoard.generateMoves();
		nMoves = moveList.size();

		for (i = 0; i < nMoves; i++)
		{
			Move move = moveList.getList().get(i);
			myBoard.executeMove(move);
			nodes += perft(depth - 1);
			myBoard.undoMove(move);
		}
		
		return nodes;
	}
	
	public int[] perftWithData(int depth)
	{
		int nMoves, i;
		int[] data = new int[5];
		int[] dataTemp = new int[5];

		if (depth == 0)
		{	
			int node = 1;
			int capture = myBoard.getLastMove().getCapturedPiece() == null ? 0 : 1 ;
			int castling = myBoard.getLastMove().isCastling() ? 1 : 0 ;
			int promotion = myBoard.getLastMove().isPromotion() ? 1 : 0 ;
			int enPassant = myBoard.getLastMove().isEnPassant() ? 1 : 0 ;
			return new int[]{node, capture, castling, promotion, enPassant};
		}

		MoveList moveList = myBoard.generateMoves();
		nMoves = moveList.size();

		for (i = 0; i < nMoves; i++)
		{
			Move move = moveList.getList().get(i);
			
			myBoard.executeMove(move);

			dataTemp = perftWithData(depth - 1);
			data[0] += dataTemp[0];
			data[1] += dataTemp[1];
			data[2] += dataTemp[2];
			data[3] += dataTemp[3];
			data[4] += dataTemp[4];
			
			myBoard.undoMove(move);
		}
		
		return data;
	}
}