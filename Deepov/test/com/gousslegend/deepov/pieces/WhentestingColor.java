package com.gousslegend.deepov.pieces;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gousslegend.deepov.Board;
import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Position;

public class WhentestingColor
{

    @Test
    public void test()
    {
	Color black = Color.BLACK;
	assertEquals(Color.WHITE, black.getOppositeColor());
    }

    @Test
    public void test2()
    {
	Color black = Color.WHITE;
	assertEquals(Color.BLACK, black.getOppositeColor());
    }
}
