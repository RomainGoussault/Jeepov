package com.gousslegend.deepov
import static org.junit.Assert.assertEquals
import com.gousslegend.deepov.pieces.Piece
import spock.lang.*

class WhenTestingPerft extends spock.lang.Specification
{
	def "Pieces number"()
	{
		given:
		Board board = new Board()
		board.setupBoard();

		def list = board.getPieces()

		expect:
		list.size() == 32;
	}

	def "Perft1"()
	{
		given:
		Board board = new Board()
		board.setupBoard();

		def movesList = new ArrayList<Move>();
		def pieces = board.getPieces()
		
		for(Piece piece: pieces)
		{
			movesList.addAll(piece.getLegalMoves().getList())
		}
		
		expect:
		movesList.size() == 20;
	}
}