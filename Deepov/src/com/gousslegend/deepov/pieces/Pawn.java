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

		// no need to check if we are on the edge on the board:
		// it should not happen because it will be promoted before
		destination = myPosition.deltaY(direction);
		if (myBoard.isPositionFree(destination))
		{
			pseudoLegalMoves.add(new Move(myPosition, destination));
		}

		// check to the right and the left for capture
		Piece possibleCapture = null;
		Move possibleMove = null;

		destination = myPosition.deltaXY(1, direction);
		possibleCapture = myBoard.getPiece(destination);
		if (possibleCapture != null && areColorDifferent(possibleCapture))
		{
			possibleMove = new Move(myPosition, destination);
			possibleMove.setCapturedPiece(possibleCapture);
			pseudoLegalMoves.add(possibleMove);
		}

		destination = myPosition.deltaXY(-1, direction);
		possibleCapture = myBoard.getPiece(destination);
		if (possibleCapture != null && areColorDifferent(possibleCapture))
		{
			possibleMove = new Move(myPosition, destination);
			possibleMove.setCapturedPiece(possibleCapture);
			pseudoLegalMoves.add(possibleMove);
		}

		if (isGoingToPromote()) // Promotion
		{
			for (Move move : pseudoLegalMoves.getList())
			{
				move.setIsPromotion(true);
			}
		} else if (isOnStartingRank()) // +2 square forward move
		{
			destination = myPosition.deltaY(2 * direction);
			if (myBoard.isPositionFree(destination))
			{
				pseudoLegalMoves.add(new Move(myPosition, destination));
			}
		} else if (enPassantCapturePossible())// En Passant
		{
			Position ennemyPawnPosition = myBoard.getLastMove()
					.getDestination();
			possibleMove = new Move(myPosition, ennemyPawnPosition);
			possibleMove.setCapturedPiece(myBoard.getPiece(ennemyPawnPosition));
			pseudoLegalMoves.add(new Move(myPosition, ennemyPawnPosition));
		}

		return pseudoLegalMoves;
	}

	@Override
	public String toString()
	{
		return myColor + " Pawn " + myPosition;
	}

	private boolean enPassantCapturePossible()
	{
		if (!isOnGoodRankforEnPassant())
		{
			return false;
		}

		if (ennemyLastMoveAllowEnPassant())
		{
			return true;
		} else
		{
			return false;
		}
	}

	private boolean ennemyLastMoveAllowEnPassant()
	{
		Move lastEnnemyMove = myBoard.getLastMove();
		if (lastEnnemyMove != null)
		{
			Position ennemyMoveOrigin = lastEnnemyMove.getOrigin();
			Position ennemyMoveDestination = lastEnnemyMove.getDestination();

			boolean ennemyPawnMoved = myBoard.getPiece(lastEnnemyMove
					.getDestination()) instanceof Pawn;
			boolean plus2Move = ennemyMoveDestination.getY()
					- ennemyMoveOrigin.getY() == -2 * getDirection();

			if (ennemyPawnMoved && plus2Move)
			{
				return true;
			}
		}

		return false;
	}

	private boolean isOnGoodRankforEnPassant()
	{
		if (myColor == Color.WHITE)
		{
			return myPosition.getY() == 4;
		} else
		{
			return myPosition.getY() == 3;
		}
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
		if (myColor == Color.WHITE)
		{
			return myPosition.getY() == 1;
		} else
		{
			return myPosition.getY() == 6;
		}
	}

	public boolean isOnLastRank()
	{
		if (myColor == Color.WHITE)
		{
			return myPosition.getY() == 7;
		} else
		{
			return myPosition.getY() == 0;
		}
	}

	public int getDirection()
	{
		if (myColor == Color.WHITE)
		{
			return 1;
		} else
		{
			return -1;
		}
	}

	private boolean isGoingToPromote()
	{
		if (myColor == Color.WHITE)
		{
			return myPosition.getY() == 6;
		} else
		{
			return myPosition.getY() == 1;
		}
	}

	@Override
	public String getChar()
	{
		return formatChar("P");
	}
}
