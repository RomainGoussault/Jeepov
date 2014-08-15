package com.gousslegend.deepov.board;

import java.util.ArrayList;
import java.util.List;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.pieces.King;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.deepov.utils.Utils;

public class ArrayBoard extends Board
{
	private Piece[][] myPieces;

	public ArrayBoard()
	{
		myPieces = new Piece[8][8];
		myMoves = new ArrayList<>();
	}

	public ArrayBoard(String fen)
	{
		myPieces = new Piece[8][8];
		myMoves = new ArrayList<>();

		addPieces(Utils.getPiecesFromFen(fen));
		
		colorToPlay = Utils.getColorToPLay(fen);
	}

	public void addPiece(Piece piece)
	{
		piece.setBoard(this);
		Position position = piece.getPosition();
		
		if(!isPositionFree(position))
		{
			System.out.println("BIG MISTAKE ADD");
		}
		
		myPieces[position.getX()][position.getY()] = piece;
	}

	public Piece getPiece(Position position)
	{
		return myPieces[position.getX()][position.getY()];
	}

	public boolean isPositionFree(Position position)
	{
		int x = position.getX();
		int y = position.getY();

		if (x > BOARD_SIZE || y > BOARD_SIZE)
		{
			return false;
		}

		if (x < 0 || y < 0)
		{
			return false;
		}

		return getPiece(position) == null;
	}

	public King getKing(Color color)
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				Piece piece = myPieces[i][j];
				if (piece != null && piece instanceof King && piece.getColor() == color)
				{
					return (King) piece;
				}
			}
		}

		// There should always be a king for each color on the board
		return null;
	}

	public Position getKingPosition(Color color)
	{
		King king = getKing(color);
		
		if(king == null)
		{
			return null;
		}
		else
		{
			return king.getPosition();
		}
	}

	public List<Piece> getPieces(Color color)
	{
		List<Piece> pieces = new ArrayList<>();

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				Piece piece = myPieces[i][j];
				if (piece != null && piece.getColor() == color)
				{
					pieces.add(piece);
				}
			}
		}

		return pieces;
	}

	protected List<Piece> getPieces()
	{
		List<Piece> pieces = new ArrayList<>();

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				Piece piece = myPieces[i][j];
				if(piece != null)
				{
					pieces.add(piece);
				}
			}
		}

		return pieces;
	}

	@Override
	public void removePiece(Position position)
	{
		if(isPositionFree(position))
		{
			System.out.println("BIG MISTAKE REMOVE");
		}
		myPieces[position.getX()][position.getY()] = null;
	}
}