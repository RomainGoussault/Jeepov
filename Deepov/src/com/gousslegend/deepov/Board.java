package com.gousslegend.deepov;

import com.gousslegend.deepov.pieces.Piece;

public class Board
{
    private Piece[][] myPieces;
    private static final int BOARD_SIZE= 7;

    public Board()
    {
	myPieces = new Piece[8][8];
    }
    
    public Board(Piece[][] pieces)
    {
	myPieces = pieces;
    }
    
    public void addPiece(Piece piece)
    {
	Position position = piece.getMyPosition();
	int x = position.getX();
	int y = position.getY();
	myPieces[x][y] = piece;
    }
    
    public String toString()
    {
	return null;
    }

    public boolean isPositionFree(Position position)
    {
	int x = position.getX();
	int y = position.getY();
	
	if(x > BOARD_SIZE || y > BOARD_SIZE)
	{
	    return false;
	}
	
	if(x < 0 || y < 0)
	{
	    return false;
	}
	
	return myPieces[x][y] == null;
    }
}
