package octo_ninja.player;

import octo_ninja.model.GameState;
import octo_ninja.model.Move;

/** A framework Player using minimax with alpha-beta pruning, with a pluggable state estimator.*/
public abstract class AlphaBetaPlayer extends TournamentPlayer {
	private int plyDepth = 3;

	@Override
	public Move chooseMove(GameState state) {
		Node start = new Node(state, NodeType.MAX, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		
		return null;
	}

	
	/** The state estimator used by the minimax algorithm to choose moves.*/
	public abstract double getEstimate(GameState state);

	enum NodeType{
		MAX,
		MIN;
	}
	class Node{
		private NodeType type;
		private GameState state;
		private double alpha, beta;
		
		Node(GameState state, NodeType type, double alpha, double beta){
			this.type = type;
			try {
				this.state = state.clone();
			} catch (CloneNotSupportedException e) {
				System.err.println("Impossibru! Game state didn't implement cloneable.");
				e.printStackTrace();
			}
			this.alpha = alpha;
			this.beta = beta;
		}
		
	}
}
