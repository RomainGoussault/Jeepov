package com.gousslegend.deepov.pieces
import static org.junit.Assert.assertEquals
import spock.lang.*

import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Move
import com.gousslegend.deepov.Position
import com.gousslegend.deepov.board.ArrayBoard
import com.gousslegend.deepov.board.Board

class WhenTestingKingMovement extends spock.lang.Specification
{

	@Shared
	Board board
	@Shared
	King king

	def setupSpec()
	{
		board = new ArrayBoard()
		king = new King()
	}
	
	def cleanup()
	{
		board = new ArrayBoard()
	}

	def "Testing king alone on board"()
	{		
		King king = new King(position, board, Color.BLACK);
		board.addPiece(king);
		
		expect:
		king.getLegalMoves().size() <= 8
		king.getLegalMoves().size() >= 3
		
		where:
		position << Position.getAllPositionOnBoard()
	}

	def "Test blocked King with ennemy pieces"()
	{
		given:
		Position position = new Position(0, 0);

		King king = new King(position, board, Color.BLACK);
		Pawn pawn1 = new Pawn(new Position(1, 1), board, Color.WHITE);
		Pawn pawn2 = new Pawn(new Position(0, 1), board, Color.WHITE);
		Pawn pawn3 = new Pawn(new Position(1, 0), board, Color.WHITE);
		
		when:
		board.addPiece(king);
		board.addPiece(pawn1);
		board.addPiece(pawn2);
		board.addPiece(pawn3);
		
		then:
		king.getPseudoLegalMoves().size() == 3;
		king.getLegalMoves().size() == 2;
	}
	
	def "Test blocked King with ally piece"()
	{
		given:
		Position position = new Position(0, 0);

		King king = new King(position, board, Color.BLACK);
		Pawn pawn1 = new Pawn(new Position(1, 1), board, Color.BLACK);
		Pawn pawn2 = new Pawn(new Position(0, 1), board, Color.BLACK);
		Pawn pawn3 = new Pawn(new Position(1, 0), board, Color.BLACK);
		when:
		board.addPiece(king);
		board.addPiece(pawn1);
		board.addPiece(pawn2);
		board.addPiece(pawn3);
		
		then:
		king.getLegalMoves().size() == 0
	}

	def "Test 2 kings"()
	{
		given:
		King whiteKing = new King(whiteKingPosition, board, Color.WHITE)
		King blackKing = new King(blackKingPosition, board, Color.BLACK)

		board.addPiece(whiteKing)
		board.addPiece(blackKing)

		expect:
		whiteKing.getLegalMoves().size() == moveSize

		where:
		whiteKingPosition  | blackKingPosition   | moveSize
		new Position(0, 0) |  new Position(2, 1)  | 1
	}

	def "Test Capture Move on King with Ennemy Rook"()
	{
		given:
		Rook blackRook = new Rook(new Position(0, 1), board, Color.BLACK)
		King whiteKing = new King(new Position(0, 0), board, Color.WHITE)
		King blackKing = new King(new Position(2, 1), board, Color.BLACK)
		
		
		board.addPiece(blackRook)
		board.addPiece(whiteKing)
		board.addPiece(blackKing)
		
		expect:
		whiteKing.getLegalMoves().getFistMove().getCapturedPiece() == blackRook;
	}
	
	@Unroll
	def "isKingSideCastlingPossible"()
	{
		given:
		King whiteKing = new King(whiteKingPosition, board, Color.WHITE)
		Rook whiteRook = new Rook(whiteRookPosition, board, Color.WHITE)
		Rook blackRook = new Rook(blackRookPosition, board, Color.BLACK)

		board.addPiece(whiteKing)
		board.addPiece(whiteRook)
		board.addPiece(blackRook)
		
		expect:
		whiteKing.isKingSideCastlingPossible() == castlingAllowed

		where:
		whiteKingPosition  | whiteRookPosition    | blackRookPosition   | castlingAllowed
		new Position(4, 0) |  new Position(7, 0)  |  new Position(5, 7) | false
		new Position(4, 0) |  new Position(7, 0)  |  new Position(4, 7) | false
		new Position(4, 0) |  new Position(7, 0)  |  new Position(6, 7) | false
		new Position(4, 0) |  new Position(7, 0)  |  new Position(7, 7) | true
		new Position(4, 0) |  new Position(7, 0)  |  new Position(1, 7) | true
		new Position(4, 0) |  new Position(7, 0)  |  new Position(2, 5) | true
		new Position(4, 0) |  new Position(7, 0)  |  new Position(3, 3) | true
		new Position(4, 0) |  new Position(5, 0)  |  new Position(7, 0) | false
	}
	
	@Unroll
	def "isQueenSideCastlingPossible"()
	{
		given:
		King whiteKing = new King(whiteKingPosition, board, Color.WHITE)
		Rook whiteRook = new Rook(whiteRookPosition, board, Color.WHITE)
		Rook blackRook = new Rook(blackRookPosition, board, Color.BLACK)

		board.addPiece(whiteKing)
		board.addPiece(whiteRook)
		board.addPiece(blackRook)
		
		expect:
		whiteKing.isQueenSideCastlingPossible() == castlingAllowed

		where:
		whiteKingPosition  | whiteRookPosition    | blackRookPosition   | castlingAllowed
		new Position(4, 0) |  new Position(0, 0)  |  new Position(0, 7) | true
		new Position(4, 0) |  new Position(0, 0)  |  new Position(1, 5) | true
		new Position(4, 0) |  new Position(0, 0)  |  new Position(2, 7) | false
		new Position(4, 0) |  new Position(0, 0)  |  new Position(3, 7) | false
		new Position(4, 0) |  new Position(0, 0)  |  new Position(4, 7) | false
		new Position(4, 0) |  new Position(0, 0)  |  new Position(5, 7) | true
		new Position(4, 0) |  new Position(0, 0)  |  new Position(6, 5) | true
		new Position(4, 0) |  new Position(0, 0)  |  new Position(7, 3) | true
		new Position(4, 0) |  new Position(5, 0)  |  new Position(3, 3) | false
	}
	
	@Unroll
	def "Executing castling move"()
	{
		given:
		King whiteKing = new King(whiteKingPosition, board, Color.WHITE)
		Rook whiteRook = new Rook(whiteRookPosition, board, Color.WHITE)

		board.addPiece(whiteKing)
		board.addPiece(whiteRook)
		Move castlingMove = whiteKing.getCastlingMoves().get(0);
		board.executeMove(castlingMove);
		
		expect:
		whiteKing.isKingSideCastlingPossible() == castlingAllowed

		where:
		whiteKingPosition  | whiteRookPosition   | castlingAllowed
		new Position(4, 0) |  new Position(0, 0)  | false
		new Position(4, 0) |  new Position(7, 0)  | false
	}
	
	@Unroll
	def "Undoing castling move"()
	{
		given:
		King blackKing = new King(blackKingPosition, board, Color.BLACK)
		Rook blackRook = new Rook(blackRookPosition, board, Color.BLACK)

		board.addPiece(blackKing)
		board.addPiece(blackRook)
		Move castlingMove = blackKing.getCastlingMoves().get(0);
		
		board.executeMove(castlingMove);
		board.undoMove(castlingMove);
		
		expect:
		board.getNumberOfPieces() == 2;
		blackKing.getCastlingMoves().size() == 1
		board.getKingPosition(Color.BLACK).equals(blackKingPosition);

		where:
		blackKingPosition  | blackRookPosition   
		new Position(4, 7) |  new Position(0, 7)
		new Position(4, 7) |  new Position(7, 7)
	}
	
	@Unroll
	def "Undoing castling move 2"()
	{
		given:
		King blackKing = new King(blackKingPosition, board, Color.BLACK)
		Rook blackRook = new Rook(blackRookPosition, board, Color.BLACK)

		board.addPiece(blackKing)
		board.addPiece(blackRook)
		Move castlingMove = blackKing.getCastlingMoves().get(0);
		
		board.executeMove(castlingMove);
		board.undoMove(castlingMove);
		
		expect:
		board.getNumberOfPieces() == 2;
		blackKing.getCastlingMoves().size() == 1
		board.getKingPosition(Color.BLACK).equals(blackKingPosition);

		where:
		blackKingPosition  | blackRookPosition
		new Position(4, 7) |  new Position(0, 7)
		new Position(4, 7) |  new Position(7, 7)
	}
}