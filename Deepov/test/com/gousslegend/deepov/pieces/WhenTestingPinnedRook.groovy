package com.gousslegend.deepov.pieces
import static org.junit.Assert.assertEquals
import spock.lang.*

import com.gousslegend.deepov.Board
import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Position

class WhenTestingPinnedRook extends spock.lang.Specification
{
	@Shared
	def board


	def setup()
	{
		board = new Board()
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

		expect: "check when the pawn is not blocking the rook"
		//board.getKingPosition(Color.BLACK) == blackKingPosition
		blackRook.getLegalMoves().size() == moveSize;

		where:
		blackRookPosition 	| whiteRookPosition   | blackKingPosition | moveSize
		new Position(1, 0) |  new Position(2, 0) | new Position(0, 0) | 1
		
	}
}
