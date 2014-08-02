package com.gousslegend.deepov.pieces
import spock.lang.*

import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Move
import com.gousslegend.deepov.Position
import com.gousslegend.deepov.board.Board;
import com.gousslegend.deepov.board.MapBoard;

class WhenTestingPawnMovement extends spock.lang.Specification
{

	@Shared
	Board board
	@Shared
	Pawn pawn
	
	def setupSpec()
	{
		board = new MapBoard()
		pawn = new Pawn()
	}

	def cleanup()
	{
		board = new MapBoard()
	}

	def "Testing pawn alone on board"()
	{
		given:
		Pawn pawn = new Pawn(whitePawnPosition, board, Color.WHITE)
		board.addPiece(pawn)

		expect:
		pawn.getLegalMoves().size() == moveSize
		
		where:
		whitePawnPosition  |  moveSize
		new Position(5, 5) | 1
		new Position(4, 4) | 1
		new Position(5, 4) | 1
		new Position(6, 6) | 1
		new Position(2, 4) | 1
	}
	
	def "Test LegalMoves on Pawn with Ennemy Rook"()
	{
		given:
		Pawn whitePawn = new Pawn(whitePawnPosition, board, Color.WHITE)
		Rook blackRook = new Rook(blackRookPosition, board, Color.BLACK)
		board.addPiece(blackRook)
		board.addPiece(whitePawn)

		expect:
		whitePawn.getLegalMoves().size() == moveSize

		where:
		whitePawnPosition	|  blackRookPosition|  moveSize
		new Position(1, 1) |  new Position(0, 0) | 2
		new Position(2, 2) |  new Position(0, 0) | 1
		new Position(4, 0) |  new Position(0, 0) | 1
		new Position(1, 1) |  new Position(2, 2) | 3
		new Position(1, 1) |  new Position(0, 2) | 3
		new Position(0, 4) |  new Position(0, 5) | 0
	}

	def "Test attacking squares"()
	{
		given:
		Pawn whitePawn = new Pawn(whitePawnPosition, board, Color.WHITE)
		Rook blackRook = new Rook(blackRookPosition, board, Color.BLACK)
		Rook blackRook2 = new Rook(blackRookPosition2, board, Color.BLACK)

		board.addPiece(whitePawn)
		board.addPiece(blackRook)
		board.addPiece(blackRook2)

		expect:
		whitePawn.getAttackingSquares().size() == attackingSquaresSize

		where:
		whitePawnPosition  |  blackRookPosition|  blackRookPosition2  | attackingSquaresSize
		new Position(1, 1) |  new Position(5, 5) |  new Position(6, 6)| 0
		new Position(1, 3) |  new Position(0, 4) |  new Position(2, 4)| 2
		new Position(1, 3) |  new Position(2, 4) |  new Position(7, 4)| 1
	}

	def "Test 4 Moves on Pawn"()
	{
		given:
		Pawn whitePawn = new Pawn(new Position(5,1), board, Color.WHITE)
		Rook blackRook = new Rook(new Position(4,2), board, Color.BLACK)
		Rook blackRook2 = new Rook(new Position(6,2), board, Color.BLACK)

		board.addPiece(whitePawn)
		board.addPiece(blackRook)
		board.addPiece(blackRook2)

		expect:
		whitePawn.getLegalMoves().size() == 4
	}

	@Unroll
	def "Test isPromotion"()
	{
		given:
		Pawn whitePawn = new Pawn(whitePawnPosition, board, Color.WHITE)
		Rook blackRook = new Rook(blackRookPosition, board, Color.BLACK)
		Rook blackRook2 = new Rook(blackRookPosition2, board, Color.BLACK)

		board.addPiece(whitePawn)
		board.addPiece(blackRook)
		board.addPiece(blackRook2)

		expect:
		whitePawn.getLegalMoves().getList().get(0).isPromotion() == isPromotion

		where:
		whitePawnPosition  |  blackRookPosition|  blackRookPosition2  | isPromotion
		new Position(1, 1) |  new Position(5, 5) |  new Position(6, 6)| false
		new Position(1, 6) |  new Position(7, 7) |  new Position(0, 0)| true
		new Position(7, 6) |  new Position(2, 4) |  new Position(7, 4)| true
		new Position(7, 1) |  new Position(2, 4) |  new Position(7, 4)| false
	}

	@Unroll
	def "Test Promotion"()
	{
		given:
		Pawn whitePawn = new Pawn(new Position(5,6), board, Color.WHITE)
		board.addPiece(whitePawn)

		when:
		Move promotionMove = whitePawn.getLegalMoves().getFistMove()
		board.executeMove(promotionMove)

		then:
		board.getPiece(new Position(5,7)) instanceof Queen
		board.getNumberOfPieces() == 1
	}

	@Unroll
	def "Test Promotion with capture"()
	{
		given:
		Pawn whitePawn = new Pawn(new Position(7,6), board, Color.WHITE)
		Rook whiteRook = new Rook(new Position(7,7), board, Color.WHITE)
		Rook blackRook = new Rook(new Position(6,7), board, Color.BLACK)
		board.addPiece(whitePawn)
		board.addPiece(whiteRook)
		board.addPiece(blackRook)

		when:
		Move promotionMove = whitePawn.getLegalMoves().getFistMove()
		board.executeMove(promotionMove)

		then:
		board.getPiece(new Position(6,7)) instanceof Queen
		promotionMove.isPromotion() == true
		board.getNumberOfPieces() == 2
		promotionMove.getCapturedPiece() == blackRook
	}

	def "Test blocked Pawn with same color piece"()
	{
		given:
		Position position = new Position(3, 3)
		Pawn pawn = new Pawn(position, board, Color.WHITE)
		Pawn pawn1 = new Pawn(new Position(3, 4), board, Color.WHITE)

		when:
		board.addPiece(pawn)
		board.addPiece(pawn1)

		then:
		pawn.getLegalMoves().size() == 0
	}

	def "Test LegalMoves on the edge of the board"()
	{
		given:
		Pawn whitePawn = new Pawn(whitePawnPosition, board, Color.BLACK)

		board.addPiece(whitePawn)

		expect:
		whitePawn.getLegalMoves().size() == moveSize

		where:
		whitePawnPosition  |  moveSize
		new Position(0, 5) | 1
		new Position(7, 5) | 1
	}
	
	@Unroll
	def "Test en +2 move"()
	{
		given: "Two pawns"
		Pawn whitePawn = new Pawn(whitePawnPosition, board, Color.WHITE)
		Pawn whitePawn2 = new Pawn(whitePawn2Position, board, Color.WHITE)
		board.addPiece(whitePawn)
		board.addPiece(whitePawn2)

		expect:
		whitePawn.getLegalMoves().size() == moveSize

		where:
		whitePawnPosition  | whitePawn2Position    | moveSize 
		new Position(0, 1) | new Position(5, 5)    | 2
		new Position(0, 1) | new Position(0, 2)    | 0
		new Position(0, 1) | new Position(0, 3)    | 1
	}
	
	@Unroll
	def "Test en passant"()
	{
		given: "Two pawns"
		Pawn whitePawn = new Pawn(whitePawnPosition, board, Color.WHITE)
		Pawn blackPawn = new Pawn(blackPawnPositionOrigin, board, Color.BLACK)
		board.addPiece(whitePawn)
		board.addPiece(blackPawn)

		board.executeMove(new Move(blackPawnPositionOrigin, blackPawnPositionDestination))

		expect:
		whitePawn.enPassantCapturePossible() == enPassantCapturePossible

		where:
		whitePawnPosition  | blackPawnPositionOrigin  | blackPawnPositionDestination  |enPassantCapturePossible
		new Position(0, 5) | new Position(5, 5)       | new Position(5, 4)            | false
		new Position(1, 4) | new Position(0, 6)       | new Position(0, 4)            | true
		new Position(1, 4) | new Position(2, 6)       | new Position(2, 4)            | true
		new Position(1, 4) | new Position(2, 6)       | new Position(2, 5)            | false
		new Position(1, 4) | new Position(1, 6)       | new Position(1, 5)            | false
		new Position(1, 4) | new Position(2, 7)       | new Position(2, 6)            | false
	}

	@Unroll
	def "Test en passant 2"()
	{
		given: "Two pawns"
		Pawn whitePawn = new Pawn(whitePawnPosition, board, Color.WHITE)
		Rook whiteRook = new Rook(new Position(0,0), board, Color.WHITE)
		Pawn blackPawn = new Pawn(blackPawnPositionOrigin, board, Color.BLACK)
		Rook blackRook = new Rook(new Position(7,7), board, Color.BLACK)

		board.addPiece(whitePawn)
		board.addPiece(whiteRook)
		board.addPiece(blackPawn)
		board.addPiece(blackRook)
		board.executeMove(new Move(blackPawnPositionOrigin, blackPawnPositionDestination)) //black move
		board.executeMove(new Move(new Position(0,0), new Position(1,0))) //white move
		board.executeMove(new Move(new Position(7,7), new Position(7,0))) //black move again

		expect:
		whitePawn.enPassantCapturePossible() == enPassantCapturePossible

		where:
		whitePawnPosition  | blackPawnPositionOrigin  | blackPawnPositionDestination  |enPassantCapturePossible
		new Position(1, 4) | new Position(0, 6)       | new Position(0, 4)            | false
		new Position(1, 4) | new Position(2, 6)       | new Position(2, 4)            | false
	}

	@Unroll
	def "Test legal Moves"()
	{
		given: "Two pawns"
		Pawn whitePawn = new Pawn(whitePawnPosition, board, Color.WHITE)
		King whiteKing = new King(new Position(0,7), board, Color.WHITE)
		Bishop blackBishop1 = new Bishop(blackBishop1Position, board, Color.BLACK)
		Bishop blackBishop2 = new Bishop(blackBishop2Position, board, Color.BLACK)

		board.addPiece(whitePawn)
		board.addPiece(whiteKing)
		board.addPiece(blackBishop1)
		board.addPiece(blackBishop2)

		expect:
		whitePawn.getLegalMoves().size() == legalMovePossibles
		whitePawn.getPseudoLegalMoves().size() == pseudoLegalMovePossibles

		where:
		whitePawnPosition  | blackBishop1Position  | blackBishop2Position  | legalMovePossibles | pseudoLegalMovePossibles
		new Position(2, 4) | new Position(1, 5)    | new Position(3, 4)    | 1					| 2
		new Position(2, 4) | new Position(1, 5)    | new Position(0, 0)    | 2					| 2
	}

	@Unroll
	def "Test pawn checking"()
	{
		given: "Two pawns"
		Pawn blackPawn = new Pawn(blackPawnPosition, board, Color.BLACK)
		King whiteKing = new King(whiteKingPosition, board, Color.WHITE)
		Rook whiteRook = new Rook(whiteRookPosition, board, Color.WHITE)

		board.addPiece(blackPawn)
		board.addPiece(whiteKing)
		board.addPiece(whiteRook)

		expect:
		whiteRook.getLegalMoves().size() == legalMovePossibles

		where:
		blackPawnPosition  | whiteKingPosition  | whiteRookPosition  | legalMovePossibles
		new Position(1, 2) | new Position(0, 1) | new Position(7, 2) | 1
	}
}