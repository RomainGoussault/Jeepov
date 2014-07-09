package com.gousslegend.deepov.pieces;

import java.util.ArrayList;
import java.util.List;

import com.gousslegend.deepov.Board;
import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;

public class Pawn extends Piece
{

	public Pawn(Position position, Board board, Color color)
	{
		super(position, board, color);
		// TODO Auto-generated constructor stub
	}
	
	public Pawn()
	{
		super();
	}

	@Override
	public MoveList getPseudoLegalMoves()
	{
		MoveList pseudoLegalMoves = new MoveList(myBoard);

		int direction = getDirection();
		Position destination = null;
		
		//no need to check if we are on the edge on the board:
		//it should not happen because it will be promoted before
		destination = myPosition.deltaX(direction); 
		if (myBoard.isPositionFree(destination))
		{
			pseudoLegalMoves.add(new Move(myPosition, destination));
		}
		
		//check to the right and the left for capture
		Piece possibleCapture = null;
		
		destination = myPosition.deltaXY(direction, 1);
		possibleCapture = myBoard.getPiece(destination);
		if (possibleCapture != null && areColorDifferent(possibleCapture))
		{
			pseudoLegalMoves.add(new Move(myPosition, destination));
		}
		
		destination = myPosition.deltaXY(direction,-1);
		possibleCapture = myBoard.getPiece(destination);
		if (possibleCapture != null && areColorDifferent(possibleCapture))
		{
			pseudoLegalMoves.add(new Move(myPosition, destination));
		}
		
		//Todo: Promotion, en passant, 2 delta at start

		return pseudoLegalMoves;
	}

	@Override
	public List<Position> getAttackingSquares()
	{
		// TODO Auto-generated method stub
		return new ArrayList<Position>();
	}

	public int getDirection()
	{
		if (myColor == Color.WHITE)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
}
