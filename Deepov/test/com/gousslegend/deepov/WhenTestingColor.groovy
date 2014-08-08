package com.gousslegend.deepov
import static org.junit.Assert.assertEquals
import spock.lang.*

class WhenTestingColor extends spock.lang.Specification
{
	def "Testing opposite color"()
	{
		expect:
		color.getOppositeColor() == oppositeColor

		where:
		color | oppositeColor
		Color.WHITE | Color.BLACK
		Color.BLACK | Color.WHITE
	}
}