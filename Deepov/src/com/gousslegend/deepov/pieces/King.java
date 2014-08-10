package com.gousslegend.deepov.pieces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.board.Board;

public class King extends Piece
{
	public King()
	{
		super();
	}

	public King(Color color)
	{
		super(color);
	}

	public King(Position position, Color color)
	{
		super(position, color);
	}
	
	public King(Position position,Board board, Color color)
	{
		super(position, board, color);
	}
	/**
	 * This method get all the legal moves for the piece
	 * 
	 * @return
	 */
	public MoveList getLegalMoves()
	{
		MoveList legalMoves = getPseudoLegalMoves();
		legalMoves.setBoard(myBoard);
		
		Iterator<Move> moveIterator = legalMoves.getList().iterator();
		while (moveIterator.hasNext())
		{
			Move move = moveIterator.next();

				myBoard.executeMove(move);
				if (myBoard.isCheck(myColor))
				{
					myBoard.undoMove(move);
					moveIterator.remove();
				}
				else
				{
					myBoard.undoMove(move);
				}
		}

		return legalMoves;
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

		if (!this.hasMoved() && getInitialKingPosition().equals(myPosition))
		{
			if(isKingSideCastlingPossible())
			{
				possibleMove = new Move(myPosition, new Position(6, myPosition.getY()));
				possibleMove.setisCastling(true);
				pseudoLegalMoves.add(possibleMove);

			}
			if(isQueenSideCastlingPossible())
			{
				possibleMove = new Move(myPosition, new Position(2, myPosition.getY()));
				possibleMove.setisCastling(true);
				pseudoLegalMoves.add(possibleMove);
			}
		}

		return pseudoLegalMoves;
	}

	private Position getInitialKingPosition()
	{
		int y = myColor == Color.WHITE ? 0 : 7 ;
		return new Position(4,y);
	}

	private boolean isQueenSideCastlingPossible()
	{
		boolean isQueenSideCastlingPossible = true;
		
		Rook rook = getQueenSideRook();
		
		if(rook != null && !rook.hasMoved())
		{
			int y = myColor == Color.WHITE ? 0 : 7 ;
			
			//check if the positions are not in check
			List<Position> positions = new ArrayList<>();
			positions.add(new Position(2,y));
			positions.add(new Position(3,y));
			positions.add(new Position(4,y));
			
			for(Position position : positions)
			{
				isQueenSideCastlingPossible = isQueenSideCastlingPossible && !myBoard.isPositionAttacked(position, myColor);
			}
				
			isQueenSideCastlingPossible = isQueenSideCastlingPossible && myBoard.isPositionFree(new Position(1,y));
			isQueenSideCastlingPossible = isQueenSideCastlingPossible && myBoard.isPositionFree(new Position(2,y));
			isQueenSideCastlingPossible = isQueenSideCastlingPossible && myBoard.isPositionFree(new Position(3,y));
		}
		else
		{
			//isCastlingPossible = false;
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
			
			for(Position position : positions)
			{
				isKingSideCastlingPossible = isKingSideCastlingPossible && !myBoard.isPositionAttacked(position, myColor);
			}
			
			//check if positions are free
			isKingSideCastlingPossible = isKingSideCastlingPossible && myBoard.isPositionFree(new Position(5,y));
			isKingSideCastlingPossible = isKingSideCastlingPossible && myBoard.isPositionFree(new Position(6,y));

		}
		else
		{
			//isCastlingPossible = false;
			return false;
		}
		
		return isKingSideCastlingPossible;
	}

	private Rook getKingSideRook()
	{
		Position rookPosition = new Position(7,myPosition.getY());
		
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
		Position rookPosition = new Position(0,myPosition.getY());
		
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

	public List<Move> getCastlingMoves()
	{
		MoveList moves = getPseudoLegalMoves();
		ArrayList<Move> castlingMoves = new ArrayList<>();
		
		for(Move move : moves.getList())
		{
			if(move.isCastling())
			{
				castlingMoves.add(move);
			}
		}
		
		return castlingMoves;
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