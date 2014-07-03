
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gousslegend.deepov.Board;
import com.gousslegend.deepov.Position;
import com.gousslegend.deepov.pieces.Rook;

public class WhenTestingRookMovement
{
    @Test
    public void testRookMoves()
    {
	Board board = new Board();
	Position position = new Position(0, 0);
	
	Rook rook = new Rook(position, board, true);
	assertEquals(rook.getLegalMoves().size(), 14);
    }
}
