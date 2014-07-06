package com.gousslegend.deepov.pieces
import static org.junit.Assert.assertEquals

import org.junit.Test

import spock.lang.*

import com.gousslegend.deepov.Board
import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Move
import com.gousslegend.deepov.Position

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
		board.move(move);
		def list = board.getPieces(Color.BLACK)
		
		expect:
		list.get(0).getPosition() ==  destination
		board.get
	}
}