package com.gousslegend.deepov.pieces
import static org.junit.Assert.assertEquals

import org.junit.Test

import spock.lang.*

import com.gousslegend.deepov.Board
import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Position

class WhenTestingCheck extends spock.lang.Specification
{
	@Shared
	def board


	def setup()
	{
		board = new Board()
	}

	def "Testing king position"()
	{
		given:
		def BackKing = new King(position, board, Color.BLACK)
		board.addPiece(BackKing)

		expect:
		position == board.getKing(Color.BLACK).getMyPosition()

		where:
		position << Position.getAllPositionOnBoard()
	}

	def "Testing 2 king position"()
	{
		given:
		Position BackKingPosition = new Position(0, 0)
		Position WhiteKingPosition = new Position(2, 2)

		King BackKing = new King(BackKingPosition, board, Color.BLACK)
		King WhiteKing = new King(WhiteKingPosition,board, Color.WHITE)

		board.addPiece(BackKing)
		board.addPiece(WhiteKing)

		expect:
		board.getKing(Color.BLACK).getMyPosition()== BackKingPosition
		board.getKing(Color.WHITE).getMyPosition()== WhiteKingPosition
	}

	def "Testing rook checking"()
	{
		given:
		King BackKing = new King(new Position(5, 0), board, Color.BLACK)
		Rook WhiteRook = new Rook(new Position(0, 0), board, Color.WHITE)

		board.addPiece(BackKing)
		board.addPiece(WhiteRook)

		expect:
		board.isCheck(Color.BLACK)
	}
}