package com.gousslegend.deepov
import static org.junit.Assert.assertEquals
import spock.lang.*

import com.gousslegend.deepov.board.ArrayBoard
import com.gousslegend.deepov.board.Board
import com.gousslegend.deepov.pieces.Rook

class WhenTestingBoard extends spock.lang.Specification
{
	def "Moving a rook"()
	{
		given:
		ArrayBoard board = new ArrayBoard()
		def origin = new Position(0,0);
		def destination = new Position(3,0);
		board.addPiece(new Rook(origin, board, Color.BLACK))
		
		def move = new Move(origin, destination)
		board.executeMove(move);
		def list = board.getPieces(Color.BLACK)
		
		expect:
		list.get(0).getPosition() ==  destination
		board.getLastMove() == move
	}
	
	def "Undoing a move"()
	{
		given:
		ArrayBoard board = new ArrayBoard()
		def origin = new Position(0,0);
		def destination = new Position(3,0);
		board.addPiece(new Rook(origin, board, Color.BLACK))
		
		def move = new Move(origin, destination)
		board.executeMove(move);
		board.undoMove(move);
		def list = board.getPieces(Color.BLACK)
		
		expect:
		list.get(0).getPosition() ==  origin
		board.getMovesPlayed().size() == 0
		board.getLastMove() == null
	}
	
	def "Moving with capture"()
	{
		given:
		ArrayBoard board = new ArrayBoard()
		def origin = new Position(0,0);
		def destination = new Position(3,0);
		board.addPiece(new Rook(origin, board, Color.BLACK))
		board.addPiece(new Rook(destination, board, Color.WHITE))
		
		def move = new Move(origin, destination)
		move.setCapturedPiece(board.getPiece(destination))
		board.executeMove(move);
		def list = board.getPieces(Color.BLACK)
		
		expect:
		list.size()==1;
	}
	
	def "Undoing a Move with capture"()
	{
		given:
		ArrayBoard board = new ArrayBoard()
		def origin = new Position(0,0);
		def destination = new Position(3,0);
		def blackRook = new Rook(origin, board, Color.BLACK)
		def whiteRook = new Rook(destination, board, Color.WHITE)
		board.addPiece(blackRook)
		board.addPiece(whiteRook)
		
		def move = new Move(origin, destination)
		move.setCapturedPiece(whiteRook)
		
		board.executeMove(move);
		board.undoMove(move);
		def list = board.getPieces()
		
		expect:
		list.size()==2;
	}
	
	def "Test pinned by Rook sliding pieces"()
	{
		given:
		Game game = new Game(false,"8/8/5k2/8/8/3P4/3K1N1q/8 w - - 0 1")
		Board board = game.getBoard()
		
		expect:
		board.getPinnedPieces(Color.WHITE).size() == 1
	}
	
	def "Test pinned by Bishop sliding pieces"()
	{
		given:
		Game game = new Game(false,"8/k1K5/3N4/4q3/8/8/8/8 w - - 0 1")
		Board board = game.getBoard()
		
		expect:
		board.getPinnedPieces(Color.WHITE).size() == 1
	}
	
	def "Test 5 pins!"()
	{
		given:
		Game game = new Game(false,"b6k/1P6/8/3K1B1r/2RNN3/5q2/b7/3r4 w - - 0 1")
		Board board = game.getBoard()
		
		expect:
		board.getPinnedPieces(Color.WHITE).size() == 5
		board.getPinnedPieces(Color.BLACK).size() == 0
	}
	
	def "Test No pins!"()
	{
		given:
		Game game = new Game(false,"8/6k1/6r1/3b4/8/3P2N1/3K4/8 w - - 0 1")
		Board board = game.getBoard()
		
		expect:
		board.getPinnedPieces(Color.WHITE).size() == 0
		board.getPinnedPieces(Color.BLACK).size() == 0
	}
	
	def "Test pinned by Rook sliding pieces2"()
	{
		given:
		Game game = new Game(false,"8/6k1/8/3r4/8/8/3P4/3K4 w - - 0 1")
		Board board = game.getBoard()
		MoveList movelist = board.getLegalMoves(Color.WHITE)
		
		expect:
		movelist.size() == 6
	}
	
	def "Test is checkmate"()
	{
		given:
		Game game = new Game(false,fen)
		Board board = game.getBoard()
		
		expect:
		board.isCheckmate(Color.BLACK) == checkMate
		
		where:
		fen 	 							| checkMate
	//	"rnbq1b1r/ppp2ppp/1np4k/4p1Q1/3P4/8/PPP1PPPP/RNB1KBNR w KQkq - 0 1"  | true
		"8/8/8/8/8/8/3R4/3R3k w - - 0 1)"  | true
		"8/8/8/8/8/3R4/8/3R3k w - - 0 1"  | false
		"8/3R4/8/8/8/8/3R2Qk/8 w - - 0 1"  | true
		"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"  | false
		"8/8/6B1/8/8/8/2Q5/2k5 w - - 0 1"  | true
	}
}