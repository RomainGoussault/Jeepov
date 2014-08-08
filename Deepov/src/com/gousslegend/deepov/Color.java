package com.gousslegend.deepov;

public enum Color
{
    WHITE, BLACK;

    private Color opposite;

    static
    {
	WHITE.opposite = BLACK;
	BLACK.opposite = WHITE;
    }

    public Color getOppositeColor()
    {
	return opposite;
    }
}
