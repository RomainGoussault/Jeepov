package com.gousslegend.deepov.pieces
import static org.junit.Assert.assertEquals
import spock.lang.*

import com.gousslegend.deepov.Board
import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Position

class WhenTestingPinnedBishop extends spock.lang.Specification
{
	@Shared
	def board


	def setup()
	{
		board = new Board()
	}
	
	def "Test LegalMoves on pinned bishop"()
	{
		given:
		Bishop whiteBishop = new Bishop(whiteBishopPosition, board, Color.BLACK);
		Rook whiteRook = new Rook(whiteRookPosition, board, Color.WHITE);
		King blackKing = new King(blackKingPosition, board, Color.BLACK);
		
		board.addPiece(whiteBishop)
		board.addPiece(whiteRook)
		board.addPiece(blackKing)

		expect: 
		whiteBishop.getLegalMoves().size() == moveSize;

		where:
		whiteBishopPosition| whiteRookPosition   | blackKingPosition | moveSize
		new Position(1, 0) |  new Position(4, 0) | new Position(0, 0) | 0
		new Position(2, 0) |  new Position(7, 0) | new Position(0, 0) | 0
	}
}
