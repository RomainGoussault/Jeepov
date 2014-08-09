package com.gousslegend.deepov.board;

import java.util.List;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.MoveList;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.pieces.Bishop;
import com.gousslegend.deepov.pieces.King;
import com.gousslegend.deepov.pieces.Knight;
import com.gousslegend.deepov.pieces.Pawn;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.deepov.pieces.Queen;
import com.gousslegend.deepov.pieces.Rook;

public abstract class Board
{
	public static final int BOARD_SIZE = 7;

	/** Move taken in this game so far */
	protected List<Move> myMoves;

	protected Color colorToPlay = Color.WHITE;

	public Board()
	{
		super();
	}

	public abstract void addPiece(Piece piece);

	public abstract Piece getPiece(Position position);

	public abstract boolean isPositionFree(Position position);

	protected abstract List<Piece> getPieces(Color color);

	protected abstract List<Piece> getPieces();

	public abstract Piece getKing(Color color);

	public abstract Position getKingPosition(Color color);

	public abstract void removePiece(Position position);

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
			Piece promotedPiece = move.getPromotedPiece();
			removePiece(promotedPiece);
			
			//add the pawn back
			addPiece(new Pawn(origin, this, promotedPiece.getColor())); //queen get board? save pawn
			
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
	
	public void setupBoard()
	{
		//Add Pawns
		for(int i = 0; i<=7; i++)
		{
			addPiece(new Pawn(new Position(i,1), this, Color.WHITE));
			addPiece(new Pawn(new Position(i,6), this, Color.BLACK));
		}

		addPiece(new Rook(new Position(0,0), this, Color.WHITE));
		addPiece(new Rook(new Position(7,0), this, Color.WHITE));
		addPiece(new Rook(new Position(0,7), this, Color.BLACK));
		addPiece(new Rook(new Position(7,7), this, Color.BLACK));

		addPiece(new Knight(new Position(1,0), this, Color.WHITE));
		addPiece(new Knight(new Position(6,0), this, Color.WHITE));
		addPiece(new Knight(new Position(1,7), this, Color.BLACK));
		addPiece(new Knight(new Position(6,7), this, Color.BLACK));

		addPiece(new Bishop(new Position(2,0), this, Color.WHITE));
		addPiece(new Bishop(new Position(5,0), this, Color.WHITE));
		addPiece(new Bishop(new Position(2,7), this, Color.BLACK));
		addPiece(new Bishop(new Position(5,7), this, Color.BLACK));

		addPiece(new Queen(new Position(3,0), this, Color.WHITE));
		addPiece(new Queen(new Position(3,7), this, Color.BLACK));
		addPiece(new King(new Position(4,0), this, Color.WHITE));
		addPiece(new King(new Position(4,7), this, Color.BLACK));
	}

	public String toString()
	{
		String board = "";
		Piece piece = null;

		for(int i = 7; i >= 0 ; i--)
		{
			board += i + "|  ";

			for(int j = 0; j < 8 ; j++)
			{
				piece = getPiece(new Position(j,i));
				if(piece == null)
				{
					board += "* ";
				}
				else
				{
					board += piece.getChar() + " ";
				}				
			}

			board += " \n";
		}

		board +="   ________________\n";
		board +="    0 1 2 3 4 5 6 7\n";

		return board;
	}

	public boolean isPositionOnBoard(Position position)
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

		return true;
	}

	public boolean isCheck(Color color)
	{
		Position kingPosition = getKingPosition(color);

		List<Piece> ennemyPieces = getEnnemiesPieces(color);

		for (Piece ennemyPiece : ennemyPieces)
		{
			if (isPieceAttacking(ennemyPiece, kingPosition))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isPositionAttacked(Position position, Color color)
	{
		List<Piece> ennemyPieces = getEnnemiesPieces(color);

		for (Piece ennemyPiece : ennemyPieces)
		{
			if (isPieceAttacking(ennemyPiece, position))
			{
				return true;
			}
		}
		return false;
	}

	public List<Piece> getEnnemiesPieces(Color color)
	{
		return getPieces(color.getOppositeColor());
	}

	public MoveList generateMoves()
	{
		return generateMoves(colorToPlay);
	}


	public MoveList generateMoves(Color color)
	{
		List<Piece> pieces = getPieces(color);
		MoveList moveList = new MoveList(this);

		for(Piece piece : pieces)
		{
			moveList.append(piece.getLegalMoves());
		}

		return moveList;
	}

	public List<Move> getMoves()
	{
		return myMoves;
	}

	/**
	 * This method return the number of active pieces on the board
	 * @return
	 */
	public int getNumberOfPieces()
	{
		return getPieces().size();
	}

	public Move getLastMove()
	{
		if(myMoves.size() > 0)
		{
			return myMoves.get(myMoves.size()-1);
		}
		else
		{
			return null;
		}
	}

	private boolean isPieceAttacking(Piece ennemyPiece, Position positionAttackec)
	{
		List<Position> attackingSquares = ennemyPiece.getAttackingSquares();

		return attackingSquares.contains(positionAttackec);
	}

	public Color getColorToPlay()
	{
		return colorToPlay;
	}

	public void setColorToPlay(Color colorToPlay)
	{
		this.colorToPlay = colorToPlay;
	}

	public void checkForNoPawnOnBackRank()
	{
		List<Piece> pieces = getPieces();

		for(Piece piece : pieces)
		{
			if(piece instanceof Pawn)
			{
				if(piece.getPosition().getY() == 0 | piece.getPosition().getY() == 7)
				{
					System.out.println("ERROR NO PAWN ON BACK RANKS");
				}
			}
		}
	}

	public void addPieces(List<Piece> pieces)
	{
		for (Piece piece : pieces)
		{
			piece.setBoard(this);
			addPiece(piece);
		}
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