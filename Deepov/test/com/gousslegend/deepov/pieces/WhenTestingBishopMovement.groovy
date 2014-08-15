package com.gousslegend.deepov.pieces
import static org.junit.Assert.assertEquals
import spock.lang.*

import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Position
import com.gousslegend.deepov.board.ArrayBoard
import com.gousslegend.deepov.board.Board

class WhenTestingBishopMovement extends spock.lang.Specification
{
	@Shared
	Board board
	@Shared
	Bishop bishop

	def setupSpec()
	{
		board = new ArrayBoard()
		bishop = new Bishop()
	}
	
	def cleanup()
	{
		board = new ArrayBoard()
	}

	def "Testing bishop alone on board"()
	{		
		Bishop bishop = new Bishop(position, board, Color.BLACK);
		board.addPiece(bishop);
		
		expect:
		bishop.getLegalMoves().size() <= 14
		bishop.getLegalMoves().size() >= 7
		bishop.getAttackingSquares().size() <= 14
		bishop.getAttackingSquares().size() >= 7
		
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
	
	def "Test blocked Bishop with ennemy piece"()
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
		new Position(0, 0) |  new Position(1, 1) | 9
		new Position(0, 0) |  new Position(3, 0) | 7
		new Position(0, 0) |  new Position(2, 0) | 7
		new Position(0, 0) |  new Position(6, 1) | 9
		new Position(4, 3) |  new Position(6, 1) | 5
		new Position(7, 7) |  new Position(0, 0) | 7
		new Position(7, 7) |  new Position(0, 0) | 7
		new Position(7, 7) |  new Position(1, 1) | 9
		new Position(4, 7) |  new Position(1, 1) | 9
	}
	
	def "Test LegalMoves on pinned bishop"()
	{
		given:
		Bishop blackBishop = new Bishop(blackBishopPosition, board, Color.BLACK);
		Rook whiteRook = new Rook(whiteRookPosition, board, Color.WHITE);
		King blackKing = new King(blackKingPosition, board, Color.BLACK);
		
		board.addPiece(blackBishop)
		board.addPiece(whiteRook)
		board.addPiece(blackKing)
		board.setColorToPlay(Color.BLACK)
		board.updatePinnedPieces()

		expect:
		blackBishop.getLegalMoves().size() == moveSize;

		where:
		blackBishopPosition| whiteRookPosition   | blackKingPosition | moveSize
		new Position(1, 0) |  new Position(4, 0) | new Position(0, 0) | 0
		new Position(2, 0) |  new Position(7, 0) | new Position(0, 0) | 0
	}
	
	def "Test bishop only move when king is in check"()
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
		new Position(0, 3) |  new Position(7, 0) | new Position(0, 0) | 1
	}
	
	def "Test Capture Move on Bishop with Ennemy Rook"()
	{
		given:
		Rook blackRook = new Rook(new Position(1, 1), board, Color.BLACK)
		Bishop whiteBishop = new Bishop( new Position(0, 0), board, Color.WHITE)
		board.addPiece(blackRook)
		board.addPiece(whiteBishop)

		expect:
		whiteBishop.getLegalMoves().getFistMove().getCapturedPiece() == blackRook;
	}
	
	def "Test attacking squares on Bishop"()
	{
		given:
		Bishop blackBishop = new Bishop(blackBishopPosition, board, Color.BLACK);
		Bishop whiteBishop = new Bishop(whiteBishopPosition, board, Color.WHITE);
		King blackKing = new King(blackKingPosition, board, Color.BLACK);
		
		board.addPiece(blackBishop)
		board.addPiece(whiteBishop)
		board.addPiece(blackKing)

		expect:
		blackBishop.getAttackingSquares().size() == attackingSquareSize;

		where:
		blackBishopPosition| whiteBishopPosition   | blackKingPosition | attackingSquareSize
		new Position(0, 0) |  new Position(1, 1) | new Position(0, 1) | 1
		new Position(0, 0) |  new Position(2, 2) | new Position(0, 1) | 2
		new Position(1, 1) |  new Position(2, 2) | new Position(0, 0) | 4
		new Position(1, 1) |  new Position(2, 2) | new Position(0, 3) | 4
	}
}
