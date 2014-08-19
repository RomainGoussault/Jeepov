package com.gousslegend.deepov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.gousslegend.player.Deepov;

public class Uci
{
	private BufferedReader reader;
	private Deepov deepov;

	public Uci()
	{
		reader = new BufferedReader(new InputStreamReader(System.in));
		deepov = new Deepov();
		
		while (true)
		{
			String cmd = null;
			try
			{
				cmd = reader.readLine();
				if (cmd.equals("uci"))
				{
					start();
					break;
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
	}

	public void start() throws IOException
	{
		int movetime;
		int maxMoveTime;
		int searchDepth;
		int wtime = 0;
		int btime = 0;
		int winc = 0;
		int binc = 0;
		int togo = 0;
		String cmd;
		boolean infinite = false;

		System.out.println("id name Deepov");
		System.out.println("id author Romain Goussault");
		System.out.println("uciok");

		while (true)
		{
			cmd = reader.readLine();

			if (cmd.startsWith("quit"))
			{
				System.exit(0);
			}
			else if ("isready".equals(cmd))
			{
				System.out.println("readyok");
			}
			else if (cmd.startsWith("perft"))
			{
				cmd = cmd.substring(5);
				cmd = cmd.trim();
				int depth = Integer.parseInt(cmd.substring(0));
				//TODO launch perft
				//System.out.println(perft(depth));
			}
			else if (cmd.startsWith("divide"))
			{
				cmd = cmd.substring(6);
				cmd = cmd.trim();
				int depth = Integer.parseInt(cmd.substring(0));
				//TODO launch divide
				//System.out.println(divide(depth));
			}
			if (cmd.startsWith("position"))
			{
				//TODO Implement
				if (cmd.indexOf(("startpos")) != -1)
				{
					int mstart = cmd.indexOf("moves");
					if (mstart > -1)
					{
						String moves = cmd.substring(mstart + 5);
						deepov.resetBoard();
					}
				}
				else
				{
					int mstart = cmd.indexOf("moves");
					if (mstart > -1)
					{
						String fen = cmd.substring(cmd.indexOf("fen") + 4,
								mstart - 1);
						deepov.resetBoard(fen);
					}
					else
					{
						String fen = cmd.substring(cmd.indexOf("fen") + 4);
						deepov.resetBoard(fen);
					}
				}
			}
			else if (cmd.startsWith("go"))
			{
				movetime = 0;
				maxMoveTime = 0;
				searchDepth = 0;
				infinite = false;
				if (cmd.indexOf("depth") != -1)
				{
					int index = cmd.indexOf("depth");
					cmd = cmd.substring(index + 5);
					cmd = cmd.trim();
					searchDepth = Integer.parseInt(cmd.substring(0));
					movetime = 9999999;
					maxMoveTime = movetime;
				}
				else if (cmd.indexOf("movetime") != -1)
				{
					int index = cmd.indexOf("movetime");
					cmd = cmd.substring(index + 8);
					cmd = cmd.trim();
					movetime = Integer.parseInt(cmd.substring(0));
					maxMoveTime = movetime;
					searchDepth = 40;
				}
				else if (cmd.indexOf("infinite") != -1)
				{
					infinite = true;
					searchDepth = 40;
					movetime = 1000;
					maxMoveTime = movetime;
				}
				
				deepov.negaMaxRoot(3);
				Move bestMove = deepov.getBestmove();
				System.out.println("bestmove " + bestMove.toAlgebricNotation());
			}
			else if (cmd.equals("ucinewgame"))
			{
				deepov.resetBoard();
			}
		}
	}
}