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

	def "Testing rook alone on board"()
	{
		expect:
		new Rook(position, board, Color.BLACK).getLegalMoves().size() == 14

		where:
		position << Position.getAllPositionOnBoard()
	}

	def "Test blocked Rook with ennemy pieces"()
	{
		given:
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
	
	def "Test pseudoLegalMoves on pinned rook"()
	{
		given:
		Rook blackRook = new Rook(blackRookPosition, board, Color.BLACK);
		Rook whiteRook = new Rook(whiteRookPosition, board, Color.WHITE);
		King blackKing = new King(blackKingPosition, board, Color.BLACK);
		
		board.addPiece(blackRook)
		board.addPiece(whiteRook) 
		board.addPiece(blackKing)

		expect: "check when the pawn is not blocking the rook"
		blackRook.getPseudoLegalMoves().size() == moveSize;

		where:
		blackRookPosition 	| whiteRookPosition   | blackKingPosition | moveSize
		new Position(1, 0) |  new Position(3, 0) | new Position(0, 0) | 9
		new Position(1, 0) |  new Position(2, 0) | new Position(0, 0) | 8
		
	}
	
	def "Test LegalMoves on pinned rook"()
	{
		given:
		Board board2 = new Board();
		Rook blackRook = new Rook(blackRookPosition, board2, Color.BLACK);
		Rook whiteRook = new Rook(whiteRookPosition, board2, Color.WHITE);
		King blackKing = new King(blackKingPosition, board2, Color.BLACK);
		
		board2.addPiece(blackRook)
		board2.addPiece(whiteRook)
		board2.addPiece(blackKing)

		expect: "check when the pawn is not blocking the rook"
		blackRook.getLegalMoves().size() == moveSize;

		where:
		blackRookPosition 	| whiteRookPosition   | blackKingPosition | moveSize
		new Position(1, 0) |  new Position(2, 0) | new Position(0, 0) | 1
		
	}
}
