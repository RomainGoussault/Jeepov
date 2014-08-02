package com.gousslegend.deepov.board;

import java.util.List;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.pieces.Piece;

public abstract class Board
{
	public static final int BOARD_SIZE = 7;
	
	/** Move taken in this game so far */
	protected List<Move> myMoves;
	
	public Board()
	{
		super();
	}
	
	public abstract void addPiece(Piece piece);

	public abstract void setupBoard();
	
	public abstract Piece getPiece(Position position);

	public abstract boolean isPositionFree(Position position);
	
	protected abstract List<Piece> getPieces(Color color);
	
	protected abstract List<Piece> getPieces();

	public abstract Piece getKing(Color color);

	public abstract Position getKingPosition(Color color);

	public abstract void executeMove(Move move);

	public abstract void undoMove(Move move);
	
	public String toString()
	{
		String board = "";
		Piece piece = null;
		
		for(int i = 7; i >= 0 ; i--)
		{
			board += i + "|  ";

			for(int j = 0; j < 8 ; j++)
			{
				piece = getPiece(new Position(j,i));
				if(piece == null)
				{
					board += "* ";
				}
				else
				{
					board += piece.getChar() + " ";
				}				
			}
			
			board += " \n";
		}
		
		board +="   ________________\n";
		board +="    0 1 2 3 4 5 6 7\n";
		
		return board;
	}
	
	public boolean isPositionOnBoard(Position position)
	{
		int x = position.getX();
		int y = position.getY();

		if (x > BOARD_SIZE || y > BOARD_SIZE)
		{
			return false;
		}

		if (x < 0 || y < 0)
		{
			return false;
		}

		return true;
	}

	public boolean isCheck(Color color)
	{
		Position kingPosition = getKingPosition(color);

		List<Piece> ennemyPieces = getEnnemiesPieces(color);

		for (Piece ennemyPiece : ennemyPieces)
		{
			if (isPieceAttacking(ennemyPiece, kingPosition))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isPositionAttacked(Position position, Color color)
	{
		List<Piece> ennemyPieces = getEnnemiesPieces(color);

		for (Piece ennemyPiece : ennemyPieces)
		{
			if (isPieceAttacking(ennemyPiece, position))
			{
				return true;
			}
		}
		return false;
	}
	
	public List<Piece> getEnnemiesPieces(Color color)
	{
		return getPieces(color.getOppositeColor());
	}


	public MoveList generateMoves(Color color)
	{
		List<Piece> pieces = getPieces(color);
		MoveList moveList = new MoveList(this);
		
		for(Piece piece : pieces)
		{
			moveList.append(piece.getLegalMoves());
		}
		
		return moveList;
	}

	public List<Move> getMoves()
	{
		return myMoves;
	}
	
	/**
	 * This method return the number of active pieces on the board
	 * @return
	 */
	public int getNumberOfPieces()
	{
		return getPieces().size();
	}
	
	public Move getLastMove()
	{
		if(myMoves.size() > 0)
		{
			return myMoves.get(myMoves.size()-1);
		}
		else
		{
			return null;
		}
	}
	
	private boolean isPieceAttacking(Piece ennemyPiece, Position positionAttackec)
	{
		List<Position> attackingSquares = ennemyPiece.getAttackingSquares();
		
		return attackingSquares.contains(positionAttackec);
	}
}