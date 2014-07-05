package com.gousslegend.deepov.pieces;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gousslegend.deepov.Color;

public class WhentestingColor
{

    @Test
    public void test()
    {
	Color black = Color.BLACK;
	assertEquals(Color.WHITE, black.getOppositeColor());
    }
}
