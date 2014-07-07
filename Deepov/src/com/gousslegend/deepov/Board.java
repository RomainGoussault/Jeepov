package com.gousslegend.deepov;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.gousslegend.deepov.pieces.King;
import com.gousslegend.deepov.pieces.Piece;

public class Board
{
	private Map<Position, Piece> myPieces;
	public static final int BOARD_SIZE = 7;

	public Board()
	{
		myPieces = new HashMap<>(64);
	}

	public void addPiece(Piece piece)
	{
		Position position = piece.getPosition();
		myPieces.put(position, piece);
	}

	public String toString()
	{
		return null;
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