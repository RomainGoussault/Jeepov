package com.gousslegend.deepov.pieces;

import java.util.Iterator;
import java.util.List;

import com.gousslegend.deepov.Board;
import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;

public abstract class Piece
{
	protected Position myPosition;
	protected int myValue;
	protected Board myBoard;
	protected Color myColor;

	/**
	 * Default constructor used for testing
	 */
	public Piece()
	{
		myPosition = new Position();
		myBoard = new Board();
		myColor = Color.BLACK;
	}

	public Piece(Color color)
	{
		myPosition = new Position();
		myBoard = new Board();
		myColor = color;
	}

	public Piece(Position position, Board board, Color color)
	{
		myPosition = position;
		myBoard = board;
		myColor = color;
	}

	/**
	 * This method get all the pseudo legal (possibly leaving the king in check) moves for the piece
	 * 
	 * @return
	 */
	public abstract MoveList getPseudoLegalMoves();


	/**
	 * This method get all the legal moves for the piece
	 * 
	 * @return
	 */
	public MoveList getLegalMoves()
	{
		MoveList legalMoves = getPseudoLegalMoves();
		legalMoves.setBoard(myBoard);
		
		Iterator<Move> moveIterator = legalMoves.getMyList().iterator();
		while (moveIterator.hasNext())
		{
			Move move = moveIterator.next();
			myBoard.executeMove(move);
			if (legalMoves.getBoard().isCheck(myColor))
			{
				moveIterator.remove();
			}
			myBoard.undo(move);
		}

		return legalMoves;
	}
	
	
	/**
	 * This method returns all the positions that the piece is attacking
	 * 
	 * @return
	 */
	public abstract List<Position> getAttackingSquares();

	public Position getPosition()
	{
		return myPosition;
	}

	public boolean areColorDifferent(Piece piece)
	{
		return myColor != piece.getColor();
	}

	public boolean areColorDifferent(Position position)
	{
		Piece piece = myBoard.getPiece(position);
		return myColor != piece.getColor();
	}

	public void setPosition(Position myPosition)
	{
		this.myPosition = myPosition;
	}

	public Board getBoard()
	{
		return myBoard;
	}

	public void setBoard(Board myBoard)
	{
		this.myBoard = myBoard;
	}

	public int getValue()
	{
		return myValue;
	}

	public Color getColor()
	{
		return myColor;
	}

	public void setColor(Color myColor)
	{
		this.myColor = myColor;
	}

	public void setValue(int myValue)
	{
		this.myValue = myValue;
	}
}
