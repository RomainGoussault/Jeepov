package com.gousslegend.deepov;

import java.util.ArrayList;
import java.util.List;

import com.gousslegend.deepov.board.Board;

public class Position
{
	private byte x;
	private byte y;

	public Position()
	{
		x = 0;
		y = 0;
	}

	public Position(byte _x, byte _y)
	{
		x = _x;
		y = _y;
	}
	
	public Position(int x, byte y)
	{
		this((byte) x , y);
	}

	public Position(byte x, int y)
	{
		this(x , (byte) y);
	}

	public Position(int x, int y)
	{
		this((byte) x , (byte) y);
	}

	public static List<Position> getAllPositionOnBoard()
	{
		List<Position> positions= new ArrayList<>();
		
		for(byte i = 0; i<= Board.BOARD_SIZE; i++)
		{
			for(byte j = 0; j<= Board.BOARD_SIZE; j++)
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

	public Position deltaXY(byte delta)
	{
		return deltaXY(delta, delta);
	}
	
	public byte getX()
	{
		return x;
	}

	public void setX(byte x)
	{
		this.x = x;
	}

	public byte getY()
	{
		return y;
	}

	public void setY(byte y)
	{
		this.y = y;
	}

	@Override
	public String toString()
	{
		return "[" + x + ", " + y + "]";
	}

	@Override
	public int hashCode()
	{
		final byte prime = 31;
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

	public String toShortString()
	{
		String[] letters = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};
		int yy = y + 1;
		
		return letters[x] + yy;
	}
}