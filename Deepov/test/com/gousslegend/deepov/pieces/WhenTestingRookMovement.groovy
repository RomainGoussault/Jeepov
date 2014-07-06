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
		new Rook(position, board, Color.BLACK).getPseudoLegalMoves().size() == 14

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
		rook.getPseudoLegalMoves().size() == 2;
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
		rook.getPseudoLegalMoves().size() == 0;
	}
}
