package com.gousslegend.player;

import java.util.List;
import java.util.Scanner;

import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.board.Board;

public class Human extends Player
{
	public Human(String name, Board board)
	{
		super(name, board);
	}

	@Override
	public String toString()
	{
		return "Human " + myName;
	}

	public Move takeTurn()
	{
		Move move = null;
		List<Move> moves = myBoard.getLegalMoves().getList();

		while(true)
		{
			move = getMoveFromStdIn();
			
			for(Move legalmove : moves)
			{
				if(move.partialEquals(legalmove))
				{
					return legalmove;
				}
			}
			
				System.out.println("This move is not legal. Enter a new move");
				System.out.println("");
				System.out.println(myBoard);
		}

	}
	
	public Move getMoveFromStdIn()
	{
		Scanner sc = new Scanner(System.in);

		System.out.println("Origin?");
		int ox = sc.nextInt();
		int oy = sc.nextInt();

		System.out.println("Destination?");
		int dx = sc.nextInt();
		int dy = sc.nextInt();

		Position origin = new Position(ox, oy);
		Position destination = new Position(dx, dy);

		Move move = new Move(origin, destination);
		System.out.println(move);
		
		return move;
	}
	
	
}
