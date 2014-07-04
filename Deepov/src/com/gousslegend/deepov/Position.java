package com.gousslegend.deepov;

public class Position
{
    private int x;
    private int y;
    
    public Position(int _x, int _y)
    {
	x = _x;
	y = _y;
    }
    
    public Position deltaX(int delta)
    {
	return new Position(x + delta, y);
    }
    
    public Position deltaY(int delta)
    {
	return new Position(x, y + delta);
    }
    
    public Position Position(int deltaX, int deltaY)
    {
	return new Position(x + deltaX, y + deltaY);
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
	return "Position [x=" + x + ", y=" + y + "]";
    }
}
