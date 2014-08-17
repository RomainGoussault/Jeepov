package com.gousslegend.player;

import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.board.Board;

public abstract class Player
{
	protected String myName = "Human";
	protected Board myBoard;
	
	public Player(String name, Board board)
	{
		myName = name;
		myBoard = board;
	}

	public Player(Board board)
	{
		myBoard = board;
	}

	public abstract Move takeTurn();
	
	public String getName()
	{
		return myName;
	}

	public void setName(String myName)
	{
		this.myName = myName;
	}
}