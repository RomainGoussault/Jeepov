package com.gousslegend.deepov.pieces
import static org.junit.Assert.assertEquals
import spock.lang.*

import com.gousslegend.deepov.Board
import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Position

class WhenTestingPosition extends spock.lang.Specification
{
	def "There should be 64 positions on the boad"()
	{
		expect:
		Position.getAllPositionOnBoard().size() == 64
	}
}