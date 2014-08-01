package com.gousslegend.deepov
import static org.junit.Assert.assertEquals
import com.gousslegend.deepov.pieces.Piece
import spock.lang.*

class WhenTestingPerft extends spock.lang.Specification
{
	def "Perft12"()
	{
		given:
		Game game = new Game()
		
		expect:
		game.perft(depth) == nodes;
		
		where:
		depth  |  nodes
		0 	   | 1
		1 	   | 20
		2 	   | 400
		3 	   | 8902
		4 	   | 197281
		//5 	   | 4865609
	}
}