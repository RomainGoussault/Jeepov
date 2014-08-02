package com.gousslegend.deepov.pieces;

import java.util.ArrayList;
import java.util.List;

import com.gousslegend.deepov.Board;
import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;

public class King extends Piece
{
	private boolean isCastlingPossible = true;

	public King()
	{
		super();
	}

	public King(Color color)
	{
		super(color);
	}

	public King(Position position, Board board, Color color)
	{
		super(position, board, color);
	}

	@Override
	public MoveList getPseudoLegalMoves()
	{
		MoveList pseudoLegalMoves = new MoveList(myBoard);
		Move possibleMove = null;

		List<Position> destinations = new ArrayList<>();
		destinations.add(myPosition.deltaXY(0, 1));
		destinations.add(myPosition.deltaXY(0, -1));
		destinations.add(myPosition.deltaXY(1, -1));
		destinations.add(myPosition.deltaXY(1, 0));
		destinations.add(myPosition.deltaXY(1, 1));
		destinations.add(myPosition.deltaXY(-1, -1));
		destinations.add(myPosition.deltaXY(-1, 0));
		destinations.add(myPosition.deltaXY(-1, 1));

		for (Position destination : destinations)
		{
			if (myBoard.isPositionOnBoard(destination))
			{
				possibleMove = new Move(myPosition, destination);
				if (myBoard.isPositionFree(destination))
				{
					possibleMove = new Move(myPosition, destination);
					pseudoLegalMoves.add(possibleMove);
				}
				else
				{
					Piece piece = myBoard.getPiece(destination);
					// look for capture
					if (areColorDifferent(piece))
					{
						possibleMove = new Move(myPosition, destination);
						possibleMove.setCapturedPiece(piece);
						pseudoLegalMoves.add(possibleMove);
					}
				}
			}
		}

		if (isCastlingPossible)
		{
			if (!this.hasMoved())
			{
				if(isKingSideCastlingPossible())
				{
					possibleMove = new Move(myPosition, new Position(6, myPosition.getY()));
					possibleMove.setisCastling(true);
				}
				if(isQueenSideCastlingPossible())
				{
					possibleMove = new Move(myPosition, new Position(3, myPosition.getY()));
					possibleMove.setisCastling(true);	
				}
			}
			else
			{
				isCastlingPossible = false;
			}
		}

		return pseudoLegalMoves;
	}

	private boolean isQueenSideCastlingPossible()
	{
		boolean isQueenSideCastlingPossible = true;
		
		Rook rook = getQueenSideRook();
		
		if(rook != null && !rook.hasMoved())
		{
			int y = myColor == Color.WHITE ? 0 : 7 ;
			
			//check if the positions are in check
			List<Position> positions = new ArrayList<>();
			positions.add(new Position(2,y));
			positions.add(new Position(3,y));
			positions.add(new Position(4,y));
			
			for(Position position : positions)
			{
				isQueenSideCastlingPossible = isQueenSideCastlingPossible && myBoard.isPositionAttacked(position, myColor);
			}
		}
		else
		{
			isCastlingPossible = false;
			return false;
		}
		
		return isQueenSideCastlingPossible;
	}

	private boolean isKingSideCastlingPossible()
	{
		boolean isKingSideCastlingPossible = true;
		
		Rook rook = getKingSideRook();
		
		if(rook != null && !rook.hasMoved())
		{
			int y = myColor == Color.WHITE ? 0 : 7 ;
			
			//check if the positions are in check
			List<Position> positions = new ArrayList<>();
			positions.add(new Position(4,y));
			positions.add(new Position(5,y));
			positions.add(new Position(6,y));
			positions.add(new Position(7,y));
			
			for(Position position : positions)
			{
				isKingSideCastlingPossible = isKingSideCastlingPossible && !myBoard.isPositionAttacked(position, myColor);
			}
		}
		else
		{
			isCastlingPossible = false;
			return false;
		}
		
		return isKingSideCastlingPossible;
	}

	private Rook getKingSideRook()
	{
		Position rookPosition = null;
		if(myColor == Color.WHITE)
		{
			rookPosition = new Position(7,0);
		}
		else
		{
			rookPosition = new Position(7,7);
		}
		
		Piece possibleRook = myBoard.getPiece(rookPosition);
		
		if(possibleRook instanceof Rook)
		{
			return (Rook) possibleRook;
		}
		else
		{
			return null;
		}
	}
	
	private Rook getQueenSideRook()
	{
		Position rookPosition = null;
		if(myColor == Color.WHITE)
		{
			rookPosition = new Position(0,0);
		}
		else
		{
			rookPosition = new Position(0,7);
		}
		
		Piece possibleRook = myBoard.getPiece(rookPosition);
		
		if(possibleRook instanceof Rook)
		{
			return (Rook) possibleRook;
		}
		else
		{
			return null;
		}
	}

	@Override
	public List<Position> getAttackingSquares()
	{
		List<Position> destinations = new ArrayList<>();
		List<Position> attackedSquares = new ArrayList<>();

		destinations.add(myPosition.deltaXY(0, 1));
		destinations.add(myPosition.deltaXY(0, -1));
		destinations.add(myPosition.deltaXY(1, -1));
		destinations.add(myPosition.deltaXY(1, 0));
		destinations.add(myPosition.deltaXY(1, 1));
		destinations.add(myPosition.deltaXY(-1, -1));
		destinations.add(myPosition.deltaXY(-1, 0));
		destinations.add(myPosition.deltaXY(-1, 1));

		for (Position destination : destinations)
		{
			if (myBoard.isPositionOnBoard(destination))
			{
				attackedSquares.add(destination);
			}
		}

		return attackedSquares;
	}

	@Override
	public String toString()
	{
		return myColor + " King " + myPosition;
	}

	@Override
	public String getChar()
	{
		return formatChar("K");
	}
}
