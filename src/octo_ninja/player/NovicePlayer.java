package octo_ninja.player;

import java.util.ArrayList;
import java.util.List;

import octo_ninja.model.Board;
import octo_ninja.model.GameState;
import octo_ninja.model.Move;
import octo_ninja.model.Piece;

/** A simple opponent with lookahead of one move, that takes available wins and avoids giving any away.*/
public class NovicePlayer extends TournamentPlayer {

	@Override
	public Move chooseMove(GameState state) {
		if(state.getTurn() < 3){
			return RandomPlayer.getRandomMove(state);
		}
		//Identify whether we can place the chosen piece to win this move.
		Piece placeable = state.getChosenPiece();
		Board board = state.getBoard();
		List<int[]> winPositions = findMatches(placeable,board);
		//Identify a piece that won't give our opponent a win next move, if any.
		Piece[] pieces = (Piece[]) state.getPieces().toArray();
		Piece chosen = null;
		while(chosen == null){
			
		}
		return null;
	}

	private List<int[]> findMatches(Piece placeable, Board board) {
		List<int[]> ret = new ArrayList<int[]>();
		for(Piece.Feature feature : Piece.Feature.values()){
			boolean wantedValue = placeable.possessesFeature(feature);
			int counter;
			int nullX = -1,nullY = -1;
			//Check rows
			for (int i = 1; i < 5; i++) {
				counter = 0;
				for (int j = 1; j < 5; j++) {
					Piece piece = board.getPiece(i,j);
					if(piece != null && piece.possessesFeature( feature) == wantedValue){
						counter++;
					}else if(piece == null){
						nullX = i;
						nullY = j;
					}
				}
				if(counter == 3){
					ret.add(new int[]{nullX,nullY});
				}
			}
			//Check columns
			for (int j = 1; j < 5; j++) {
				counter = 0;
				for (int i = 1; i < 5; i++) {
					Piece piece = board.getPiece(i,j);
					if(piece != null && piece.possessesFeature( feature) == wantedValue){
						counter++;
					}else if(piece == null){
						nullX = i;
						nullY = j;
					}
				}
				if(counter == 3){
					ret.add(new int[]{nullX,nullY});
				}
			}
			//Check bottom left to top right
			counter = 0;
			for (int i = 1; i < 5; i++) {
				Piece piece = board.getPiece(i,i);
				if(piece != null && piece.possessesFeature( feature) == wantedValue){
					counter++;
				}else if(piece == null){
					nullX = i;
					nullY = i;
				}
			}
			if(counter == 3){
				ret.add(new int[]{nullX,nullY});
			}

			//Check top left to bottom right
			counter = 0;
			for (int i = 1; i < 5; i++) {
				Piece piece = board.getPiece(i,5-i);
				if(piece != null && piece.possessesFeature( feature) == wantedValue){
					counter++;
				}else if(piece == null){
					nullX = i;
					nullY = 5-i;
				}
			}
			if(counter == 3){
				ret.add(new int[]{nullX,nullY});
			}
		}
		return ret;
	}

	@Override
	public void gameEnded(boolean victory) {
		// TODO Auto-generated method stub

	}


}
