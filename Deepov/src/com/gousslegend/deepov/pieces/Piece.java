package com.gousslegend.deepov.pieces;

import java.awt.Point;
import java.util.List;

import com.gousslegend.deepov.Board;
import com.gousslegend.deepov.Move;

public abstract class Piece
{
    protected Point myPosition;
    protected int myValue;
    protected Board myBoard;
    protected boolean myIsBlack;

    public Piece(Point position, Board board, boolean isBlack)
    {
	myPosition = position;
	myBoard = board;
	myIsBlack = isBlack;
    }

    /**
     * This method get all the possible moves for the piece
     * 
     * @return
     */
    public abstract List<Move> getLegalMoves();

    public Point getMyPosition()
    {
	return myPosition;
    }

    public void setMyPosition(Point myPosition)
    {
	this.myPosition = myPosition;
    }

    public Board getMyBoard()
    {
	return myBoard;
    }

    public void setMyBoard(Board myBoard)
    {
	this.myBoard = myBoard;
    }

    public int getMyValue()
    {
	return myValue;
    }

    public boolean isMyIsBlack()
    {
	return myIsBlack;
    }
}
