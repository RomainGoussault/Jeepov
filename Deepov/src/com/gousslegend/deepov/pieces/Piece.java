package com.gousslegend.deepov.pieces;

import java.util.Iterator;
import java.util.List;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.board.ArrayBoard;
import com.gousslegend.deepov.board.Board;

public abstract class Piece
{
	protected Position myPosition;
	protected Board myBoard;
	protected Color myColor;
	protected int moveCounter = 0;
	protected boolean isPinned = false;

	public boolean isPinned()
	{
		return isPinned;
	}

	public void setPinned(boolean isPinned)
	{
		this.isPinned = isPinned;
	}

	/**
	 * Default constructor used for testing
	 */
	public Piece()
	{
		myPosition = new Position();
		myBoard = new ArrayBoard();
		myColor = Color.BLACK;
	}

	public Piece(Color color)
	{
		myPosition = new Position();
		myBoard = new ArrayBoard();
		myColor = color;
	}

	public Piece(Position position, Board board, Color color)
	{
		myPosition = position;
		myBoard = board;
		myColor = color;
	}

	public Piece(Position position, Color color)
	{
		myPosition = position;
		myBoard = null;
		myColor = color;
	}

	/**
	 * This method get all the pseudo legal (possibly leaving the king in check)
	 * moves for the piece
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
		boolean isCheck = myBoard.isCheck(myColor);
		//List<Piece> pinnedPieces = myBoard.getPinnedPieces(myColor);
		//isPinned = pinnedPieces.contains(this) ? true : false;
		
		Iterator<Move> moveIterator = legalMoves.getList().iterator();
		while (moveIterator.hasNext())
		{
			Move move = moveIterator.next();

			if (isCheck || isPinned)
			{
				myBoard.executeMove(move);
				if (myBoard.isCheck(myColor))
				{
					myBoard.undoMove(move);
					moveIterator.remove();
				}
				else
				{
					myBoard.undoMove(move);
				}
			}
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

	public Color getColor()
	{
		return myColor;
	}

	public void setColor(Color myColor)
	{
		this.myColor = myColor;
	}

	public void incrementMoveCounter()
	{
		moveCounter++;
	}

	public void decrementMoveCounter()
	{
		moveCounter--;
	}

	public boolean hasMoved()
	{
		return moveCounter != 0;
	}

	@Override
	public String toString()
	{
		return myColor + " Piece " + myPosition;
	}

	public abstract String getChar();

	public String formatChar(String string)
	{
		if (myColor == Color.WHITE)
		{
			return string.toUpperCase();
		}
		else
		{
			return string.toLowerCase();
		}
	}

	public List<Position> getAttackingSquaresTrans()
	{
		return null;
	}
}