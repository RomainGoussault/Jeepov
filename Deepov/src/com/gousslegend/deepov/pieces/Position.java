package com.gousslegend.deepov.pieces;

public class Position
{
    private int x;
    private int y;
    
    public Position(int _x, int _y)
    {
	x = _x;
	y = _y;
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
}
