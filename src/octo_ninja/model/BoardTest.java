package octo_ninja.model;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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
	
	@Test
	public void testIsOccupied(){
		Board board = new Board();
		for (int i = 1; i < 5; i++) {
			for (int j = 1; j < 5; j++) {
				assertFalse(board.isOccupied(i, j));
			}
		}
		Set<Piece> pieces = Piece.getPieceSet();
		int t = 0;
		for (Piece piece : pieces) {
			assertEquals(16 - t, board.getUnoccupiedCount());
			int x = (t % 4) + 1;
			int y = (t / 4) + 1;
			int[][] list = board.getUnoccupiedPositions();
			System.out.println(x + " " + y + " " + list.toString());
			boolean found = false;
			for (int i = 0; i < list.length; i++) {
				if(list[i][0] == x && list[i][1] == y){
					found = true;
				}
			}
			assertTrue("Unoccupied position not in unoccupied positions list.",found);
			board.placePiece(piece, x, y);
			assertTrue(board.isOccupied(x, y));
			list = board.getUnoccupiedPositions();
			found = false;
			for (int i = 0; i < list.length; i++) {
				if(list[i][0] == x && list[i][1] == y){
					found = true;
				}
			}
			assertFalse("Occupied position found in unoccupied positions list.",found);
			t++;
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIsOccupiedLowArgument(){
		Board board = new Board();
		board.isOccupied(0, 0);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testIsOccupiedHighArgument(){
		Board board = new Board();
		board.isOccupied(5, 5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIsOccupiedMixedXArgument(){
		Board board = new Board();
		board.isOccupied(4, 5);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testIsOccupiedMixedYArgument(){
		Board board = new Board();
		board.isOccupied(0, 3);
	}
}
