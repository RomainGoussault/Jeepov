package com.gousslegend.deepov.pieces;

import java.util.ArrayList;
import java.util.List;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.board.Board;

public class Bishop extends Piece
{
	public Bishop()
	{
		super();
	}

	public Bishop(Position position, Board board, Color color)
	{
		super(position, board, color);
	}

	public Bishop(Position position, Color color)
	{
		super(position, color);
	}

	public static MoveList getPseudoLegalMoves(Piece pieceToMove)
	{
		Board board = pieceToMove.getBoard();
		Position position = pieceToMove.getPosition();

		MoveList pseudoLegalMoves = new MoveList(board);
		int i = 1;

		Move possibleMove = null;
		Position destination = null;

		i = 1;
		destination = position.deltaXY(i, -i);
		while (board.isPositionOnBoard(destination))
		{
			possibleMove = new Move(position, destination);

			if (board.isPositionFree(destination))
			{
				pseudoLegalMoves.add(possibleMove);
			}
			else
			{
				Piece piece = board.getPiece(destination);
				// look for capture
				if (pieceToMove.areColorDifferent(piece))
				{
					possibleMove.setCapturedPiece(piece);
					pseudoLegalMoves.add(possibleMove);
				}
				break;
			}

			i++;
			destination = position.deltaXY(i, -i);
		}

		i = 1;
		destination = position.deltaXY(-i, i);
		while (board.isPositionOnBoard(destination))
		{
			possibleMove = new Move(position, destination);

			if (board.isPositionFree(destination))
			{
				pseudoLegalMoves.add(possibleMove);
			}
			else
			{
				Piece piece = board.getPiece(destination);
				// look for capture
				if (pieceToMove.areColorDifferent(piece))
				{
					possibleMove.setCapturedPiece(piece);
					pseudoLegalMoves.add(possibleMove);
				}
				break;
			}

			i++;
			destination = position.deltaXY(-i, i);
		}

		i = 1;
		destination = position.deltaXY(i, i);
		while (board.isPositionOnBoard(destination))
		{
			possibleMove = new Move(position, destination);

			if (board.isPositionFree(destination))
			{
				pseudoLegalMoves.add(possibleMove);
			}
			else
			{
				Piece piece = board.getPiece(destination);
				// look for capture
				if (pieceToMove.areColorDifferent(piece))
				{
					possibleMove.setCapturedPiece(piece);
					pseudoLegalMoves.add(possibleMove);
				}
				break;
			}

			i++;
			destination = position.deltaXY(i, i);
		}

		i = 1;
		destination = position.deltaXY(-i, -i);
		while (board.isPositionOnBoard(destination))
		{
			possibleMove = new Move(position, destination);

			if (board.isPositionFree(destination))
			{
				pseudoLegalMoves.add(possibleMove);
			}
			else
			{
				Piece piece = board.getPiece(destination);
				// look for capture
				if (pieceToMove.areColorDifferent(piece))
				{
					possibleMove.setCapturedPiece(piece);
					pseudoLegalMoves.add(possibleMove);
				}
				break;
			}

			i++;
			destination = position.deltaXY(-i, -i);
		}

		return pseudoLegalMoves;
	}

	@Override
	public MoveList getPseudoLegalMoves()
	{
		return getPseudoLegalMoves(this);
	}

	public static List<Position> getAttackingSquares(Piece piece)
	{
		Board board = piece.getBoard();
		Position position = piece.getPosition();

		List<Position> attackedPositions = new ArrayList<>();
		int i = 1;
		Position destination = null;

		i = 1;
		destination = position.deltaXY(i, -i);
		while (board.isPositionOnBoard(destination))
		{
			attackedPositions.add(destination);

			if (!board.isPositionFree(destination))
			{
				break;
			}

			i++;
			destination = position.deltaXY(i, -i);
		}

		i = 1;
		destination = position.deltaXY(-i, i);
		while (board.isPositionOnBoard(destination))
		{
			attackedPositions.add(destination);

			if (!board.isPositionFree(destination))
			{
				break;
			}

			i++;
			destination = position.deltaXY(-i, i);
		}

		i = 1;
		destination = position.deltaXY(i, i);
		while (board.isPositionOnBoard(destination))
		{
			attackedPositions.add(destination);

			if (!board.isPositionFree(destination))
			{
				break;
			}

			i++;
			destination = position.deltaXY(i, i);
		}

		i = 1;
		destination = position.deltaXY(-i, -i);
		while (board.isPositionOnBoard(destination))
		{
			attackedPositions.add(destination);

			if (!board.isPositionFree(destination))
			{
				break;
			}

			i++;
			destination = position.deltaXY(-i, -i);
		}

		return attackedPositions;
	}

	public static List<Position> getAttackingSquaresTrans(Piece piece)
	{
		Board board = piece.getBoard();
		Position position = piece.getPosition();

		List<Position> attackedPositions = new ArrayList<>();
		int i = 1;
		Position destination = null;

		i = 1;
		destination = position.deltaXY(i, -i);
		while (board.isPositionOnBoard(destination))
		{
			attackedPositions.add(destination);

			i++;
			destination = position.deltaXY(i, -i);
		}

		i = 1;
		destination = position.deltaXY(-i, i);
		while (board.isPositionOnBoard(destination))
		{
			attackedPositions.add(destination);

			i++;
			destination = position.deltaXY(-i, i);
		}

		i = 1;
		destination = position.deltaXY(i, i);
		while (board.isPositionOnBoard(destination))
		{
			attackedPositions.add(destination);

			i++;
			destination = position.deltaXY(i, i);
		}

		i = 1;
		destination = position.deltaXY(-i, -i);
		while (board.isPositionOnBoard(destination))
		{
			attackedPositions.add(destination);

			i++;
			destination = position.deltaXY(-i, -i);
		}

		return attackedPositions;
	}

	public List<Position> getAttackingSquaresTrans()
	{
		return getAttackingSquaresTrans(this);
	}
	
	@Override
	public List<Position> getAttackingSquares()
	{
		return getAttackingSquares(this);
	}

	@Override
	public String toString()
	{
		return myColor + " Bishop " + myPosition;
	}

	@Override
	public String getChar()
	{
		return formatChar("B");
	}
}
