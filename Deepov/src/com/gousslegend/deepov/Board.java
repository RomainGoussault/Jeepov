package com.gousslegend.deepov;

import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.deepov.pieces.Position;

public class Board
{
    private Piece[][] myPieces;

    public String toString()
    {
	return null;
    }

    public boolean isPositionFree(Position position)
    {
	int x = position.getX();
	int y = position.getY();
	return myPieces[x][y] == null;
    }
}
