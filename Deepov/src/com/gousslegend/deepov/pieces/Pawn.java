package com.gousslegend.deepov.pieces;

import java.util.ArrayList;
import java.util.List;

import com.gousslegend.deepov.Board;
import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;

public class Pawn extends Piece
{

	public Pawn(Position position, Board board, Color color)
	{
		super(position, board, color);
		// TODO Auto-generated constructor stub
	}
	
	public Pawn()
	{
		super();
	}

	@Override
	public MoveList getPseudoLegalMoves()
	{
		MoveList pseudoLegalMoves = new MoveList(myBoard);

		int direction = getDirection();
		Position destination = null;
		
		if(isOnStartingRank())
		{
			destination = myPosition.deltaY(2*direction); 
			if (myBoard.isPositionFree(destination))
			{
				pseudoLegalMoves.add(new Move(myPosition, destination));
			}	
		}
		
		//no need to check if we are on the edge on the board:
		//it should not happen because it will be promoted before
		destination = myPosition.deltaY(direction); 
		if (myBoard.isPositionFree(destination))
		{
			pseudoLegalMoves.add(new Move(myPosition, destination));
		}
		
		//check to the right and the left for capture
		Piece possibleCapture = null;
		
		destination = myPosition.deltaXY(1, direction);
		possibleCapture = myBoard.getPiece(destination);
		if (possibleCapture != null && areColorDifferent(possibleCapture))
		{
			pseudoLegalMoves.add(new Move(myPosition, destination));
		}
		
		destination = myPosition.deltaXY(-1, direction);
		possibleCapture = myBoard.getPiece(destination);
		if (possibleCapture != null && areColorDifferent(possibleCapture))
		{
			pseudoLegalMoves.add(new Move(myPosition, destination));
		}
		
		//Todo: Promotion, en passant

		return pseudoLegalMoves;
	}

	@Override
	public List<Position> getAttackingSquares()
	{
		int direction = getDirection();
		List<Position> attackedPositions = new ArrayList<>();
		Position destination = null;
		
		destination = myPosition.deltaXY(-1, direction);
		if (myBoard.isPositionOnBoard(destination))
		{
			if (!myBoard.isPositionFree(destination))
			{
				Piece piece = myBoard.getPiece(destination);
				if (areColorDifferent(piece))
				{
					attackedPositions.add(destination);
				}
			}
		}

		destination = myPosition.deltaXY(1, direction);
		if (myBoard.isPositionOnBoard(destination))
		{
			if (!myBoard.isPositionFree(destination))
			{
				Piece piece = myBoard.getPiece(destination);
				if (areColorDifferent(piece))
				{
					attackedPositions.add(destination);
				}
			}
		}
		
		return attackedPositions;
	}

	public boolean isOnStartingRank()
	{
		if(myColor == Color.WHITE)
		{
			return myPosition.getY() == 1;
		}
		else
		{
			return myPosition.getY() == 6;
		}
	}

	public boolean isOnLastRank()
	{
		if(myColor == Color.WHITE)
		{
			return myPosition.getY() == 7;
		}
		else
		{
			return myPosition.getY() == 0;
		}
	}
	
	public int getDirection()
	{
		if (myColor == Color.WHITE)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
}
