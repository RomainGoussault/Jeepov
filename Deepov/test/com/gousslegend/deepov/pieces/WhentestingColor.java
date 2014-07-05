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

    @Test
    public void test3()
    {
	Board board = new Board();
	Position position = new Position(0, 0);

	Rook rook = new Rook(position, board, Color.BLACK);
	Pawn pawn1 = new Pawn(new Position(1, 0), board, Color.WHITE);
	Pawn pawn2 = new Pawn(new Position(0, 1), board, Color.WHITE);

	board.addPiece(rook);
	board.addPiece(pawn1);
	board.addPiece(pawn2);

	assertEquals(board.getEnnemiesPieces(Color.WHITE),rook);
    }

}
