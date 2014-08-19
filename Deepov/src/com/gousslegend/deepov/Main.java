package com.gousslegend.deepov;

import java.io.IOException;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		printInfo();
		Uci uci = new Uci();
		uci.start();
	}
	
	private static void printInfo()
	{
		System.out.println("######## Deepov ########");
		System.out.println("######## Version 0.1 ########");
		System.out.println("Source available: https://github.com/RomainGoussault/Deepov");
		System.out.println("");
	}
}
