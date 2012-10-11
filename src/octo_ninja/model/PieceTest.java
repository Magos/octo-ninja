package octo_ninja.model;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class PieceTest {


	@Test
	public void testPieceFromString() {
		Set<Piece> pieces = Piece.getPieceSet();
		for (Piece piece : pieces) {
			Piece other = Piece.PieceFromString(piece.toString());
			assertEquals(piece,other);
		}
	}
	
	@Test
	public void testClone(){
		Set<Piece> pieces = Piece.getPieceSet();
		for (Piece piece : pieces) {
			Piece other = piece.clone();
			assertEquals(piece,other);
		}
	}

}
