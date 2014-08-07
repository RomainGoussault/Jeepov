package com.gousslegend.deepov
import static org.junit.Assert.assertEquals
import spock.lang.*

class WhenTestingPerft extends spock.lang.Specification
{
	@Ignore
	def "Perft"()
	{
		given:
		Game game = new Game(false, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
		def data = [nodes, captures, castles, promotions] as int[]
		
		expect:
		game.perftWithData(depth) == data
		
		where:
		depth  | nodes          | captures  | castles | promotions
//		0 	   | 1				| 0		    | 0		   | 0
		1 	   | 20				| 0			| 0		   | 0
		2 	   | 400			| 0			| 0		   | 0
		3 	   | 8902			| 34		| 0		   | 0
		4 	   | 197281			| 1576		| 0		   | 0
		5 	   | 4865609		| 82719		| 0		   | 0
		6	   | 119060324		| 2812008	| 0		   | 0
	//	7      | 3195901860		| 0
	}
	
	def "Perft Kiwipete"()
	{
		given:
		String fen = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq ";
		Game game = new Game(false, fen);
		def data = [nodes, captures, castles, promotions] as int[]
		
		expect:
		game.perftWithData(depth) == data
		
		where:
		depth  | nodes          | captures  | castles  | promotions
		1 	   | 48				| 8			| 2		   | 0
	//	2 	   | 2039			| 351		| 91    	 | 0
	}
}