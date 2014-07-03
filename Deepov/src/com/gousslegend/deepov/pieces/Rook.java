package com.gousslegend.deepov.pieces;

import java.util.List;
import java.util.Vector;

import com.gousslegend.deepov.Board;
import com.gousslegend.deepov.Move;

public class Rook extends Piece
{

    public Rook(Position position, Board board, boolean isBlack)
    {
	super(position, board, isBlack);
	// TODO Auto-generated constructor stub
    }

    @Override
    public List<Move> getLegalMoves()
    {
	List<Move> moves = new Vector<>();
	int x = myPosition.getX();
	int y = myPosition.getY();
	
	
	
	return moves;
    }

}
