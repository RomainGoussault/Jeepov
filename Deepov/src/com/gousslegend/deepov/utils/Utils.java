package com.gousslegend.deepov.utils;


import java.util.ArrayList;
import java.util.List;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.board.Board;
import com.gousslegend.deepov.board.MapBoard;
import com.gousslegend.deepov.pieces.Bishop;
import com.gousslegend.deepov.pieces.King;
import com.gousslegend.deepov.pieces.Knight;
import com.gousslegend.deepov.pieces.Pawn;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.deepov.pieces.Queen;
import com.gousslegend.deepov.pieces.Rook;

public class Utils
{
	public static Board readFen(String fen)
	{
		MapBoard board = new MapBoard();
		String[] spaceSplit = fen.split(" ");
		String[] piecesByRank = spaceSplit[0].split("/");
		
		int rank = 7;
		for(String pieces : piecesByRank)
		{
			board.addPieces(getPieces(pieces, rank));
			rank--;
		}
		
		return board;
	}
	
	private static List<Piece> getPieces(String piecesString, int rank)
	{
		List<Piece> pieces = new ArrayList<>();
		int x = -1;
		Color color;
		Piece piece = null;
		
		char[] piecesChar = piecesString.toCharArray();
		for(char pieceChar : piecesChar)
		{
			if(Character.isDigit(pieceChar))
			{
				x = pieceChar;
			}
			else
			{
				x++;
				color = Character.isLowerCase(pieceChar) ?  Color.BLACK : Color.WHITE;
				pieceChar = Character.toLowerCase(pieceChar);
				
				if(pieceChar == 'k')
				{
					piece = new King(new Position(x,rank), color);
				}
				else if(pieceChar == 'q')
				{
					piece = new Queen(new Position(x,rank), color);
				}
				else if(pieceChar == 'r')
				{
					piece = new Rook(new Position(x,rank), color);
				}
				else if(pieceChar == 'b')
				{
					piece = new Bishop(new Position(x,rank), color);
				}
				else if(pieceChar == 'n')
				{
					piece = new Knight(new Position(x,rank), color);
				}
				else if(pieceChar == 'p')
				{
					piece = new Pawn(new Position(x,rank), color);
				}
				
				pieces.add(piece);
			}
		}
		
		return pieces;
	}

	public static void main(String[] args)
	{
		String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
		Board board = Utils.readFen(fen);
		System.out.println(board);
		
	}
}
