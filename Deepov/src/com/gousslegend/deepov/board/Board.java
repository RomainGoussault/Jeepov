package com.gousslegend.deepov.board;

import java.util.ArrayList;
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

	public abstract List<Piece> getPieces(Color color);

	protected abstract List<Piece> getPieces();

	public abstract King getKing(Color color);

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
		//updatePinnedPieces(colorToPlay);
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
			//addPiece(new Pawn(origin, this, promotedPiece.getColor())); 
			addPiece(move.getPromotedPawn()); //TODO understand why previous line does not work with Kiwipete perft(4)
			
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
		//updatePinnedPieces(colorToPlay);

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

	public MoveList getLegalMoves()
	{
		return getLegalMoves(colorToPlay);
	}


	public MoveList getLegalMoves(Color color)
	{
		List<Piece> pieces = getPieces(color);
		MoveList moveList = new MoveList(this);
		
		updatePinnedPieces();

		for(Piece piece : pieces)
		{
			moveList.append(piece.getLegalMoves());
		}

		return moveList;
	}

	public List<Move> getMovesPlayed()
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

	public Color getColorToPlay()
	{
		return colorToPlay;
	}

	public void setColorToPlay(Color colorToPlay)
	{
		this.colorToPlay = colorToPlay;
	}

	public boolean isCheckmate()
	{
		return isCheckmate(colorToPlay);
	}
	
	public boolean isCheckmate(Color color)
	{
		MoveList moves = getLegalMoves(color);
		return  moves.size() == 0;
	}
	
	public void addPieces(List<Piece> pieces)
	{
		for (Piece piece : pieces)
		{
			piece.setBoard(this);
			addPiece(piece);
		}
	}
	
	public void removePiece(Piece piece)
	{
		removePiece(piece.getPosition());
	}
	
	public List<Piece> getPinnedPieces(Color color)
	{
		King king = getKing(color);
		List<Piece> pinnedPieces = new ArrayList<>();
		
		if(king == null) return pinnedPieces;
		List<Piece> possiblePinnedPieces = null;
		List<Piece> rookSlidingPieces = getRookSlidingPieces(color.getOppositeColor());
		List<Piece> bishopSlidingPieces = getBishopSlidingPieces(color.getOppositeColor());
		byte kingX = king.getPosition().getX();
		byte kingY = king.getPosition().getY();
		Piece possiblePinnedPiece = null;
		
		for(Piece piece : rookSlidingPieces)
		{
			//is the king on the same row/column?
			if(piece.getPosition().getX() == kingX )
			{
				possiblePinnedPieces = getPiecesInBetweenY(piece, king);
				if(possiblePinnedPieces.size() == 1)
				{
					possiblePinnedPiece = possiblePinnedPieces.get(0);
					if(possiblePinnedPiece.getColor().equals(color))
					{
						pinnedPieces.add(possiblePinnedPiece);
					}
				}
			}
			else if(piece.getPosition().getY() == kingY )
			{
				possiblePinnedPieces = getPiecesInBetweenX(piece, king);
				if(possiblePinnedPieces.size() == 1)
				{
					possiblePinnedPiece = possiblePinnedPieces.get(0);
					if(possiblePinnedPiece.getColor().equals(color))
					{
						pinnedPieces.add(possiblePinnedPiece);
					}
				}
			}
		}
		
		for(Piece piece : bishopSlidingPieces)
		{
			List<Position> attackedTransPositions = piece.getAttackingSquaresTrans();
			if(attackedTransPositions.contains(king.getPosition()))
			{
				possiblePinnedPieces = getPiecesInBetweenXY(piece, king);
				if(possiblePinnedPieces.size() == 1)
				{
					possiblePinnedPiece = possiblePinnedPieces.get(0);
					if(possiblePinnedPiece.getColor().equals(color))
					{
						pinnedPieces.add(possiblePinnedPiece);
					}
				}
			}
		}
		
		return pinnedPieces;
	}
	
	public void updatePinnedPieces()
	{
		King king = getKing(colorToPlay);
		
		List<Piece> pieces = getPieces(colorToPlay);
		for(Piece piece : pieces)
		{
			piece.setPinned(false);
		}
		
		if(king == null) return;
		List<Piece> possiblePinnedPieces = null;
		List<Piece> rookSlidingPieces = getRookSlidingPieces(colorToPlay.getOppositeColor());
		List<Piece> bishopSlidingPieces = getBishopSlidingPieces(colorToPlay.getOppositeColor());
		byte kingX = king.getPosition().getX();
		byte kingY = king.getPosition().getY();
		Piece possiblePinnedPiece = null;
		
		for(Piece piece : rookSlidingPieces)
		{
			//is the king on the same row/column?
			if(piece.getPosition().getX() == kingX )
			{
				possiblePinnedPieces = getPiecesInBetweenY(piece, king);
				if(possiblePinnedPieces.size() == 1)
				{
					possiblePinnedPiece = possiblePinnedPieces.get(0);
					if(possiblePinnedPiece.getColor().equals(colorToPlay))
					{
						possiblePinnedPiece.setPinned(true);
					}
				}
			}
			else if(piece.getPosition().getY() == kingY )
			{
				possiblePinnedPieces = getPiecesInBetweenX(piece, king);
				if(possiblePinnedPieces.size() == 1)
				{
					possiblePinnedPiece = possiblePinnedPieces.get(0);
					if(possiblePinnedPiece.getColor().equals(colorToPlay))
					{
						possiblePinnedPiece.setPinned(true);
					}
				}
			}
		}
		
		for(Piece piece : bishopSlidingPieces)
		{
			List<Position> attackedTransPositions = piece.getAttackingSquaresTrans();
			if(attackedTransPositions.contains(king.getPosition()))
			{
				possiblePinnedPieces = getPiecesInBetweenXY(piece, king);
				if(possiblePinnedPieces.size() == 1)
				{
					possiblePinnedPiece = possiblePinnedPieces.get(0);
					if(possiblePinnedPiece.getColor().equals(colorToPlay))
					{
						possiblePinnedPiece.setPinned(true);
					}
				}
			}
		}
		
	}
	
	private List<Piece> getPiecesInBetweenXY(Piece piece, King king)
	{
		List<Piece> pieces = new ArrayList<>();
		Piece possiblePiece = null;
		
		byte x = piece.getPosition().getX();
		byte y = piece.getPosition().getY();
		byte xKing = king.getPosition().getX();
		byte yKing = king.getPosition().getY();
		
		//Delta to go from piece to king
		int deltaX = (x < xKing ? 1 : -1);
		int deltaY = (y < yKing ? 1 : -1);
		
		Position position = null;
		position = piece.getPosition().deltaXY(deltaX, deltaY);
		while(position.getX() != xKing)
		{
			possiblePiece = getPiece(position);
			if(possiblePiece != null)
			{
				pieces.add(possiblePiece);
			}
			position = position.deltaXY(deltaX, deltaY);
		}
		
		return pieces;
	}

	private List<Piece> getPiecesInBetweenX(
			Piece piece, King king)
	{
		List<Piece> pieces = new ArrayList<>();
		Position position = null;
		Piece possiblePiece = null;
		
		byte x = piece.getPosition().getX();
		byte xKing = king.getPosition().getX();
		
		int deltaX = (x < xKing ? 1 : -1);
		
		position = piece.getPosition().deltaX(deltaX);
		while(position.getX() != xKing)
		{
			possiblePiece = getPiece(position);
			if(possiblePiece != null)
			{
				pieces.add(possiblePiece);
			}
			position = position.deltaX(deltaX);
		}
		
		return pieces;
	}
	
	private List<Piece> getPiecesInBetweenY(
			Piece piece, King king)
	{
		List<Piece> pieces = new ArrayList<>();
		Position position = null;
		Piece possiblePiece = null;
		
		byte y = piece.getPosition().getY();
		byte yKing = king.getPosition().getY();
		
		int deltaY = (y < yKing ? 1 : -1);
		
		position = piece.getPosition().deltaY(deltaY);
		while(position.getY() != yKing)
		{
			possiblePiece = getPiece(position);
			if(possiblePiece != null)
			{
				pieces.add(possiblePiece);
			}
			position = position.deltaY(deltaY);
		}
		
		return pieces;
	}
	private List<Piece> getBishopSlidingPieces(Color color)
	{
		 List<Piece> rookSlidlingPieces = new ArrayList<>();
		 List<Piece> pieces = getPieces(color);
		 
		 for(Piece piece : pieces)
		 {
			 if(piece instanceof Bishop || piece instanceof Queen)
			 {
				 rookSlidlingPieces.add(piece);
			 }
		 }
		 
		 return rookSlidlingPieces;
	}
	
	private List<Piece> getRookSlidingPieces(Color color)
	{
		 List<Piece> rookSlidlingPieces = new ArrayList<>();
		 List<Piece> pieces = getPieces(color);
		 
		 for(Piece piece : pieces)
		 {
			 if(piece instanceof Rook || piece instanceof Queen)
			 {
				 rookSlidlingPieces.add(piece);
			 }
		 }
		 
		 return rookSlidlingPieces;
	}

	private void executeMove(Piece piece, Position destination)
	{
		removePiece(piece);
		piece.setPosition(destination);
		addPiece(piece);
	}
	
	private boolean isPieceAttacking(Piece ennemyPiece, Position positionAttackec)
	{
		List<Position> attackingSquares = ennemyPiece.getAttackingSquares();

		return attackingSquares.contains(positionAttackec);
	}
}