package com.gousslegend.deepov.pieces;

import java.util.List;
import java.util.Vector;

import com.gousslegend.deepov.Board;
import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.Position;

public class Rook extends Piece
{
    public Rook(Position position, Board board, Color color)
    {
	super(position, board, color);
    }

    @Override
    public List<Move> getLegalMoves()
    {
	List<Move> moves = new Vector<>();
	int i = 1;

	Move possibleMove = null;
	Position destination = null;
	
	i = 1;
	destination = myPosition.deltaX(i);
	while (destination.getX() <= Board.BOARD_SIZE)
	{
	    possibleMove = new Move(myPosition, destination);
	    
	    if (myBoard.isPositionFree(destination))
	    {
		moves.add(possibleMove);
	    }
	    else
	    {
		Piece piece = myBoard.getPiece(destination);
		// look for capture
		if (areColorDifferent(piece))
		{
		    possibleMove.setCapturedPiece(piece);
		    moves.add(possibleMove);
		}
		break;
	    }
	    
	    
	    i++;
	    destination = myPosition.deltaX(i);
	}

	i = -1;
	destination = myPosition.deltaX(i);
	while (destination.getX() > 0)
	{
	    possibleMove = new Move(myPosition, destination);

	    if (myBoard.isPositionFree(destination))
	    {
		moves.add(possibleMove);
	    }
	    else
	    {
		Piece piece = myBoard.getPiece(destination);
		// look for capture
		if (areColorDifferent(piece))
		{
		    possibleMove.setCapturedPiece(piece);
		    moves.add(possibleMove);
		}
		break;
	    }
	    
	    i++;
	    destination = myPosition.deltaX(i);
	}

	i = -1;
	destination = myPosition.deltaY(i);
	while (destination.getY() > 0)
	{
	    possibleMove = new Move(myPosition, destination);

	    if (myBoard.isPositionFree(destination))
	    {
		moves.add(possibleMove);
	    }
	    else
	    {
		Piece piece = myBoard.getPiece(destination);
		// look for capture
		if (areColorDifferent(piece))
		{
		    possibleMove.setCapturedPiece(piece);
		    moves.add(possibleMove);
		}
		break;
	    }
	    
	    i++;
	    destination = myPosition.deltaY(i);
	}

	i = 1;
	destination = myPosition.deltaY(i);
	while (destination.getY() <= Board.BOARD_SIZE)
	{
	    possibleMove = new Move(myPosition, destination);

	    if (myBoard.isPositionFree(destination))
	    {
		moves.add(possibleMove);
	    }
	    else
	    {
		Piece piece = myBoard.getPiece(destination);
		// look for capture
		if (areColorDifferent(piece))
		{
		    possibleMove.setCapturedPiece(piece);
		    moves.add(possibleMove);
		}
		break;
	    }
	    
	    i++;
	    destination = myPosition.deltaY(i);
	}

	return moves;
    }
}
