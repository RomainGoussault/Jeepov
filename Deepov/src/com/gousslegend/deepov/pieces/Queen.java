package com.gousslegend.deepov.pieces;

import java.util.ArrayList;
import java.util.List;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.board.Board;

public class Queen extends Piece
{
	public Queen()
	{
		super();
	}

	public Queen(Position position, Color color)
	{
		super(position, color);
	}
	
	public Queen(Position position,Board board, Color color)
	{
		super(position, board, color);
	}

	@Override
	public MoveList getPseudoLegalMoves()
	{
		MoveList pseudoLegalMoves = new MoveList(myBoard);
		MoveList rookMoves = Rook.getPseudoLegalMoves(this);
		MoveList bishopMoves = Bishop.getPseudoLegalMoves(this);

		pseudoLegalMoves.append(rookMoves);
		pseudoLegalMoves.append(bishopMoves);

		return pseudoLegalMoves;
	}

	@Override
	public List<Position> getAttackingSquares()
	{
		List<Position> attackedSquares = new ArrayList<>();
		List<Position> rookMoves = Rook.getAttackingSquares(this);
		List<Position> bishopMoves = Bishop.getAttackingSquares(this);

		attackedSquares.addAll(rookMoves);
		attackedSquares.addAll(bishopMoves);

		return attackedSquares;
	}

	public List<Position> getAttackingSquaresTrans()
	{
		return Bishop.getAttackingSquaresTrans(this);
	}
	
	@Override
	public String toString()
	{
		return myColor + " Queen " + myPosition;
	}

	@Override
	public String getChar()
	{
		return formatChar("Q");
	}
}
