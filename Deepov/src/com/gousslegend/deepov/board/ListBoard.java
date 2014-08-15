package com.gousslegend.deepov.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.pieces.King;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.deepov.pieces.Queen;

public class ListBoard extends Board
{
	private ArrayList<Piece> myPieces;

	public ListBoard()
	{
		myPieces = new ArrayList<>(40);
		myMoves = new ArrayList<>();
	}
	
	@Override
	public void addPiece(Piece piece)
	{
		myPieces.add(piece);
	}

	@Override
	public Piece getPiece(Position position)
	{
		for(Piece piece : myPieces)
		{
			if(piece.getPosition().equals(position))
			{
				return piece;
			}
		}
		
		return null;
	}

	@Override
	public boolean isPositionFree(Position position)
	{
		for(Piece piece : myPieces)
		{
			if(piece.getPosition().equals(position))
			{
				return false;
			}
		}
		
		return true;
	}

	@Override
	public List<Piece> getPieces(Color color)
	{
		ArrayList<Piece> pieces = new ArrayList<>();
		
		for(Piece piece : myPieces)
		{
			if(piece.getColor() == color)
			{
				pieces.add(piece);
			}
		}
		
		return pieces;
	}

	@Override
	protected List<Piece> getPieces()
	{
		return myPieces;
	}

	@Override
	public King getKing(Color color)
	{
		for(Piece piece : myPieces)
		{
			if(piece.getColor() == color)
			{
				if(piece instanceof King)
				{
					return (King) piece;
				}
			}
		}
		
		return null;
	}

	@Override
	public Position getKingPosition(Color color)
	{
		return getKing(color).getPosition();
	}

	@Override
	public void removePiece(Position position)
	{
		Piece pieceToRemove = null;
				
		for(Piece piece : myPieces)
		{
			if(piece.getPosition().equals(position))
			{
				pieceToRemove = piece;
				break;
			}
		}
		myPieces.remove(pieceToRemove);
	}
	
	public void executeMove(Move move)
	{
		Position origin = move.getOrigin();
		Position destination = move.getDestination();
		boolean isCaptureMove = move.getCapturedPiece() != null;

		if (isCaptureMove)
		{
			removePiece(move.getCapturedPiece().getPosition());
		}
		
		if(getPiece(destination) != null)
		{
			//	System.out.println("ERROR: Destination not null for Move " + move);
		}
		Piece pieceToMove = getPiece(origin);
		//removePiece(origin);
		pieceToMove.setPosition(destination);
		pieceToMove.incrementMoveCounter();

		
		//addPiece(pieceToMove);
		
		if(move.isPromotion())
		{
			//remove the pawn
			removePiece(destination);
			//add a queen
			addPiece(new Queen(destination, this, pieceToMove.getColor()));
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

		pieceMoved.setPosition(origin);
		if (isCaptureMove)
		{
			Piece pieceCaptured = move.getCapturedPiece();
			addPiece(pieceCaptured);
		}
		
		myMoves.remove(getLastMove());
	}
}