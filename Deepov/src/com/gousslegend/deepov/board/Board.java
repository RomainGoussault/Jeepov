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
		removePiece(origin);
		pieceToMove.setPosition(destination);
		pieceToMove.incrementMoveCounter();

		if (isCaptureMove)
		{
			if(getPiece(move.getCapturedPiece().getPosition()) == null)
			{
				System.out.println("ERROR: Removing null piece for Move " + move);
			}
			removePiece(move.getCapturedPiece().getPosition());
		}

		if(getPiece(destination) != null)
		{
			System.out.println("ERROR: Destination not null for Move " + move);
		}
		addPiece(pieceToMove);

		if(move.isCastling())
		{
			King king = (King) pieceToMove;

			//move the rook
			boolean isKingSideCastling = move.getDestination().getX() == 6;
			Position rookOrigin;
			Position rookDestination;

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
			removePiece(rookOrigin);
			rook.setPosition(rookDestination);
			rook.incrementMoveCounter();
			addPiece(rook);
		}

		if(move.isPromotion())
		{
			//remove the pawn
			removePiece(destination);
			//add a queen
			addPiece(new Queen(destination, this, pieceToMove.getColor()));
		}

		myMoves.add(move);
		colorToPlay = colorToPlay.getOppositeColor();
	}

	public void undoMove(Move move)
	{
		Position origin = move.getOrigin();
		Position destination = move.getDestination();
		boolean isCaptureMove = move.getCapturedPiece() != null;

		Piece pieceMoved = getPiece(destination);
		pieceMoved.decrementMoveCounter();

		removePiece(destination);
		if (isCaptureMove)
		{
			Piece pieceCaptured = move.getCapturedPiece();
			if(pieceCaptured == null)
			{
				System.out.println("ERROR: adding null piece for Move " + move);
			}
			addPiece(pieceCaptured);
		}
		
		pieceMoved.setPosition(origin);
		addPiece(pieceMoved);
		
		if(move.isCastling())
		{
			King king = (King) pieceMoved;

			//move back the rook
			boolean isKingSideCastling = move.getDestination().getX() == 6;
			Position rookOrigin;
			Position rookDestination;

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
			removePiece(rookDestination);
			rook.setPosition(rookOrigin);
			rook.decrementMoveCounter();
			addPiece(rook);
		}

		myMoves.remove(getLastMove());
		colorToPlay = colorToPlay.getOppositeColor();

		//TODO undo promotion
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
}