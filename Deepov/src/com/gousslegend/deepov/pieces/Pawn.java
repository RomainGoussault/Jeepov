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

	@Override
	public MoveList getPseudoLegalMoves()
	{
		MoveList pseudoLegalMoves = new MoveList(myBoard);

		Move possibleMove = null;
		Position destination = myPosition.deltaX(getDirection());

		if (myBoard.isPositionFree(destination))
		{
			pseudoLegalMoves.add(new Move(myPosition, destination));
		}
		
		//Todo: Promotion, en passant

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
