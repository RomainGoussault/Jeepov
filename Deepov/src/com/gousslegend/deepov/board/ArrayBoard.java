package com.gousslegend.deepov.board;

import java.util.ArrayList;
import java.util.List;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.pieces.King;
import com.gousslegend.deepov.pieces.Pawn;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.deepov.pieces.Rook;
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

	public Piece getKing(Color color)
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				Piece piece = myPieces[i][j];
				if (piece != null && piece instanceof King && piece.getColor() == color)
				{
					return piece;
				}
			}
		}

		// There should always be a king for each color on the board
		return null;
	}

	public Position getKingPosition(Color color)
	{
		King king = (King) getKing(color);
		
		if(king == null)
		{
			return null;
		}
		else
		{
			return king.getPosition();
		}
	}

	protected List<Piece> getPieces(Color color)
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
	
	public void undoMove(Move move)
	{
		Position origin = move.getOrigin();
		Position destination = move.getDestination();
		boolean isCaptureMove = move.getCapturedPiece() != null;
		Piece pieceToMove = getPiece(destination);
		
		if(move.isCastling())
		{
			King king = (King) pieceToMove;
			//move back the king
			executeMove(king, origin);
			king.decrementMoveCounter();
			
			//move back the rook
			boolean isKingSideCastling = move.getDestination().getX() == 6;
			Position rookOrigin, rookDestination;

			if(isKingSideCastling)
			{
				rookOrigin = new Position(7, destination.getY());
				rookDestination = new Position(5, destination.getY());
			}
			else
			{
				rookOrigin = new Position(0, destination.getY());
				rookDestination = new Position(3, destination.getY());
			}

			Rook rook = (Rook) getPiece(rookDestination);
			executeMove(rook, rookOrigin);
			rook.decrementMoveCounter();
		}
		else if(move.isPromotion())
		{
			//remove the promoted piece
			removePiece(move.getPromotedPiece());
			
			//add the pawn back
			addPiece(new Pawn(origin, this, move.getPromotedPiece().getColor())); //queen get board? save pawn
			
			if(isCaptureMove)
			{
				//add the captured piece
				addPiece(move.getCapturedPiece());
			}
		}
		else if(move.isEnPassant())
		{
			//add the taken pawn
			addPiece(move.getCapturedPiece());
			executeMove(pieceToMove, origin);
			pieceToMove.decrementMoveCounter();
		}
		else
		{
			executeMove(pieceToMove, origin);
			pieceToMove.decrementMoveCounter();

			if(isCaptureMove)
			{
				//add the captured piece
				addPiece(move.getCapturedPiece());
			}
		}
		
		myMoves.remove(getLastMove());
		colorToPlay = colorToPlay.getOppositeColor();
	}
	
	public void executeMove(Move move)
	{
		Position origin = move.getOrigin();
		Position destination = move.getDestination();
		boolean isCaptureMove = move.getCapturedPiece() != null;
		Piece pieceToMove = getPiece(origin);
		
		if(move.isCastling())
		{
			//Move the king
			King king = (King) pieceToMove;
			king.incrementMoveCounter();
			executeMove(king, destination);
			
			//Move the rook
			boolean isKingSideCastling = move.getDestination().getX() == 6;
			Position rookOrigin, rookDestination;

			if(isKingSideCastling)
			{
				rookOrigin = new Position(7, destination.getY());
				rookDestination = new Position(5, destination.getY());
			}
			else
			{
				rookOrigin = new Position(0, destination.getY());
				rookDestination = new Position(3, destination.getY());
			}

			Rook rook = (Rook) getPiece(rookOrigin);
			executeMove(rook, rookDestination);
			rook.incrementMoveCounter();
		}
		else if(move.isPromotion())
		{
			if(isCaptureMove)
			{
				//remove the piece
				removePiece(move.getCapturedPiece());
			}
			
			//remove the pawn first
			removePiece(pieceToMove);
			
			//add the promoted piece
			addPiece(move.getPromotedPiece());
		}
		else if(move.isEnPassant())
		{
			removePiece(move.getCapturedPiece());
			pieceToMove.incrementMoveCounter();
			executeMove(pieceToMove, destination);
		}
		else
		{
			if(isCaptureMove)
			{
				//remove the piece
				removePiece(move.getCapturedPiece());
			}

			pieceToMove.incrementMoveCounter();
			executeMove(pieceToMove, destination);
		}
		
		myMoves.add(move);
		colorToPlay = colorToPlay.getOppositeColor();
	}
	
	private void executeMove(Piece piece, Position destination)
	{
		removePiece(piece);
		piece.setPosition(destination);
		addPiece(piece);
	}
	
	public void removePiece(Piece piece)
	{
		removePiece(piece.getPosition());
	}
}