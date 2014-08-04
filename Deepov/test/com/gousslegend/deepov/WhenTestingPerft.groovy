package com.gousslegend.deepov
import static org.junit.Assert.assertEquals
import spock.lang.*

import com.gousslegend.deepov.board.Board

class WhenTestingPerft extends spock.lang.Specification
{
	@Unroll
	def "Perft"()
	{
		given:
		Game game = new Game(false)
		
		expect:
		game.perft(depth) == nodes;
		
		where:
		depth  |  nodes
		0 	   | 1
		1 	   | 20
		2 	   | 400
		3 	   | 8902
		4 	   | 197281
		5 	   | 4865609
	//	6      | 119060324
	//	7      | 3195901860
	}
}