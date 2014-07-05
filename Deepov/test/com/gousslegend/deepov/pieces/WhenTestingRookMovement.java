package com.gousslegend.deepov.pieces;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gousslegend.deepov.Board;
import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Position;

public class WhenTestingRookMovement
{
    @Test
    public void testAloneRookMoves()
    {
	Board board = new Board();
	Position position = new Position(0, 0);
	
	Rook rook = new Rook(position, board, Color.BLACK);
	board.addPiece(rook);
	
	assertEquals(rook.getLegalMoves().size(), 14);
    }
    
    @Test
    public void testBlockedRookWithCapture()
    {
	Board board = new Board();
	Position position = new Position(0, 0);
	
	Rook rook = new Rook(position, board, Color.BLACK);
	Pawn pawn1 = new Pawn(new Position(1, 0), board, Color.WHITE);
	Pawn pawn2 = new Pawn(new Position(0, 1), board, Color.WHITE);
	
	board.addPiece(rook);
	board.addPiece(pawn1);
	board.addPiece(pawn2);
	
	assertEquals(rook.getLegalMoves().size(), 2);
    }
    
}
