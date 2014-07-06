package com.gousslegend.deepov;

import java.util.List;
import java.util.Vector;

public class MoveList
{

	private List<Move> myList;

	public MoveList()
	{
		myList = new Vector<Move>();
	}

	public void addMove(Move move)
	{
		myList.add(move);
	}
}
