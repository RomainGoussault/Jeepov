package com.gousslegend.deepov.pieces;

import java.util.ArrayList;
import java.util.List;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.board.Board;

public class Knight extends Piece
{

	public Knight()
	{
		super();
	}

	public Knight(Color color)
	{
		super(color);
	}

	public Knight(Position position, Color color)
	{
		super(position, color);
	}
	
	public Knight(Position position, Board board, Color color)
	{
		super(position, board, color);
	}

	@Override
	public MoveList getPseudoLegalMoves()
	{
		MoveList pseudoLegalMoves = new MoveList(myBoard);
		Move possibleMove = null;

		List<Position> destinations = new ArrayList<>();
		destinations.add(myPosition.deltaXY(1, 2));
		destinations.add(myPosition.deltaXY(2, 1));
		destinations.add(myPosition.deltaXY(-1, 2));
		destinations.add(myPosition.deltaXY(-2, 1));
		destinations.add(myPosition.deltaXY(-1, -2));
		destinations.add(myPosition.deltaXY(-2, -1));
		destinations.add(myPosition.deltaXY(1, -2));
		destinations.add(myPosition.deltaXY(2, -1));

		for (Position destination : destinations)
		{
			if (myBoard.isPositionOnBoard(destination))
			{
				possibleMove = new Move(myPosition, destination);
				if (myBoard.isPositionFree(destination))
				{
					possibleMove = new Move(myPosition, destination);
					pseudoLegalMoves.add(possibleMove);
				} else
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

		return pseudoLegalMoves;
	}

	@Override
	public List<Position> getAttackingSquares()
	{
		List<Position> destinations = new ArrayList<>();
		List<Position> attackedSquares = new ArrayList<>();

		destinations.add(myPosition.deltaXY(1, 2));
		destinations.add(myPosition.deltaXY(2, 1));
		destinations.add(myPosition.deltaXY(-1, 2));
		destinations.add(myPosition.deltaXY(-2, 1));
		destinations.add(myPosition.deltaXY(-1, -2));
		destinations.add(myPosition.deltaXY(-2, -1));
		destinations.add(myPosition.deltaXY(1, -2));
		destinations.add(myPosition.deltaXY(2, -1));

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
		return myColor + " Knight " + myPosition;
	}

	@Override
	public String getChar()
	{
		return formatChar("N");

	}
}