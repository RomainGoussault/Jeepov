package com.gousslegend.player;

import com.gousslegend.deepov.Move;
import com.gousslegend.deepov.board.Board;

public abstract class Player
{
	protected String myName = "Human";
	
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
