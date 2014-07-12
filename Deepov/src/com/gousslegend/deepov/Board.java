package com.gousslegend.deepov;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.gousslegend.deepov.pieces.Bishop;
import com.gousslegend.deepov.pieces.King;
import com.gousslegend.deepov.pieces.Knight;
import com.gousslegend.deepov.pieces.Pawn;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.deepov.pieces.Queen;
import com.gousslegend.deepov.pieces.Rook;

public class Board
{
	private Map<Position, Piece> myPieces;
	public static final int BOARD_SIZE = 7;
	
	/** Move taken in this game so far */
	private List<Move> myMoves;

	public Board()
	{
		myPieces = new HashMap<>(32,1);
		myMoves = new ArrayList<>();
	}

	public void addPiece(Piece piece)
	{
		Position position = piece.getPosition();
		myPieces.put(position, piece);
	}

	public void setupBoard()
	{
		//Add Pawns
		for(int i = 0; i<8; i++)
		{
			addPiece(new Pawn(new Position(1,i), this, Color.WHITE));
			addPiece(new Pawn(new Position(6,i), this, Color.BLACK));
		}

		addPiece(new Rook(new Position(0,0), this, Color.WHITE));
		addPiece(new Rook(new Position(7,0), this, Color.WHITE));
		addPiece(new Rook(new Position(0,7), this, Color.BLACK));
		addPiece(new Rook(new Position(7,7), this, Color.BLACK));
		
		addPiece(new Knight(new Position(1,0), this, Color.WHITE));
		addPiece(new Knight(new Position(6,0), this, Color.WHITE));
		addPiece(new Knight(new Position(1,7), this, Color.BLACK));
		addPiece(new Knight(new Position(6,7), this, Color.BLACK));
		
		addPiece(new Bishop(new Position(2,0), this, Color.WHITE));
		addPiece(new Bishop(new Position(5,0), this, Color.WHITE));
		addPiece(new Bishop(new Position(2,7), this, Color.BLACK));
		addPiece(new Bishop(new Position(5,7), this, Color.BLACK));
		
		addPiece(new Queen(new Position(3,0), this, Color.WHITE));
		addPiece(new Queen(new Position(3,7), this, Color.BLACK));
		addPiece(new King(new Position(4,7), this, Color.BLACK));
		addPiece(new King(new Position(4,7), this, Color.BLACK));
	}
	
	@Override
	public String toString()
	{
		return "Board [myPieces=" + myPieces + ", myMoves=" + myMoves + "]";
	}

	public Piece getPiece(Position position)
	{
		return myPieces.get(position);
	}

	public boolean isPositionFree(Position position)
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

		return !myPieces.containsKey(position);
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
			if (isPieceChecking(ennemyPiece, kingPosition))
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
	
	public Piece getKing(Color color)
	{
		for (Entry<Position, Piece> entry : myPieces.entrySet())
		{
			Piece piece = entry.getValue();
			if (piece instanceof King && piece.getColor() == color)
			{
				return piece;
			}
		}

		// There should always be a king for each color on the board
		return null;
	}

	public Position getKingPosition(Color color)
	{
		for (Entry<Position, Piece> entry : myPieces.entrySet())
		{
			Piece piece = entry.getValue();
			if (piece instanceof King && piece.getColor() == color)
			{
				return piece.getPosition();
			}
		}

		// There should always be a king for each color on the board
		return null;
	}

	public void executeMove(Move move)
	{
		Position origin = move.getOrigin();
		Position destination = move.getDestination();
		boolean isCaptureMove = move.getCapturedPiece() != null;

		Piece pieceToMove = getPiece(origin);
		pieceToMove.setPosition(destination);

		myPieces.remove(origin);
		if (isCaptureMove)
		{
			myPieces.remove(destination);
		}
		myPieces.put(destination, pieceToMove);
		
		if(move.isPromotion())
		{
			//remove the pawn
			myPieces.remove(destination);
			//add a queen
			myPieces.put(destination, new Queen(destination, this, pieceToMove.getColor()));
		}
		
		myMoves.add(move);
	}

	public void undoMove(Move move)
	{
		Position origin = move.getOrigin();
		Position destination = move.getDestination();
		boolean isCaptureMove = move.getCapturedPiece() != null;
		
		Piece pieceMoved = getPiece(destination);

		myPieces.remove(destination);
		if (isCaptureMove)
		{
			myPieces.put(destination, move.getCapturedPiece());
		}
		pieceMoved.setPosition(origin);
		myPieces.put(origin, pieceMoved);
		
		myMoves.remove(getLastMove());
	}
	
	public List<Move> getMoves()
	{
		return myMoves;
	}
	
	/**
	 * This method return the number of actives pieces on the board
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
	
	private boolean isPieceChecking(Piece ennemyPiece, Position kingPosition)
	{
		List<Position> attackingSquares = ennemyPiece.getAttackingSquares();
		
		return attackingSquares.contains(kingPosition);
	}
	
	private List<Piece> getPieces(Color color)
	{
		List<Piece> pieces = new ArrayList<>();
		
		for (Entry<Position, Piece> entry : myPieces.entrySet())
		{
			Piece piece = entry.getValue();
			if (piece.getColor() == color)
			{
				pieces.add(piece);
			}
		}
		return pieces;
	}
	
	private List<Piece> getPieces()
	{
		List<Piece> pieces = new ArrayList<>();
		
		for (Entry<Position, Piece> entry : myPieces.entrySet())
		{
			Piece piece = entry.getValue();
			pieces.add(piece);
		}
		return pieces;
	}
}