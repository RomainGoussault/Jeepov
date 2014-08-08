package com.gousslegend.deepov.pieces;

import java.util.ArrayList;
import java.util.List;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.board.Board;
import com.gousslegend.deepov.board.Board;

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

	public Rook(Position position, Color color)
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
		destination = position.deltaX(i);
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
			destination = position.deltaX(i);
		}

		i = -1;
		destination = position.deltaX(i);
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

			i--;
			destination = position.deltaX(i);
		}

		i = -1;
		destination = position.deltaY(i);
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

			i--;
			destination = position.deltaY(i);
		}

		i = 1;
		destination = position.deltaY(i);
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
			destination = position.deltaY(i);
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
		Position position = piece.getPosition();
		Board board = piece.getBoard();

		List<Position> attackedPositions = new ArrayList<>();
		int i = 1;
		Position destination = null;

		i = 1;
		destination = position.deltaX(i);
		while (board.isPositionOnBoard(destination))
		{
			attackedPositions.add(destination);

			if (!board.isPositionFree(destination))
			{
				break;
			}

			i++;
			destination = position.deltaX(i);
		}

		i = -1;
		destination = position.deltaX(i);
		while (board.isPositionOnBoard(destination))
		{
			attackedPositions.add(destination);

			if (!board.isPositionFree(destination))
			{
				break;
			}

			i--;
			destination = position.deltaX(i);
		}

		i = -1;
		destination = position.deltaY(i);
		while (board.isPositionOnBoard(destination))
		{
			attackedPositions.add(destination);

			if (!board.isPositionFree(destination))
			{
				break;
			}

			i--;
			destination = position.deltaY(i);
		}

		i = 1;
		destination = position.deltaY(i);
		while (board.isPositionOnBoard(destination))
		{
			attackedPositions.add(destination);

			if (!board.isPositionFree(destination))
			{
				break;
			}

			i++;
			destination = position.deltaY(i);
		}

		return attackedPositions;
	}

	@Override
	public List<Position> getAttackingSquares()
	{
		return getAttackingSquares(this);
	}

	@Override
	public String toString()
	{
		return myColor + " Rook " + myPosition;
	}

	@Override
	public String getChar()
	{
		return formatChar("R");
	}
}
