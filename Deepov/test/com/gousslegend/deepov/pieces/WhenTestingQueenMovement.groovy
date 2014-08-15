package com.gousslegend.deepov.pieces
import static org.junit.Assert.assertEquals
import spock.lang.*

import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Position
import com.gousslegend.deepov.board.ArrayBoard
import com.gousslegend.deepov.board.Board

class WhenTestingQueenMovement extends spock.lang.Specification
{
	@Shared
	Board board
	@Shared
	Queen queen

	def setupSpec()
	{
		board = new ArrayBoard()
		queen = new Queen()
	}
	
	def cleanup()
	{
		board = new ArrayBoard()
	}

	def "Testing queen alone on board"()
	{		
		Queen queen = new Queen(position, board, Color.BLACK);
		board.addPiece(queen);
		
		expect:
		queen.getLegalMoves().size() <= 28
		queen.getLegalMoves().size() >= 21
		
		where:
		position << Position.getAllPositionOnBoard()
	}

	def "Test blocked Queen with ennemy pieces"()
	{
		given:
		Position position = new Position(0, 0);

		Queen queen = new Queen(position, board, Color.BLACK);
		Pawn pawn1 = new Pawn(new Position(1, 1), board, Color.WHITE);
		Pawn pawn2 = new Pawn(new Position(0, 1), board, Color.WHITE);
		Pawn pawn3 = new Pawn(new Position(1, 0), board, Color.WHITE);
		
		when:
		board.addPiece(queen);
		board.addPiece(pawn1);
		board.addPiece(pawn2);
		board.addPiece(pawn3);
		
		then:
		queen.getLegalMoves().size() == 3;
	}
	
	def "Test blocked Queen with ennemy piece"()
	{
		given:
		Position position = new Position(0, 0);

		Queen queen = new Queen(position, board, Color.BLACK);
		Pawn pawn1 = new Pawn(new Position(1, 1), board, Color.WHITE);

		when:
		board.addPiece(queen);
		board.addPiece(pawn1);

		then:
		queen.getLegalMoves().size() == 15;
	}

	def "Test LegalMoves on pinned queen"()
	{
		given:
		Queen blackQueen = new Queen(blackQueenPosition, board, Color.BLACK);
		Rook whiteRook = new Rook(whiteRookPosition, board, Color.WHITE);
		King blackKing = new King(blackKingPosition, board, Color.BLACK);
		
		board.addPiece(blackQueen)
		board.addPiece(whiteRook)
		board.addPiece(blackKing)
		board.setColorToPlay(Color.BLACK)
		board.updatePinnedPieces()

		expect:
		blackQueen.getLegalMoves().size() == moveSize;

		where:
		blackQueenPosition| whiteRookPosition   | blackKingPosition | moveSize
		new Position(1, 0) |  new Position(4, 0) | new Position(0, 0) | 3
		new Position(2, 0) |  new Position(7, 0) | new Position(0, 0) | 6
	}
	
	def "Test queen only move when king is in check"()
	{
		given:
		Queen whiteQueen = new Queen(whiteQueenPosition, board, Color.BLACK);
		Rook whiteRook = new Rook(whiteRookPosition, board, Color.WHITE);
		King blackKing = new King(blackKingPosition, board, Color.BLACK);
		
		board.addPiece(whiteQueen)
		board.addPiece(whiteRook)
		board.addPiece(blackKing)

		expect:
		whiteQueen.getLegalMoves().size() == moveSize;

		where:
		whiteQueenPosition| whiteRookPosition   | blackKingPosition | moveSize
		new Position(0, 3) |  new Position(7, 0) | new Position(0, 0) | 1
	}
	
	def "Test Capture Move on Queen with Ennemy Rook"()
	{
		given:
		Rook blackRook = new Rook(new Position(1, 1), board, Color.BLACK)
		Queen whiteQueen = new Queen(new Position(0, 0), board, Color.WHITE)
		Bishop whiteBishop = new Bishop(new Position(1, 0), board, Color.WHITE)
		Bishop whiteBishop2 = new Bishop(new Position(0, 1), board, Color.WHITE)
		
		board.addPiece(blackRook)
		board.addPiece(whiteQueen)
		board.addPiece(whiteBishop)
		board.addPiece(whiteBishop2)
		
		expect:
		whiteQueen.getLegalMoves().getFistMove().getCapturedPiece() == blackRook;
	}
}
