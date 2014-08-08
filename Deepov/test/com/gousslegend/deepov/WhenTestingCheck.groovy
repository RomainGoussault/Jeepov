package com.gousslegend.deepov
import static org.junit.Assert.assertEquals
import spock.lang.*

import com.gousslegend.deepov.board.ArrayBoard
import com.gousslegend.deepov.pieces.King
import com.gousslegend.deepov.pieces.Pawn
import com.gousslegend.deepov.pieces.Rook

class WhenTestingCheck extends spock.lang.Specification
{
	@Shared
	def board
	@Shared
	def rook


	def setup()
	{
		board = new ArrayBoard()
		rook = new Rook()
	}

	def "Testing king position"()
	{
		given:
		def BackKing = new King(position, board, Color.BLACK)
		board.addPiece(BackKing)

		expect:
		position == board.getKing(Color.BLACK).getPosition()

		where:
		position << Position.getAllPositionOnBoard()
	}

	def "Testing 2 king positions"()
	{
		given: "2 kings"
		Position BackKingPosition = new Position(0, 0)
		Position WhiteKingPosition = new Position(2, 2)

		King BackKing = new King(BackKingPosition, board, Color.BLACK)
		King WhiteKing = new King(WhiteKingPosition,board, Color.WHITE)

		board.addPiece(BackKing)
		board.addPiece(WhiteKing)

		expect:
		board.getKing(Color.BLACK).getPosition()== BackKingPosition
		board.getKingPosition(Color.BLACK) == BackKingPosition
		board.getKing(Color.WHITE).getPosition()== WhiteKingPosition
	}

	def "Testing rook checking "()
	{
		given:
		board.addPiece(new Rook(rookPosition, board, Color.WHITE))
		King BackKing = new King(new Position(3, 3), board, Color.BLACK)
		board.addPiece(BackKing)

		expect:
		board.isCheck(Color.BLACK) == check

		where:
		rookPosition 		| check
		new Position(3, 0)  | true
		new Position(3, 5)  | true
		new Position(4, 4)  | false
		new Position(4, 3)  | true
		new Position(7, 4)  | false
		new Position(7, 7)  | false
	}

	def "Testing rook checking with a blocking ally Pawn"()
	{
		given:"A bord with white king and 2 black pieces"
		board.addPiece(new Rook(rookPosition, board, Color.WHITE))
		board.addPiece(new Pawn(pawnPosition, board, Color.WHITE))
		King BackKing = new King(new Position(0, 0), board, Color.BLACK)
		board.addPiece(BackKing)

		expect: "check when the pawn is not blocking the rook"
		board.isCheck(Color.BLACK) == check

		where:
		rookPosition 	   | pawnPosition        | check
		new Position(7, 0) |  new Position(3, 3) | true
		new Position(3, 0) |  new Position(3, 3) | true
		new Position(0, 7) |  new Position(3, 3) | true
		new Position(0, 3) |  new Position(3, 3) | true
		new Position(0, 7) |  new Position(0, 3) | false
		new Position(7, 0) |  new Position(0, 3) | true
	}
	
	def "Testing rook checking with a blocking ennemy Pawn"()
	{
		given:"A bord with white king and 2 black pieces"
		board.addPiece(new Rook(rookPosition, board, Color.WHITE))
		board.addPiece(new Pawn(pawnPosition, board, Color.BLACK))
		King BackKing = new King(new Position(0, 0), board, Color.BLACK)
		board.addPiece(BackKing)

		expect: "check when the pawn is not blocking the rook"
		board.isCheck(Color.BLACK) == check

		where:
		rookPosition 	   | pawnPosition        | check
		new Position(7, 0) |  new Position(3, 3) | true
		new Position(3, 0) |  new Position(3, 3) | true
		new Position(0, 7) |  new Position(3, 3) | true
		new Position(0, 3) |  new Position(3, 3) | true
		new Position(0, 7) |  new Position(0, 3) | false
		new Position(7, 0) |  new Position(0, 3) | true
	}
}