package com.gousslegend.deepov.pieces
import static org.junit.Assert.assertEquals

import org.junit.Test

import spock.lang.*

import com.gousslegend.deepov.Board
import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Position

class WhenTestingColor extends spock.lang.Specification
{
	def "Testing color"()
	{
		expect:
		color.getOppositeColor() == oppositeColor

		where:
		color | oppositeColor
		Color.WHITE | Color.BLACK
		Color.BLACK | Color.WHITE
	}
}