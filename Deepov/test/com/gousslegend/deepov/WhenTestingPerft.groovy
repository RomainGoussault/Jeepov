package com.gousslegend.deepov
import static org.junit.Assert.assertEquals
import spock.lang.*

class WhenTestingPerft extends spock.lang.Specification
{
	//@Ignore //WORKING
	@Unroll
	def "Perft"()
	{
		given:
		Game game = new Game(false, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
		def data = [nodes, captures, castles, promotions, enPassant, checks, checkmates] as int[]
		
		expect:
		game.perftWithDataCheck(depth) == data
		
		where:
		depth  | nodes          | captures  | castles  | promotions  | enPassant | checks      | checkmates
		1 	   | 20				| 0			| 0		   | 0           | 0         | 0           | 0
		2 	   | 400			| 0			| 0		   | 0           | 0         | 0           | 0
		3 	   | 8902			| 34		| 0		   | 0           | 0         | 12          | 0
		4 	   | 197281			| 1576		| 0		   | 0           | 0         | 469         | 8
	//	5 	   | 4865609		| 82719		| 0		   | 0           | 258       | 27351       | 347
	//	6	   | 119060324		| 2812008	| 0		   | 0           | 0         | 0    	   | 0
	//	7      | 3195901860		| 0			| 0		   | 0           | 0         | 0           | 0
	}
	
	@Unroll
//	@Ignore
	def "Perft Kiwipete"()
	{
		given:
		String fen = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -";
		Game game = new Game(false, fen);
		def data = [nodes, captures, castles, promotions, enPassant] as int[]
		
		expect:
		game.perftWithData(depth) == data
		
		where:
		depth  | nodes          | captures  | castles  | promotions | enPassant
		1 	   | 48				| 8			| 2		   | 0          | 0
		2 	   | 2039			| 351		| 91       | 0          | 1
		3 	   | 97862			| 17102		| 3162     | 0          | 45
	//	4 	   | 4085603		| 757163	| 128013   | 15172      | 1929
	//	5 	   | 193690690		| 35043416	| 4993637  | 8392       | 73365
	}
	
	//@Ignore //WORKING
	@Unroll
	def "Perft Position 3 "()
	{
		given:
		String fen = "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -";
		Game game = new Game(false, fen);
		def data = [nodes, captures, castles, promotions, enPassant] as int[]
		
		expect:
		game.perftWithData(depth) == data
		
		where:
		depth  | nodes          | captures  | castles  | promotions | enPassant
		1 	   | 14				| 1		    | 0		   | 0          | 0
		2 	   | 191			| 14		| 0		   | 0          | 0
		3 	   | 2812			| 209		| 0 	   | 0          | 2
		4 	   | 43238			| 3348		| 0 	   | 0          | 123
		5 	   | 674624			| 52051		| 0	       | 0          | 1165
	}
	
	//@Ignore //WORKING
	def "Perft Position 4"()
	{
		given:
		String fen = "r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1";
		Game game = new Game(false, fen);
		def data = [nodes, captures, castles, promotions, enPassant] as int[]
		
		expect:
		game.perftWithData(depth) == data
		
		where:
		depth  | nodes          | captures  | castles  | promotions  | enPassant
		1 	   | 6				| 0			| 0		   | 0  		 | 0
		2 	   | 264			| 87		| 6		   | 48  		 | 0
		3 	   | 9467			| 1021		| 0		   | 120  		 | 4
		4 	   | 422333			| 131393    | 7795	   | 60032  	 | 0
	//	3 	   | 9467
	//	4      | 1761505
	//	5      | 70202861
	}
	
//	@Ignore //WORKING
	def "Perft 5"()
	{
		given:
		String fen = "rnbqkb1r/pp1p1ppp/2p5/4P3/2B5/8/PPP1NnPP/RNBQK2R w KQkq - 0 6";
		Game game = new Game(false, fen);
		
		expect:
		game.perft(depth) == nodes
		
		where:
		depth  | nodes 
		1 	   | 42	
		2 	   | 1352
		3 	   | 53392
		4      | 1761505
	//	5      | 70202861
	}
	
//	@Ignore //WORKING
	def "Perft 6"()
	{
		given:
		String fen = "r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10";
		Game game = new Game(false, fen);
		
		expect:
		game.perft(depth) == nodes
		
		where:
		depth  | nodes
		1 	   | 46
		2 	   | 2079
		3      | 89890
		4      | 3894594
	}
}