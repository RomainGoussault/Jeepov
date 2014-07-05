package com.gousslegend.deepov.pieces;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gousslegend.deepov.Board;
import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Position;

public class WhenTestingCheck
{

    @Test
    public void testKingPosition()
    {
	Board board = new Board();
	Position BackKingPosition = new Position(0, 0);
	Position WhiteKingPosition = new Position(2, 2);

	King BackKing = new King(BackKingPosition, board, Color.BLACK);
	King WhiteKing = new King(WhiteKingPosition,board, Color.WHITE);

	board.addPiece(BackKing);
	board.addPiece(WhiteKing);

	assertEquals(board.getKing(Color.BLACK).getMyPosition(), BackKingPosition);
	assertEquals(board.getKing(Color.WHITE).getMyPosition(), WhiteKingPosition);
    }
}
