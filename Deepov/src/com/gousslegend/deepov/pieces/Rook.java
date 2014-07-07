package com.gousslegend.deepov.pieces;

import java.util.ArrayList;
import java.util.List;

import com.gousslegend.deepov.Board;
import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;

public class Rook extends Piece
{
	public Rook()
	{
		super();
	}

	public Rook(Color color)
	{
		super(color);
	}

	public Rook(Position position, Board board, Color color)
	{
		super(position, board, color);
	}

	@Override
	public MoveList getPseudoLegalMoves()
	{
		MoveList pseudoLegalMoves = new MoveList(myBoard);
		int i = 1;

		Move possibleMove = null;
		Position destination = null;

		i = 1;
		destination = myPosition.deltaX(i);
		while (myBoard.isPositionOnBoard(destination))
		{
			possibleMove = new Move(myPosition, destination);

			if (myBoard.isPositionFree(destination))
			{
				pseudoLegalMoves.add(possibleMove);
			}
			else
			{
				Piece piece = myBoard.getPiece(destination);
				// look for capture
				if (areColorDifferent(piece))
				{
					possibleMove.setCapturedPiece(piece);
					pseudoLegalMoves.add(possibleMove);
				}
				break;
			}

			i++;
			destination = myPosition.deltaX(i);
		}

		i = -1;
		destination = myPosition.deltaX(i);
		while (myBoard.isPositionOnBoard(destination))
		{
			possibleMove = new Move(myPosition, destination);

			if (myBoard.isPositionFree(destination))
			{
				pseudoLegalMoves.add(possibleMove);
			}
			else
			{
				Piece piece = myBoard.getPiece(destination);
				// look for capture
				if (areColorDifferent(piece))
				{
					possibleMove.setCapturedPiece(piece);
					pseudoLegalMoves.add(possibleMove);
				}
				break;
			}

			i--;
			destination = myPosition.deltaX(i);
		}

		i = -1;
		destination = myPosition.deltaY(i);
		while (myBoard.isPositionOnBoard(destination))
		{
			possibleMove = new Move(myPosition, destination);

			if (myBoard.isPositionFree(destination))
			{
				pseudoLegalMoves.add(possibleMove);
			}
			else
			{
				Piece piece = myBoard.getPiece(destination);
				// look for capture
				if (areColorDifferent(piece))
				{
					possibleMove.setCapturedPiece(piece);
					pseudoLegalMoves.add(possibleMove);
				}
				break;
			}

			i--;
			destination = myPosition.deltaY(i);
		}

		i = 1;
		destination = myPosition.deltaY(i);
		while (myBoard.isPositionOnBoard(destination))
		{
			possibleMove = new Move(myPosition, destination);

			if (myBoard.isPositionFree(destination))
			{
				pseudoLegalMoves.add(possibleMove);
			}
			else
			{
				Piece piece = myBoard.getPiece(destination);
				// look for capture
				if (areColorDifferent(piece))
				{
					possibleMove.setCapturedPiece(piece);
					pseudoLegalMoves.add(possibleMove);
				}
				break;
			}

			i++;
			destination = myPosition.deltaY(i);
		}

		return pseudoLegalMoves;
	}

	@Override
	public List<Position> getAttackingSquares()
	{
		List<Position> attackedPositions = new ArrayList<>();
		int i = 1;

		Position destination = null;

		i = 1;
		destination = myPosition.deltaX(i);
		while (myBoard.isPositionOnBoard(destination))
		{
			if (!myBoard.isPositionFree(destination))
			{
				Piece piece = myBoard.getPiece(destination);
				if (areColorDifferent(piece))
				{
					attackedPositions.add(destination);
				}
				break;
			}

			i++;
			destination = myPosition.deltaX(i);
		}

		i = -1;
		destination = myPosition.deltaX(i);
		while (myBoard.isPositionOnBoard(destination))
		{
			if (!myBoard.isPositionFree(destination))
			{
				Piece piece = myBoard.getPiece(destination);
				if (areColorDifferent(piece))
				{
					attackedPositions.add(destination);
				}
				break;
			}
			i--;
			destination = myPosition.deltaX(i);
		}

		i = -1;
		destination = myPosition.deltaY(i);
		while (myBoard.isPositionOnBoard(destination))
		{
			if (!myBoard.isPositionFree(destination))
			{
				Piece piece = myBoard.getPiece(destination);
				if (areColorDifferent(piece))
				{
					attackedPositions.add(destination);
				}
				break;
			}

			i--;
			destination = myPosition.deltaY(i);
		}

		i = 1;
		destination = myPosition.deltaY(i);
		while (myBoard.isPositionOnBoard(destination))
		{
			if (!myBoard.isPositionFree(destination))
			{
				Piece piece = myBoard.getPiece(destination);
				if (areColorDifferent(piece))
				{
					attackedPositions.add(destination);
				}
				break;
			}

			i++;
			destination = myPosition.deltaY(i);
		}

		return attackedPositions;
	}
}
