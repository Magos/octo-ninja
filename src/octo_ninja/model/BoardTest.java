package octo_ninja.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Set;

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
				Set<Piece> horizontalsPieces = Piece.getPieceSet();
				Board verticalsBoard = new Board();
				Set<Piece> verticalsPieces = Piece.getPieceSet();
				for (int i = 1; i < 4; i++) {
					Piece piece = getPiece(horizontalsPieces, feature);
					boolean result = horizontalsBoard.placePiece(piece, i, j);
					assertFalse(result);
					assertFalse(horizontalsBoard.isWon());
					piece = getPiece(verticalsPieces, feature);
					result = verticalsBoard.placePiece(piece, j, i);
					assertFalse(result);
					assertFalse(verticalsBoard.isWon());

				}
				Piece piece = getPiece(horizontalsPieces, feature);
				boolean result = horizontalsBoard.placePiece(piece, 4, j);
				assertTrue(result);
				assertTrue(horizontalsBoard.isWon());
				piece = getPiece(verticalsPieces, feature);
				result = verticalsBoard.placePiece(piece, j, 4);
				assertTrue(result);
				assertTrue(verticalsBoard.isWon());
			}
			//Test diagonals.
			Board botLeft = new Board();
			Set<Piece> botLeftPieces = Piece.getPieceSet();
			Board topLeft = new Board();
			Set<Piece> topLeftPieces = Piece.getPieceSet();
			for (int i = 1; i < 4; i++) {
				Piece piece = getPiece(botLeftPieces, feature);
				boolean result = botLeft.placePiece(piece, i, i);
				assertFalse(result);
				assertFalse(botLeft.isWon());
				
				piece = getPiece(topLeftPieces,feature);
				result = topLeft.placePiece(piece, i, 5-i);
				assertFalse(result);
				assertFalse(topLeft.isWon());
			}
			
			Piece piece = getPiece(botLeftPieces, feature);
			boolean result = botLeft.placePiece(piece, 4, 4);
			assertTrue(result);
			assertTrue(botLeft.isWon());
			piece = getPiece(topLeftPieces, feature);
			result = topLeft.placePiece(piece, 4, 1);
			assertTrue(result);
			assertTrue(topLeft.isWon());
		}
	}

	private Piece getPiece(Set<Piece> horizontalsPieces, Feature feature) {
		Piece ret = null;
		for (Iterator<Piece> iterator = horizontalsPieces.iterator(); iterator.hasNext();) {
			Piece piece = (Piece) iterator.next();
			if(piece.possessesFeature(feature)){
				ret = piece;
				iterator.remove();
				break;
			}
		}
		return ret;
	}
}
