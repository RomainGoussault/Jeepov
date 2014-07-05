package com.gousslegend.deepov;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

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

    /*
     * public Board(Piece[][] pieces) { myPieces = pieces; }
     */

    public void addPiece(Piece piece)
    {
	Position position = piece.getMyPosition();
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

    public boolean isCheck(Color color)
    {
	Piece king = getKing(color);
	List<Piece> ennemyPieces = getEnnemiesPieces(color);

	for (Piece ennemyPiece : ennemyPieces)
	{
	    isPieceChecking(ennemyPiece, king);
	}
	return false;
    }

    private void isPieceChecking(Piece ennemyPiece, Piece king)
    {
	//knigth check attacking square
	
	//pawn check 2 attacking square
	
	//queen, rook, bishop get end of attacking square
	
	
    }

    public List<Piece> getEnnemiesPieces(Color color)
    {
	return getPieces(color.getOppositeColor());
    }

    private List<Piece> getPieces(Color color)
    {
	List<Piece> pieces = new Vector<>();

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
}