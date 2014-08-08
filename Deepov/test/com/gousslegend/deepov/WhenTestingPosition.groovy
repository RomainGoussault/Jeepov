package com.gousslegend.deepov
import static org.junit.Assert.assertEquals
import spock.lang.*

class WhenTestingPosition extends spock.lang.Specification
{
	def "There should be 64 positions on the boad"()
	{
		expect:
		Position.getAllPositionOnBoard().size() == 64
	}
}