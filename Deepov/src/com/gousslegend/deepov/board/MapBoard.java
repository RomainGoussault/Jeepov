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
import com.gousslegend.deepov.utils.Utils;

public class MapBoard extends Board
{
	private Map<Position, Piece> myPieces;
	public MapBoard()
	{
		myPieces = new HashMap<>(40);
		myMoves = new ArrayList<>();
	}
	
	public MapBoard(String fen)
	{
		myPieces = new HashMap<>(40);
		myMoves = new ArrayList<>();
		
		addPieces(Utils.getPiecesFromFen(fen));
	}
	
	public void addPiece(Piece piece)
	{
		Position position = piece.getPosition();
		myPieces.put(position, piece);
	}

	public void addPieces(List<Piece> pieces)
	{
		for(Piece piece : pieces)
		{
			piece.setBoard(this);
			Position position = piece.getPosition();
			myPieces.put(position, piece);
		}
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
	
	public King getKing(Color color)
	{
		for (Entry<Position, Piece> entry : myPieces.entrySet())
		{
			Piece piece = entry.getValue();
			if (piece instanceof King && piece.getColor() == color)
			{
				return (King) piece;
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

	public List<Piece> getPieces(Color color)
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

	@Override
	public void removePiece(Position position)
	{
		myPieces.remove(position);
	}
}