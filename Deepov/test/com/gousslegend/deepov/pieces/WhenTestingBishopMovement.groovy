package com.gousslegend.deepov.pieces
import static org.junit.Assert.assertEquals
import spock.lang.*

import com.gousslegend.deepov.Board
import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Position

class WhenTestingBishopMovement extends spock.lang.Specification
{

	@Shared
	def board
	@Shared
	def bishop


	def setupSpec()
	{
		board = new Board()
		bishop = new Bishop()
	}
	
	def cleanup()
	{
		board = new Board()
	}

	def "Testing bishop alone on board"()
	{		
		Bishop bishop = new Bishop(position, board, Color.BLACK);
		board.addPiece(bishop);
		
		expect:
		bishop.getLegalMoves().size() <= 14
		bishop.getLegalMoves().size() >= 7
		
		where:
		position << Position.getAllPositionOnBoard()
	}

	def "Test blocked Bishop with ennemy pieces"()
	{
		given:
		Position position = new Position(0, 0);

		Bishop bishop = new Bishop(position, board, Color.BLACK);
		Pawn pawn1 = new Pawn(new Position(1, 1), board, Color.WHITE);

		when:
		board.addPiece(bishop);
		board.addPiece(pawn1);

		then:
		bishop.getLegalMoves().size() == 1;
	}
	
	def "Test blocked Bishop with same color piece"()
	{
		given:
		Position position = new Position(0, 0);

		Bishop bishop = new Bishop(position, board, Color.BLACK);
		Pawn pawn1 = new Pawn(new Position(1, 1), board, Color.WHITE);

		when:
		board.addPiece(bishop);
		board.addPiece(pawn1);

		then:
		bishop.getLegalMoves().size() == 1;
	}
	
	def "Test LegalMoves on Bishop with Ennemy Rook"()
	{
		given:
		Rook blackRook = new Rook(blackRookPosition, board, Color.BLACK)
		Bishop whiteBishop = new Bishop(whiteBishopPosition, board, Color.WHITE)
		board.addPiece(blackRook)
		board.addPiece(whiteBishop)

		expect:
		whiteBishop.getLegalMoves().size() == moveSize;

		where:
		blackRookPosition 	| whiteBishopPosition|  moveSize
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
}
