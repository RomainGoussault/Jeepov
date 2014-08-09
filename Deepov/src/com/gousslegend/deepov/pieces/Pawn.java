package com.gousslegend.deepov.pieces;

import java.util.ArrayList;
import java.util.List;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.board.Board;

public class Pawn extends Piece
{
	public Pawn(Position position, Board board, Color color)
	{
		super(position, board, color);
	}

	public Pawn(Position position, Color color)
	{
		super(position, color);
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
		boolean promotion = isGoingToPromote();
		Position destination = null;
		Piece possibleCapture = null;
		Move possibleMove = null;

		if (isOnStartingRank()) // +2 square forward move
		{
			destination = myPosition.deltaY(2 * direction);
			if (myBoard.isPositionFree(destination)
					&& myBoard.isPositionFree(myPosition.deltaY(direction)))
			{
				pseudoLegalMoves.add(new Move(myPosition, destination));
			}
		}
		else if (enPassantCapturePossible())// En Passant
		{
			Position ennemyPawnPosition = myBoard.getLastMove()
					.getDestination();
			possibleMove = new Move(myPosition,
					ennemyPawnPosition.deltaY(direction));
			possibleMove.setIsEnPassant(true);
			possibleMove.setCapturedPiece(myBoard.getPiece(ennemyPawnPosition));
			pseudoLegalMoves.add(possibleMove);
		}

		// no need to check if we are on the edge on the board:
		// it should not happen because it will be promoted before
		destination = myPosition.deltaY(direction);
		if (myBoard.isPositionFree(destination))
		{
			if (promotion)
			{
				pseudoLegalMoves.addAll(getPromotionMoves(destination));
			}
			else
			{
				possibleMove = new Move(myPosition, destination);
				pseudoLegalMoves.add(possibleMove);
			}
		}

		int[] y = new int[] { -1, 1 };
		for (int i = 0; i <= 1; i++)
		{
			destination = myPosition.deltaXY(y[i], direction);
			if (myBoard.isPositionOnBoard(destination))
			{
				possibleCapture = myBoard.getPiece(destination);

				if (possibleCapture != null	&& areColorDifferent(possibleCapture))
				{
					if (promotion)
					{
						pseudoLegalMoves
								.addAll(getPromotionMovesWithCapture(destination));
					}
					else
					{
						possibleMove = new Move(myPosition, destination);
						possibleMove.setCapturedPiece(possibleCapture);
						pseudoLegalMoves.add(possibleMove);
					}
				}
			}
		}

		return pseudoLegalMoves;
	}

	public List<Move> getPromotionMovesWithCapture(Position destination)
	{
		List<Move> moves = new ArrayList<>();
		Move possibleMove = null;
		Piece possibleCapture = null;

		possibleMove = new Move(myPosition, destination);
		possibleMove.setIsPromotion(true);
		possibleMove.setPromotedPawn(this);
		possibleMove.setPromotedPiece(new Queen(destination, myBoard, myColor));
		possibleCapture = myBoard.getPiece(destination);
		possibleMove.setCapturedPiece(possibleCapture);
		moves.add(possibleMove);

		possibleMove = new Move(myPosition, destination);
		possibleMove.setIsPromotion(true);
		possibleMove.setPromotedPawn(this);
		possibleMove.setPromotedPiece(new Rook(destination, myBoard, myColor));
		possibleCapture = myBoard.getPiece(destination);
		possibleMove.setCapturedPiece(possibleCapture);
		moves.add(possibleMove);

		possibleMove = new Move(myPosition, destination);
		possibleMove.setIsPromotion(true);
		possibleMove.setPromotedPawn(this);
		possibleMove.setPromotedPiece(new Bishop(destination, myBoard, myColor));
		possibleCapture = myBoard.getPiece(destination);
		possibleMove.setCapturedPiece(possibleCapture);
		moves.add(possibleMove);

		possibleMove = new Move(myPosition, destination);
		possibleMove.setIsPromotion(true);
		possibleMove.setPromotedPawn(this);
		possibleMove.setPromotedPiece(new Knight(destination, myBoard, myColor));
		possibleCapture = myBoard.getPiece(destination);
		possibleMove.setCapturedPiece(possibleCapture);
		moves.add(possibleMove);

		return moves;
	}

	public List<Move> getPromotionMoves(Position destination)
	{
		List<Move> moves = new ArrayList<>();
		Move possibleMove = null;

		possibleMove = new Move(myPosition, destination);
		possibleMove.setIsPromotion(true);
		possibleMove.setPromotedPawn(this);
		possibleMove.setPromotedPiece(new Queen(destination, myBoard, myColor));
		moves.add(possibleMove);

		possibleMove = new Move(myPosition, destination);
		possibleMove.setIsPromotion(true);
		possibleMove.setPromotedPawn(this);
		possibleMove.setPromotedPiece(this);
		possibleMove.setPromotedPiece(new Rook(destination, myBoard, myColor));
		moves.add(possibleMove);

		possibleMove = new Move(myPosition, destination);
		possibleMove.setIsPromotion(true);
		possibleMove.setPromotedPawn(this);
		possibleMove.setPromotedPiece(new Bishop(destination, myBoard, myColor));
		moves.add(possibleMove);

		possibleMove = new Move(myPosition, destination);
		possibleMove.setIsPromotion(true);
		possibleMove.setPromotedPawn(this);
		possibleMove.setPromotedPiece(new Knight(destination, myBoard, myColor));
		moves.add(possibleMove);

		return moves;
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
		}
		else
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

			boolean isEnnemyPawnOnNextColunns = Math.abs(ennemyMoveDestination
					.getX() - myPosition.getX()) == 1;

			return ennemyPawnMoved && plus2Move && isEnnemyPawnOnNextColunns;
		}

		return false;
	}

	private boolean isOnGoodRankforEnPassant()
	{
		if (myColor == Color.WHITE)
		{
			return myPosition.getY() == 4;
		}
		else
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
			attackedPositions.add(destination);
		}

		destination = myPosition.deltaXY(1, direction);
		if (myBoard.isPositionOnBoard(destination))
		{
			attackedPositions.add(destination);
		}

		return attackedPositions;
	}

	public boolean isOnStartingRank()
	{
		if (myColor == Color.WHITE)
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
		if (myColor == Color.WHITE)
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

	private boolean isGoingToPromote()
	{
		if (myColor == Color.WHITE)
		{
			return myPosition.getY() == 6;
		}
		else
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