package com.gousslegend.deepov.pieces;

import java.util.List;

import com.gousslegend.deepov.Board;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.Position;


public abstract class Piece
{
    protected Position myPosition;
    protected int myValue;
    protected Board myBoard;
    protected boolean myIsBlack;

    public Piece(Position position, Board board, boolean isBlack)
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

    public Position getMyPosition()
    {
	return myPosition;
    }

    public void setMyPosition(Position myPosition)
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
