package com.gousslegend.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.board.ArrayBoard;
import com.gousslegend.deepov.board.Board;
import com.gousslegend.deepov.pieces.Bishop;
import com.gousslegend.deepov.pieces.King;
import com.gousslegend.deepov.pieces.Knight;
import com.gousslegend.deepov.pieces.Pawn;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.deepov.pieces.Queen;
import com.gousslegend.deepov.pieces.Rook;

public class Deepov extends Player
{
	private Move myBestmove;

	private final int KING_VALUE = 100;
	private final int QUEEN_VALUE = 9;
	private final int ROOK_VALUE = 5;
	private final int KNIGHT_VALUE = 3;
	private final int BISHOP_VALUE = 3;
	private final int PAWN_VALUE = 1;

	public Deepov(Board board)
	{
		super(board);
		setName("Deepov");
	}

	@Override
	public Move takeTurn()
	{
		negaMaxRoot(3);
		return myBestmove;
	}
	
	public int negaMaxRoot(int depth)
	{
		int nMoves, i;
		int score = 0;
		int max = -10000;

		MoveList moveList = myBoard.getLegalMoves();
		nMoves = moveList.size();

		for (i = 0; i < nMoves; i++)
		{
			Move move = moveList.getList().get(i);
			myBoard.executeMove(move);
			score = -negaMax(depth - 1);
			if (score > max)
			{
				max = score;
				myBestmove = move;
			}
			myBoard.undoMove(move);
		}

		return max;
	}
	
	public int negaMax(int depth)
	{
		int nMoves, i;
		int score = 0;
		int max = -10000;
		
		if (depth == 0)
		{
			int a = myBoard.getColorToPlay().equals(Color.WHITE)? 1 : -1;
			return a*evaluate();
		}

		MoveList moveList = myBoard.getLegalMoves();
		nMoves = moveList.size();

		for (i = 0; i < nMoves; i++)
		{
			Move move = moveList.getList().get(i);
			myBoard.executeMove(move);
			score = -negaMax(depth - 1);
			if (score > max)
			{
				max = score;
			}
			myBoard.undoMove(move);
		}

		return max;
	}

	public int evaluate()
	{
		int score = 0;
		
		if(myBoard.isCheckmate(Color.WHITE))
		{
			score += -100000;
		}
		else if(myBoard.isCheckmate(Color.BLACK))
		{
			score += 100000;
		}
		return score + 100*getMaterialScore() + getMobilityScore();
	}

	public int getMobilityScore()
	{
		List<Piece> whitePieces = myBoard.getPieces(Color.WHITE);
		List<Position> whiteAttackingSquares = new ArrayList<>();

		for(Piece piece : whitePieces)
		{
			whiteAttackingSquares.addAll(piece.getAttackingSquares());
		}

		List<Piece> blackPieces = myBoard.getPieces(Color.BLACK);
		List<Position> blackAttackingSquares = new ArrayList<>();

		for(Piece piece : blackPieces)
		{
			blackAttackingSquares.addAll(piece.getAttackingSquares());
		}
		return whiteAttackingSquares.size() - blackAttackingSquares.size();
	}

	public int getMaterialScore()
	{
		List<Piece> whitePieces = myBoard.getPieces(Color.WHITE);
		int whitePiecesValue = 0;

		for(Piece piece : whitePieces)
		{
			whitePiecesValue += getPieceScore(piece);
		}

		List<Piece> blackPieces = myBoard.getPieces(Color.BLACK);
		int blackPiecesValue = 0;

		for(Piece piece : blackPieces)
		{
			blackPiecesValue += getPieceScore(piece);
		}

		return whitePiecesValue - blackPiecesValue;
	}

	public int getPieceScore(Piece piece)
	{
		if (piece instanceof Pawn)
		{
			return PAWN_VALUE;
		}
		else if (piece instanceof Rook)
		{
			return ROOK_VALUE;
		}
		else if (piece instanceof Bishop)
		{
			return BISHOP_VALUE;
		}
		else if (piece instanceof Knight)
		{
			return KNIGHT_VALUE;
		}
		else if (piece instanceof Queen)
		{
			return QUEEN_VALUE;
		}
		else if (piece instanceof King)
		{
			return KING_VALUE;
		}
		System.out.println("ERROR");

		return -99999;
	}

	public static void main(String[] args)
	{
		String fen = "rnbqkb1r/pppp1ppp/5n2/4p3/4P3/5Q2/PPPP1PPP/RNB1KBNR w KQkq - 0 1";

		Board board = new ArrayBoard(fen);
		System.out.println(board);

		Deepov deepov = new Deepov(board);
		System.out.println(deepov.negaMaxRoot(3));
		System.out.println(deepov.myBestmove);
		board.executeMove(deepov.myBestmove);
		System.out.println(board);


	}
}