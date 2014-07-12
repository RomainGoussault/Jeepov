package com.gousslegend.deepov;

import java.util.ArrayList;
import java.util.List;

public class Position
{
	private int x;
	private int y;

	public Position()
	{
		x = 0;
		y = 0;
	}

	public Position(int _x, int _y)
	{
		x = _x;
		y = _y;
	}
	
	public static List<Position> getAllPositionOnBoard()
	{
		List<Position> positions= new ArrayList<>();
		
		for(int i = 0; i<= Board.BOARD_SIZE; i++)
		{
			for(int j = 0; j<= Board.BOARD_SIZE; j++)
			{
				positions.add(new Position(i,j));
			}
		}
		
		return positions;
	}
	

	public Position deltaX(int delta)
	{
		return new Position(x + delta, y);
	}

	public Position deltaY(int delta)
	{
		return new Position(x, y + delta);
	}

	public Position deltaXY(int deltaX, int deltaY)
	{
		return new Position(x + deltaX, y + deltaY);
	}

	public Position deltaXY(int delta)
	{
		return deltaXY(delta, delta);
	}
	
	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	@Override
	public String toString()
	{
		return "[x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		Position otherPosition = (Position) obj;

		if (x == otherPosition.getX() && y == otherPosition.getY())
		{
			return true;
		}
		return false;
	}
}
