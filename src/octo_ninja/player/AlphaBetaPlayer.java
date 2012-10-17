package octo_ninja.player;

import octo_ninja.model.GameState;
import octo_ninja.model.Move;

/** A framework Player using minimax with alpha-beta pruning, with a pluggable state estimator.*/
public abstract class AlphaBetaPlayer extends TournamentPlayer {

	@Override
	public Move chooseMove(GameState state) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/** The state estimator used by the minimax algorithm to choose moves.*/
	public abstract double estimateState(GameState state);

}
