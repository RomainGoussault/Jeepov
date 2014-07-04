package com.gousslegend.deepov;

import com.gousslegend.deepov.pieces.Piece;


public class Move
{
    private Position myOrigin;
    private Position myDestination;
    private boolean myIsCaptured;
    private Piece myCapturedPiece;
    private int myScore;

    public Move(Position origin, Position destination)
    {
	setOrigin(origin);
	setDestination(destination);
    }

    public Position getOrigin()
    {
	return myOrigin;
    }

    public void setOrigin(Position myOrigin)
    {
	this.myOrigin = myOrigin;
    }

    public Position getDestination()
    {
	return myDestination;
    }

    public void setDestination(Position myDestination)
    {
	this.myDestination = myDestination;
    }

    public boolean isCaptured()
    {
	return myIsCaptured;
    }

    public void setIsCaptured(boolean myIsCaptured)
    {
	this.myIsCaptured = myIsCaptured;
    }

    public int getScore()
    {
	return myScore;
    }

    public void setScore(int myScore)
    {
	this.myScore = myScore;
    }

    public Piece getCapturedPiece()
    {
	return myCapturedPiece;
    }

    public void setCapturedPiece(Piece myCapturedPiece)
    {
	this.myCapturedPiece = myCapturedPiece;
    }

    @Override
    public String toString()
    {
	return "Move [myOrigin=" + myOrigin + ", myDestination="
		+ myDestination + ", myCapturedPiece=" + myCapturedPiece + "]";
    }
    

}
