package com.gousslegend.deepov.pieces
import spock.lang.*

import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Game
import com.gousslegend.deepov.Move
import com.gousslegend.deepov.Position
import com.gousslegend.deepov.board.ArrayBoard
import com.gousslegend.deepov.board.Board

class WhenTestingPawnMovement extends spock.lang.Specification
{

	@Shared
	Board board
	@Shared
	Pawn pawn
	
	def setupSpec()
	{
		board = new ArrayBoard()
		pawn = new Pawn()
	}

	def cleanup()
	{
		board = new ArrayBoard()
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
		new Position(6, 6) | 4
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
		new Position(4, 1) |  new Position(0, 0) | 2
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
		new Position(1, 1) |  new Position(5, 5) |  new Position(6, 6)| 2
		new Position(1, 3) |  new Position(0, 4) |  new Position(2, 4)| 2
		new Position(1, 3) |  new Position(2, 4) |  new Position(7, 4)| 2
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
		(board.getPiece(new Position(5,7)) instanceof Pawn) == false
		board.getNumberOfPieces() == 1
	}

	@Unroll
	def "Test Undo Promotion"()
	{
		given:
		Position pawnPosition = new Position(0,1);
		Pawn pawn = new Pawn(pawnPosition, board, Color.BLACK)
		board.addPiece(pawn)

		when:
		Move promotionMove = pawn.getLegalMoves().getFistMove()
		board.executeMove(promotionMove)
		board.undoMove(promotionMove)
		
		then:
		board.getPiece(pawnPosition) instanceof Pawn
		board.getNumberOfPieces() == 1
	}
	
	@Unroll
	def "Test Undo Promotion with capture"()
	{
		given:
		Pawn whitePawn = new Pawn(whitePawnPosition, board, Color.WHITE)
		Rook whiteRook = new Rook(whiteRookPosition, board, Color.WHITE)
		Rook blackRook = new Rook(blackRookPosition, board, Color.BLACK)
		board.addPiece(whitePawn)
		board.addPiece(whiteRook)
		board.addPiece(blackRook)

		when:
		Move promotionMove = whitePawn.getLegalMoves().getFistMove()
		board.executeMove(promotionMove)
		board.undoMove(promotionMove)
				
		then:
		board.getPiece(whitePawnPosition) instanceof Pawn
		board.getPiece(whiteRookPosition).equals(whiteRook);
		board.getPiece(blackRookPosition).equals(blackRook);
		board.getNumberOfPieces() == 3
		
		where:
		whitePawnPosition  | whiteRookPosition      | blackRookPosition
		new Position(7, 6) | new Position(7, 7)     | new Position(6, 7)
		new Position(7, 6) | new Position(5, 7)     | new Position(6, 7)
		new Position(1, 6) | new Position(5, 7)     | new Position(0, 7)
	}

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
	def "Test Undoing en passant Moves WHITE"()
	{
		given: "Two pawns"
		Pawn whitePawn = new Pawn(whitePawnPositionOrigin, board, Color.WHITE)
		Pawn blackPawn = new Pawn(blackPawnPositionOrigin, board, Color.BLACK)
		board.addPiece(whitePawn)
		board.addPiece(blackPawn)
	
		Move move1 = new Move(blackPawnPositionOrigin, blackPawnPositionDestination);
		Move move2 = new Move(whitePawnPositionOrigin, blackPawnPositionDestination.deltaY(1));
		move2.setCapturedPiece(blackPawn);
	
		board.executeMove(move1)
		board.executeMove(move2)
		board.undoMove(move2)
		board.undoMove(move1)

		expect:
		board.getNumberOfPieces() == 2
		board.getPieces(Color.BLACK).get(0).getPosition().equals(blackPawnPositionOrigin)
		board.getPieces(Color.WHITE).get(0).getPosition().equals(whitePawnPositionOrigin)
		
		where:
		whitePawnPositionOrigin  | blackPawnPositionOrigin  | blackPawnPositionDestination  
		new Position(1, 4)       | new Position(0, 6)       | new Position(0, 4)            
		new Position(1, 4)       | new Position(2, 6)       | new Position(2, 4)            
	}
	
	@Unroll
	def "Test Undoing en passant Moves BLACK"()
	{
		given: "Two pawns"
		Pawn whitePawn = new Pawn(whitePawnPositionOrigin, board, Color.WHITE)
		Pawn blackPawn = new Pawn(blackPawnPositionOrigin, board, Color.BLACK)
		board.addPiece(whitePawn)
		board.addPiece(blackPawn)
	
		Move move1 = new Move(whitePawnPositionOrigin, whitePawnPositionDestination);
		Move move2 = new Move(blackPawnPositionOrigin, whitePawnPositionDestination.deltaY(-1));
		move2.setCapturedPiece(whitePawn);
	
		board.executeMove(move1)
		board.executeMove(move2)
		board.undoMove(move2)
		board.undoMove(move1)

		expect:
		board.getNumberOfPieces() == 2
		board.getPieces(Color.BLACK).get(0).getPosition().equals(blackPawnPositionOrigin)
		board.getPieces(Color.WHITE).get(0).getPosition().equals(whitePawnPositionOrigin)
		
		where:
		whitePawnPositionOrigin  | blackPawnPositionOrigin  | whitePawnPositionDestination
		new Position(0, 1)       | new Position(1, 3)       | new Position(0, 3)
		new Position(2, 1)       | new Position(1, 3)       | new Position(2, 3)
		new Position(2, 1)       | new Position(6, 3)       | new Position(2, 3)
	}
	
	def "Test undoing several pawn moves"()
	{
		when:
		Game game = new Game(false)
		Board board = game.getBoard()
		Move move1 = new Move(new Position(0,1),new Position(0,3));
		Move move2 = new Move(new Position(4,6),new Position(4,5));
		Move move3 = new Move(new Position(1,1),new Position(0,2));
		Move move4 = new Move(new Position(1,6),new Position(1,4));
		
		board.executeMove(move1)
		board.executeMove(move2)
		board.executeMove(move3)
		board.executeMove(move4)
		board.undoMove(move1)
		board.undoMove(move2)
		board.undoMove(move3)
		board.undoMove(move4)
		
		then:
		board.getNumberOfPieces() == 32;
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
	def "Test capture"()
	{
		given: "Two pawns"
		Pawn whitePawn = new Pawn(whitePawnPosition, board, Color.BLACK)
		Rook blackRook = new Rook(blackRookPosition, board, Color.WHITE)

		board.addPiece(whitePawn)
		board.addPiece(blackRook)

		expect:
		whitePawn.getLegalMoves().size() == legalMovePossibles

		where:
		whitePawnPosition  | blackRookPosition    | legalMovePossibles
		new Position(6, 1) | new Position(7, 0)   | 4+4
		new Position(7, 1) | new Position(7, 0)   | 0
	}
	
	def "Test Undo capture"()
	{
		given: "Two pawns"
		Pawn blackPawn = new Pawn(blackPawnPosition, board, Color.BLACK)
		Rook whiteRook = new Rook(whiteRookPosition, board, Color.WHITE)

		board.addPiece(blackPawn)
		board.addPiece(whiteRook)
		board.addPiece(new Rook(new Position(6,0), Color.BLACK))
		Move move = blackPawn.getLegalMoves().getList().get(0)
		board.executeMove(move)
		board.undoMove(move)
		
		expect:
		blackPawn.getLegalMoves().size() == legalMovePossibles

		where:
		blackPawnPosition  | whiteRookPosition    | legalMovePossibles
		new Position(6, 1) | new Position(7, 0)   | 4
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
	
	@Ignore
	def "Debug kiwipete"()
	{
		given: "Two pawns"
		//Game game = new Game(true,"k7/8/K7/8/8/5R1p/8/8 w - -")
		  Game game = new Game(true,"8/8/8/8/8/4R1p1/8/8 w - -")
		  
		expect:
		game.perft(4) == 672
	}
}