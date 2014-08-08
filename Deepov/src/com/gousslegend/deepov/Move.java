package com.gousslegend.deepov;

import com.gousslegend.deepov.pieces.Piece;

public class Move
{
	private Position myOrigin;
	private Position myDestination;
	private Piece myCapturedPiece;
	private boolean myIsPromotion;
	private Piece promotedPiece;
	private boolean myIsCastling;

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
		String castling = "";
		if(isCastling()) castling = " CASTLING";
		
		String capture = "";
		if(myCapturedPiece != null) capture = " Capture: " + myCapturedPiece;
		
		return "Move [org=" + myOrigin + ", dest="
				+ myDestination + castling + capture + "]" + "\n";
	}

	public String toShortString()
	{
		return myOrigin.toShortString() +  myDestination.toShortString();
	}
	
	public boolean isPromotion()
	{
		return myIsPromotion;
	}

	public void setIsPromotion(boolean isPromotion)
	{
		myIsPromotion = isPromotion;
	}

	public boolean isCastling()
	{
		return myIsCastling;
	}

	public void setisCastling(boolean isCastling)
	{
		myIsCastling = isCastling;
	}

	public Piece getPromotedPiece()
	{
		return promotedPiece;
	}

	public void setPromotedPiece(Piece promotedPiece)
	{
		this.promotedPiece = promotedPiece;
	}
}