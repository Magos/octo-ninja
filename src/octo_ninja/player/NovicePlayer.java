package octo_ninja.player;

import java.util.Iterator;
import java.util.Set;

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
		int[] winningPosition = null;
		for(int[] position : board.getUnoccupiedPositions()){
			Board temp = state.getBoard();
			if(temp.placePiece(placeable, position[0], position[1])){
				winningPosition = position;
				board = temp;
			}
		}
		Piece chosen = null;
		Set<Piece> available = state.getPieces();
		Iterator<Piece> it = available.iterator();
		while(chosen == null && it.hasNext()){
			Piece considered = it.next();
			//Check whether an instant win placement exists for the opponent.
			try {
				for(int[] position : board.getUnoccupiedPositions()){					
					Board temp = board.clone();	
					if(temp.placePiece(considered, position[0], position[1])){
						break;
					}
					chosen = considered;
				}
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		if(winningPosition != null){
			Move ret = new Move((chosen == null ? available.iterator().next() : chosen), winningPosition[0], winningPosition[1]);
			return ret;
		}else if(chosen != null){
			Move ret = RandomPlayer.getRandomMove(state);
			ret = new Move(chosen,ret.getX(),ret.getY());
			return ret;
		}
		return RandomPlayer.getRandomMove(state);
	}

	@Override
	public void gameEnded(boolean victory) {
		// TODO Auto-generated method stub

	}


}
