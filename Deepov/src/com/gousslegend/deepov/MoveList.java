package com.gousslegend.deepov;

import java.util.List;
import java.util.Vector;

public class MoveList
{

	private List<Move> myList;
	private Board myBoard;

	public MoveList(Board board)
	{
		myList = new Vector<Move>();
		myBoard = board;
	}
	
	public MoveList()
	{
		myList = new Vector<Move>();
	}

	public void addMove(Move move)
	{
		myList.add(move);
	}

	public Board getBoard()
	{
		return myBoard;
	}

	public void setBoard(Board myBoard)
	{
		this.myBoard = myBoard;
	}
	
	public List<Move> getMyList()
	{
		return myList;
	}
	
	public void setMyList(List<Move> myList)
	{
		this.myList = myList;
	}

	public boolean add(Move move)
	{
		return myList.add(move);
	}

	public int size()
	{
		return myList.size();
	}

	@Override
	public String toString()
	{
		return "MoveList [myList=" + myList + "]";
	}
}
