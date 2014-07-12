package com.gousslegend.deepov.pieces
import static org.junit.Assert.assertEquals
import spock.lang.*

import com.gousslegend.deepov.Board
import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Position

class WhenTestingRookMovement extends spock.lang.Specification
{

	@Shared
	def board
	@Shared
	def rook


	def setupSpec()
	{
		board = new Board()
		rook = new Rook()
	}
	
	def cleanup()
	{
		board = new Board()
	}

	def "Testing rook alone on board"()
	{		
		Rook rook = new Rook(position, board, Color.BLACK);
		board.addPiece(rook);
		
		expect:
		rook.getLegalMoves().size() == 14

		where:
		position << Position.getAllPositionOnBoard()
	}

	def "Test blocked Rook with ennemy pieces"()
	{
		given:"A rook with 2 ennemies pawn"
		Position position = new Position(0, 0);

		Rook rook = new Rook(position, board, Color.BLACK);
		Pawn pawn1 = new Pawn(new Position(1, 0), board, Color.WHITE);
		Pawn pawn2 = new Pawn(new Position(0, 1), board, Color.WHITE);

		when:
		board.addPiece(rook);
		board.addPiece(pawn1);
		board.addPiece(pawn2);

		then:
		rook.getLegalMoves().size() == 2;
	}
	
	def "Test blocked Rook with same color piece"()
	{
		given:
		Position position = new Position(0, 0);

		Rook rook = new Rook(position, board, Color.BLACK);
		Pawn pawn1 = new Pawn(new Position(1, 0), board, Color.BLACK);
		Pawn pawn2 = new Pawn(new Position(0, 1), board, Color.BLACK);

		when:
		board.addPiece(rook);
		board.addPiece(pawn1);
		board.addPiece(pawn2);

		then:
		rook.getLegalMoves().size() == 0;
	}
	
	def "Test LegalMoves on pinned rook"()
	{
		given:
		Rook blackRook = new Rook(blackRookPosition, board, Color.BLACK);
		Rook whiteRook = new Rook(whiteRookPosition, board, Color.WHITE);
		King blackKing = new King(blackKingPosition, board, Color.BLACK);
		
		board.addPiece(blackRook)
		board.addPiece(whiteRook)
		board.addPiece(blackKing)

		expect:
		blackRook.getLegalMoves().size() == moveSize;

		where:
		blackRookPosition 	| whiteRookPosition   | blackKingPosition | moveSize
		new Position(1, 0) |  new Position(2, 0) | new Position(0, 0) | 1
		new Position(1, 0) |  new Position(3, 0) | new Position(0, 0) | 2
		new Position(1, 0) |  new Position(3, 3) | new Position(0, 0) | 13
		new Position(1, 3) |  new Position(0, 3) | new Position(7, 3) | 6
	}
	
	def "Test rook only move when king is in check"()
	{
		given:
		Rook blackRook = new Rook(blackRookPosition, board, Color.BLACK);
		Rook whiteRook = new Rook(whiteRookPosition, board, Color.WHITE);
		King blackKing = new King(blackKingPosition, board, Color.BLACK);
		
		board.addPiece(blackRook)
		board.addPiece(whiteRook)
		board.addPiece(blackKing)

		expect:
		blackRook.getLegalMoves().size() == moveSize;

		where:
		blackRookPosition 	| whiteRookPosition   | blackKingPosition | moveSize
		new Position(3, 3) |  new Position(7, 0) | new Position(0, 0) | 1
		new Position(3, 3) |  new Position(7, 2) | new Position(2, 2) | 1
	}
}
