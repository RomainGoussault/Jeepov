package com.gousslegend.deepov.pieces;

import java.util.List;
import java.util.Vector;

import com.gousslegend.deepov.Board;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.Position;

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
	int i = 1;

	Move possibleMove = null;
	while (myBoard.isPositionFree(myPosition.deltaX(i)))
	{
	    possibleMove = new Move(myPosition, myPosition.deltaX(i));
	    moves.add(possibleMove);
	    i++;
	}
	
	i = -1;
	while (myBoard.isPositionFree(myPosition.deltaX(i)))
	{
	    possibleMove = new Move(myPosition, myPosition.deltaX(i));
	    moves.add(possibleMove);
	    i--;
	}
	
	i = 1;
	while (myBoard.isPositionFree(myPosition.deltaY(i)))
	{
	    possibleMove = new Move(myPosition, myPosition.deltaY(i));
	    moves.add(possibleMove);
	    i++;
	}
	
	i = -1;
	while (myBoard.isPositionFree(myPosition.deltaY(i)))
	{
	    possibleMove = new Move(myPosition, myPosition.deltaY(i));
	    moves.add(possibleMove);
	    i--;
	}
	
	return moves;
    }

}
