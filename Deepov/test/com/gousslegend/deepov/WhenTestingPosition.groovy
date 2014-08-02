package com.gousslegend.deepov
import static org.junit.Assert.assertEquals
import spock.lang.*

import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Position
import com.gousslegend.deepov.board.MapBoard;

class WhenTestingPosition extends spock.lang.Specification
{
	def "There should be 64 positions on the boad"()
	{
		expect:
		Position.getAllPositionOnBoard().size() == 64
	}
}