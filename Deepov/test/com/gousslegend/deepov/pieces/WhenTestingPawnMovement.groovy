package com.gousslegend.deepov.pieces
import static org.junit.Assert.assertEquals
import spock.lang.*

import com.gousslegend.deepov.Board
import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Position

class WhenTestingPawnMovement extends spock.lang.Specification
{

	@Shared
	def board
	@Shared
	def pawn


	def setupSpec()
	{
		board = new Board()
		pawn = new Pawn()
	}
	
	def cleanup()
	{
		board = new Board()
	}

	def "Testing pawn alone on board"()
	{	
		given:	
		Pawn pawn = new Pawn(new Position(5,5), board, Color.BLACK);
		board.addPiece(pawn);
		
		expect:
		pawn.getLegalMoves().size() == 1
	}

	def "Test LegalMoves on Pawn with Ennemy Rook"()
	{
		given:
		Pawn whitePawn = new Pawn(whitePawnPosition, board, Color.WHITE)
		Rook blackRook = new Rook(blackRookPosition, board, Color.BLACK)
		board.addPiece(blackRook)
		board.addPiece(whitePawn)

		expect:
		whitePawn.getLegalMoves().size() == moveSize;

		where:
		whitePawnPosition	|  blackRookPosition|  moveSize
		new Position(1, 1) |  new Position(0, 0) | 2
		new Position(2, 2) |  new Position(0, 0) | 1
		new Position(4, 0) |  new Position(0, 0) | 1
		new Position(1, 1) |  new Position(2, 2) | 3
		new Position(1, 1) |  new Position(0, 2) | 3
		new Position(0, 4) |  new Position(0, 5) | 0
		
	}
	
	def "Test blocked Pawn with same color piece"()
	{
	given:
		Position position = new Position(3, 3);
		Pawn pawn = new Pawn(position, board, Color.WHITE);
		Pawn pawn1 = new Pawn(new Position(3, 4), board, Color.WHITE);
	
	when:
		board.addPiece(pawn);
		board.addPiece(pawn1);
	
	then:
		pawn.getLegalMoves().size() == 0;
	}
	/*def "Test blocked Pawn with ennemy pieces"()
	{
		given:
		Position position = new Position(0, 0);

		Pawn pawn = new Pawn(position, board, Color.BLACK);
		Pawn pawn1 = new Pawn(new Position(1, 1), board, Color.WHITE);

		when:
		board.addPiece(pawn);
		board.addPiece(pawn1);

		then:
		pawn.getLegalMoves().size() == 1;
	}
	
	def "Test LegalMoves on Pawn with Ennemy Rook"()
	{
		given:
		Rook blackRook = new Rook(blackRookPosition, board, Color.BLACK)
		Pawn whitePawn = new Pawn(whitePawnPosition, board, Color.WHITE)
		board.addPiece(blackRook)
		board.addPiece(whitePawn)

		expect:
		whitePawn.getLegalMoves().size() == moveSize;

		where:
		blackRookPosition 	| whitePawnPosition|  moveSize
		new Position(1, 1) |  new Position(0, 0) | 1
		new Position(2, 2) |  new Position(0, 0) | 2
		new Position(4, 0) |  new Position(0, 0) | 7
		new Position(7, 7) |  new Position(0, 0) | 7
		new Position(7, 7) |  new Position(0, 0) | 7
		new Position(7, 7) |  new Position(7, 7) | 7
		new Position(7, 7) |  new Position(1, 1) | 9
		new Position(4, 7) |  new Position(1, 1) | 9
		new Position(0, 0) |  new Position(1, 1) | 9
		new Position(0, 0) |  new Position(3, 0) | 7
		new Position(0, 0) |  new Position(2, 0) | 7
		new Position(0, 0) |  new Position(6, 1) | 9
		new Position(4, 3) |  new Position(6, 1) | 5
	}
	
	def "Test LegalMoves on pinned pawn"()
	{
		given:
		Pawn whitePawn = new Pawn(whitePawnPosition, board, Color.BLACK);
		Rook whiteRook = new Rook(whiteRookPosition, board, Color.WHITE);
		King blackKing = new King(blackKingPosition, board, Color.BLACK);
		
		board.addPiece(whitePawn)
		board.addPiece(whiteRook)
		board.addPiece(blackKing)

		expect:
		whitePawn.getLegalMoves().size() == moveSize;

		where:
		whitePawnPosition| whiteRookPosition   | blackKingPosition | moveSize
		new Position(1, 0) |  new Position(4, 0) | new Position(0, 0) | 0
		new Position(2, 0) |  new Position(7, 0) | new Position(0, 0) | 0
	}
	
	def "Test pawn only move when king is in check"()
	{
		given:
		Pawn whitePawn = new Pawn(whitePawnPosition, board, Color.BLACK);
		Rook whiteRook = new Rook(whiteRookPosition, board, Color.WHITE);
		King blackKing = new King(blackKingPosition, board, Color.BLACK);
		
		board.addPiece(whitePawn)
		board.addPiece(whiteRook)
		board.addPiece(blackKing)

		expect:
		whitePawn.getLegalMoves().size() == moveSize;

		where:
		whitePawnPosition| whiteRookPosition   | blackKingPosition | moveSize
		new Position(0, 3) |  new Position(7, 0) | new Position(0, 0) | 1
	}*/
}
