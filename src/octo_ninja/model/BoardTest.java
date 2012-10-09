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
		for(Piece.Feature feature : Piece.Feature.values()){
			//Test horizontal and vertical rows in isolation.
			for(int j = 1; j <= 4; j++){
				Board horizontalsBoard = new Board();
				Piece[] horizontalsPieces = Piece.getPieceSet();
				Board verticalsBoard = new Board();
				Piece[] verticalsPieces = Piece.getPieceSet();
				for (int i = 1; i < 4; i++) {
					Piece piece = getPiece(horizontalsPieces, feature);
					boolean result = horizontalsBoard.placePiece(piece, i, j);
					assertFalse(result);
					piece = getPiece(verticalsPieces, feature);
					result = verticalsBoard.placePiece(piece, j, i);
					assertFalse(result);
					
				}
				Piece piece = getPiece(horizontalsPieces, feature);
				boolean result = horizontalsBoard.placePiece(piece, 4, j);
				assertTrue(result);
			}
		}
	}

	private Piece getPiece(Piece[] pieces, Feature feature) {
		Piece ret = null;
		for (int i = 0; i < pieces.length; i++) {
			if(pieces[i] != null && pieces[i].possessesFeature(feature)){
				ret = pieces[i];
				pieces[i] = null;
				break;
			}
		}
		return ret;
	}
}
