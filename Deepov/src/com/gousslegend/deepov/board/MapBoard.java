package com.gousslegend.deepov.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.pieces.Bishop;
import com.gousslegend.deepov.pieces.King;
import com.gousslegend.deepov.pieces.Knight;
import com.gousslegend.deepov.pieces.Pawn;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.deepov.pieces.Queen;
import com.gousslegend.deepov.pieces.Rook;

public class MapBoard extends Board
{
	private Map<Position, Piece> myPieces;
	public MapBoard()
	{
		myPieces = new HashMap<>(40);
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
		for(int i = 0; i<=7; i++)
		{
			addPiece(new Pawn(new Position(i,1), this, Color.WHITE));
			addPiece(new Pawn(new Position(i,6), this, Color.BLACK));
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
		addPiece(new King(new Position(4,0), this, Color.WHITE));
		addPiece(new King(new Position(4,7), this, Color.BLACK));
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
		myPieces.remove(origin);
		pieceToMove.setPosition(destination);
		pieceToMove.incrementMoveCounter();

		if (isCaptureMove)
		{
			myPieces.remove(destination);

		}
		
		if(getPiece(destination) != null)
		{
			System.out.println("ERROR: Destination not null for Move " + move);

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
		pieceMoved.decrementMoveCounter();

		myPieces.remove(destination);
		if (isCaptureMove)
		{
			Piece pieceCaptured = move.getCapturedPiece();
			myPieces.put(pieceCaptured.getPosition(), pieceCaptured);
		}
		pieceMoved.setPosition(origin);
		myPieces.put(origin, pieceMoved);
		
		myMoves.remove(getLastMove());
	}
	
	protected List<Piece> getPieces(Color color)
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
	
	protected List<Piece> getPieces()
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