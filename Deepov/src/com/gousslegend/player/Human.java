package com.gousslegend.player;

import java.util.Scanner;

import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.Position;

public class Human implements Player
{
	private String myName = "Human";

	public Human(String name)
	{
		setName(name);
	}

	@Override
	public String toString()
	{
		return "Human " + myName;
	}

	@Override
	public Move takeTurn()
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

	public String getName()
	{
		return myName;
	}

	public void setName(String myName)
	{
		this.myName = myName;
	}



}
