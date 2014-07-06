package com.gousslegend.deepov.pieces;

import java.util.List;

import com.gousslegend.deepov.Board;
import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;

public class King extends Piece
{
    public King()
    {
	super();
    }
    
    public King(Position position, Board board, Color color)
    {
	super(position, board, color);
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
	public MoveList getLegalMoves()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
