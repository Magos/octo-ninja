package octo_ninja.model;

import static org.junit.Assert.*;

import octo_ninja.model.Piece.Feature;

import org.junit.Test;

public class BoardTest {

	@Test
	public void testBoard() {
		assertNotNull(new Board());
	}

	@Test
	public void testPlacePiece() {
		Board board = new Board();
		Piece[] pieces = Piece.getPieceSet();
		StringBuffer buf = new StringBuffer();
		for(Piece.Feature feature : Piece.Feature.values()){
			buf.append(feature.toString());
			buf.append(" ");
		}
		System.out.println(buf.toString());
		for (int i = 1; 0 < pieces.length; i+=1) {
			buf.setLength(0);
			for(Piece.Feature feature : Piece.Feature.values()){
				buf.append(pieces[i].possessesFeature(feature));
				buf.append(" ");
			}
			buf.append(pieces[i].toString());
			System.out.println(buf.toString());
		}
	}

}
