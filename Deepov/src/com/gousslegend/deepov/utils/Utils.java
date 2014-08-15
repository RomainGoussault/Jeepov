package com.gousslegend.deepov.utils;

import java.util.ArrayList;
import java.util.List;

import com.gousslegend.deepov.Color;
import com.gousslegend.deepov.Game;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.board.ArrayBoard;
import com.gousslegend.deepov.board.Board;
import com.gousslegend.deepov.pieces.Bishop;
import com.gousslegend.deepov.pieces.King;
import com.gousslegend.deepov.pieces.Knight;
import com.gousslegend.deepov.pieces.Pawn;
import com.gousslegend.deepov.pieces.Piece;
import com.gousslegend.deepov.pieces.Queen;
import com.gousslegend.deepov.pieces.Rook;

public class Utils
{
	public static Board getBoardFromFen(String fen)
	{
		Board board = new ArrayBoard();
		String[] spaceSplit = fen.split(" ");
		String[] piecesByRank = spaceSplit[0].split("/");
		String colorToplay = spaceSplit[1];
		String castlingAvailability = spaceSplit[2];
		String enPassantTarget = spaceSplit[3];
		String halfMoveNumber = spaceSplit[4];
		String MoveNumber = spaceSplit[5];

		int rank = 7;
		for (String pieces : piecesByRank)
		{
			board.addPieces(getPieces(pieces, rank));
			rank--;
		}

		Color color = colorToplay.equalsIgnoreCase("w") ? Color.WHITE
				: Color.BLACK;
		board.setColorToPlay(color);

		return board;
	}

	public static Color getColorToPLay(String fen)
	{
		String[] spaceSplit = fen.split(" ");
		String colorToplay = spaceSplit[1];

		return colorToplay.equalsIgnoreCase("w") ? Color.WHITE
				: Color.BLACK;

	}
	
	public static String generateFen(Board board)
	{
		String fen = "";
		Position position = null;

		for (int j = 7; j >= 0; j--)
		{
			int x = 0;
			for (int i = 0; i < 8; i++)
			{
				position = new Position(i, j);
				boolean positionFree = board.isPositionFree(position);
				if (positionFree)
				{
					x++;
				}
				else
				{
					if (x != 0)
					{
						fen += x;
					}

					// add the piece
					String c = board.getPiece(position).getChar();
					fen += c;
					x = 0;
				}

			}
			if (x != 0)
			{
				fen += x;
			}
			if (j != 0)
			{
				fen += "/";
			}

		}

		return fen;
	}

	public static List<Piece> getPiecesFromFen(String fen)
	{
		List<Piece> pieces = new ArrayList<>();
		String[] spaceSplit = fen.split(" ");
		String[] piecesByRank = spaceSplit[0].split("/");

		int rank = 7;
		for (String piecesRank : piecesByRank)
		{
			pieces.addAll(getPieces(piecesRank, rank));
			rank--;
		}

		return pieces;
	}

	public static List<Piece> getPieces(String piecesString, int rank)
	{
		List<Piece> pieces = new ArrayList<>();
		int x = -1;
		Color color;
		Piece piece = null;

		char[] piecesChar = piecesString.toCharArray();
		for (char pieceChar : piecesChar)
		{
			if (Character.isDigit(pieceChar))
			{
				x += Character.getNumericValue(pieceChar);
			}
			else
			{
				x++;
				color = Character.isLowerCase(pieceChar) ? Color.BLACK
						: Color.WHITE;
				pieceChar = Character.toLowerCase(pieceChar);

				if (pieceChar == 'k')
				{
					piece = new King(new Position(x, rank), color);
				}
				else if (pieceChar == 'q')
				{
					piece = new Queen(new Position(x, rank), color);
				}
				else if (pieceChar == 'r')
				{
					piece = new Rook(new Position(x, rank), color);
				}
				else if (pieceChar == 'b')
				{
					piece = new Bishop(new Position(x, rank), color);
				}
				else if (pieceChar == 'n')
				{
					piece = new Knight(new Position(x, rank), color);
				}
				else if (pieceChar == 'p')
				{
					piece = new Pawn(new Position(x, rank), color);
				}

				pieces.add(piece);
			}
		}

		return pieces;
	}

	public static void main(String[] args)
	{
		/*
		 * //String fen =
		 * "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
		 * 
		 * Board board = Utils.getBoardFromFen(fen);
		 */

		String fen = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R";
		Game game = new Game(true, fen);
		Board board = game.getBoard();
		String fen2 = generateFen(board);

		System.out.println(fen);
		System.out.println(fen2);
		System.out.println(fen2.equals(fen));
	}
}