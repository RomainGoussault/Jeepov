package com.gousslegend.deepov.pieces;

import java.util.List;

import com.gousslegend.deepov.Board;
import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;

public class Knight extends Piece
{
    public Knight(Position position, Board board, Color color)
    {
	super(position, board, color);
	// TODO Auto-generated constructor stub
    }

    @Override
    public MoveList getPseudoLegalMoves()
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Position> getAttackingSquares()
    {
	// TODO Auto-generated method stub
	return null;
    }
    
	@Override
	public String toString()
	{
		return myColor + " Knight " + myPosition;
	}
}