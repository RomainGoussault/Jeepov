package com.gousslegend.deepov.pieces
import static org.junit.Assert.assertEquals
import spock.lang.*

import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Position
import com.gousslegend.deepov.board.ArrayBoard
import com.gousslegend.deepov.board.Board

class WhenTestingKnightMovement extends spock.lang.Specification
{

	@Shared
	Board board
	@Shared
	Knight knight


	def setupSpec()
	{
		board = new ArrayBoard()
		knight = new Knight()
	}
	
	def cleanup()
	{
		board = new ArrayBoard()
	}

	def "Testing knight alone on board"()
	{		
		Knight knight = new Knight(position, board, Color.BLACK);
		board.addPiece(knight);
		
		expect:
		knight.getPseudoLegalMoves().size() >= 2
		knight.getPseudoLegalMoves().size() <= 8
		
		where:
		position << Position.getAllPositionOnBoard()
	}
	
	def "Test blocked Knight with ennemy pieces"()
	{
		given:
		Position position = new Position(0, 0);

		Knight knight = new Knight(position, board, Color.BLACK);
		Pawn pawn1 = new Pawn(new Position(1, 0), board, Color.WHITE);
		Pawn pawn2 = new Pawn(new Position(0, 1), board, Color.WHITE);

		when:
		board.addPiece(knight);
		board.addPiece(pawn1);
		board.addPiece(pawn2);

		then:
		knight.getLegalMoves().size() == 2;
	}
	
	def "Test blocked Knight with same color piece"()
	{
		given:
		Position position = new Position(0, 0);

		Knight knight = new Knight(position, board, Color.BLACK);
		Pawn pawn1 = new Pawn(new Position(1, 0), board, Color.BLACK);
		Pawn pawn2 = new Pawn(new Position(0, 1), board, Color.BLACK);

		when:
		board.addPiece(knight);
		board.addPiece(pawn1);
		board.addPiece(pawn2);

		then:
		knight.getLegalMoves().size() == 2;
	}
	
	@Unroll
	def "Test LegalMoves on pinned knight"()
	{
		given:
		Knight blackKnight = new Knight(blackKnightPosition, board, Color.BLACK);
		Rook whiteRook = new Rook(whiteRookPosition, board, Color.WHITE);
		King blackKing = new King(blackKingPosition, board, Color.BLACK);
		
		board.addPiece(blackKnight)
		board.addPiece(whiteRook)
		board.addPiece(blackKing)
		board.setColorToPlay(Color.BLACK)
		board.updatePinnedPieces()

		expect:
		blackKnight.getLegalMoves().size() == moveSize;

		where:
		blackKnightPosition| whiteRookPosition   | blackKingPosition | moveSize
		new Position(1, 0) |  new Position(2, 0) | new Position(0, 0) | 0
		new Position(1, 0) |  new Position(3, 0) | new Position(0, 0) | 0
		new Position(1, 0) |  new Position(3, 3) | new Position(0, 0) | 3
		new Position(1, 0) |  new Position(3, 0) | new Position(0, 0) | 0
		new Position(1, 3) |  new Position(0, 3) | new Position(7, 3) | 0
		new Position(1, 0) |  new Position(0, 3) | new Position(0, 0) | 1
		new Position(1, 0) |  new Position(0, 2) | new Position(0, 0) | 1
	}
	
	@Unroll
	def "Test knight checking"()
	{
		given:
		Knight blackKnight = new Knight(blackKnightPosition, board, Color.BLACK);
		Rook whiteRook = new Rook(whiteRookPosition, board, Color.WHITE);
		King whiteKing = new King(whiteKingPosition, board, Color.WHITE);
		
		board.addPiece(blackKnight)
		board.addPiece(whiteRook)
		board.addPiece(whiteKing)

		expect:
		whiteRook.getLegalMoves().size() == moveSize;

		where:
		blackKnightPosition| whiteKingPosition   | whiteRookPosition | moveSize
		new Position(0, 1) |  new Position(2, 2) | new Position(0, 7) | 1
		new Position(1, 0) |  new Position(2, 2) | new Position(0, 7) | 0
	}
}
