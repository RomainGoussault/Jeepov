package com.gousslegend.deepov
import static org.junit.Assert.assertEquals

import org.junit.Test

import spock.lang.*

import com.gousslegend.deepov.Board
import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Move
import com.gousslegend.deepov.Position
import com.gousslegend.deepov.pieces.Rook;

class WhenTestingBoard extends spock.lang.Specification
{
	def "Moving a rook"()
	{
		given:
		Board board = new Board()
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
		Board board = new Board()
		def origin = new Position(0,0);
		def destination = new Position(3,0);
		board.addPiece(new Rook(origin, board, Color.BLACK))
		
		def move = new Move(origin, destination)
		board.executeMove(move);
		board.undoMove(move);
		def list = board.getPieces(Color.BLACK)
		
		expect:
		list.get(0).getPosition() ==  origin
		board.getMoves().size() == 0
		board.getLastMove() == null
	}
	
	def "Moving with capture"()
	{
		given:
		Board board = new Board()
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
		Board board = new Board()
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
}