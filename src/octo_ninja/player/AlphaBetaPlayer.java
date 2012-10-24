package octo_ninja.player;

import octo_ninja.model.GameState;
import octo_ninja.model.Move;
import octo_ninja.model.Piece;

/** A framework Player using minimax with alpha-beta pruning, with a pluggable state estimator.*/
public abstract class AlphaBetaPlayer extends TournamentPlayer {
	private static final int RANDOM_CUTOFF = 6;
	protected int firstTurn = -1;
	private int plyDepth = 3;
	
	protected AlphaBetaPlayer(){
		
	}
	
	protected AlphaBetaPlayer(int plyDepth){
		this.plyDepth = plyDepth;
	}

	@Override
	public Move chooseMove(GameState state) {
		firstTurn = state.getTurn() % 2;
		if(state.getTurn() < RANDOM_CUTOFF){
			return RandomPlayer.getRandomMove(state);
		}
		Node start = new Node(state);
		start.getEstimate();
		Move ret = start.getBestMove();
		return ret;
	}


	/** The state estimator used by the minimax algorithm to choose moves. Should vary in the range [-1d,1d], signifying guaranteed loss and guaranteed win respectively. */
	public abstract double getEstimate(GameState state);

	enum NodeType{
		MAX,
		MIN;
	}

	class Node{
		private static final double TIE_UTILITY = 0;
		private static final double LOSS_UTILITY = 1d;
		private static final double VICTORY_UTILITY = -1d;
		private NodeType type;
		private GameState state;
		private double alpha, beta;
		private int depth;
		private Move bestMove;

		/** Constructor for the first node of a tree. */
		Node(GameState state){
			this.type = NodeType.MAX;
			this.state = state;
			this.alpha = Double.NEGATIVE_INFINITY;
			this.beta = Double.POSITIVE_INFINITY;
			this.depth = 0;
		}

		Node(GameState state, NodeType type, double alpha, double beta, int depth){
			this.type = type;
			this.state = state;
			this.alpha = alpha;
			this.beta = beta;
			this.depth = depth;
		}


		public Move getBestMove(){
			if(bestMove == null){
				int options = state.getBoard().getUnoccupiedCount() * state.getPieces().size();
				System.out.println("My best move is null, out of " + options + " options. Game is finished:"  + state.isWon());
			}
			return bestMove;
		}

		public double getEstimate(){
			//Check if we are a final board state.
			if(state.isWon()){
				if(state.getTurn() % 2 == firstTurn){
					return VICTORY_UTILITY;
				}else{
					return LOSS_UTILITY;
				}
			}
			if(state.getPieces().size() == 0 && state.getChosenPiece() == null){
				return TIE_UTILITY;
			}
			//Are we allowed more lookahead than this?
			if(depth > plyDepth){ //1 ply contains 2 nodes.
				//If not, use heuristic state evaluator.
				return AlphaBetaPlayer.this.getEstimate(state);
			}else{
				//If we are, generate child nodes and recursively call them.
				int[][] availablePositions = state.getBoard().getUnoccupiedPositions();
				Piece[] piecesToChooseFrom = state.getPieces().toArray(new Piece[0]);
				double result = (type == NodeType.MAX ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
				for (int i = 0; i < availablePositions.length; i++) {
					for (int j = 0; j < piecesToChooseFrom.length; j++) {
						Move move = new Move(piecesToChooseFrom[j], availablePositions[i][0], availablePositions[i][1]);
						GameState childState = state.applyMove(move);
						Node child = new Node(childState, (type == NodeType.MAX ? NodeType.MIN : NodeType.MAX), alpha, beta, depth+1);
						double childEstimate = child.getEstimate();
						switch(type){
						case MAX:
							if(childEstimate > result){result = childEstimate; bestMove = move;}
							if(result >= beta){return result;} //Prune this node for being better than the worst MIN in the tree above.
							if(childEstimate > alpha){alpha = result;}
							break;
						case MIN:
							if(childEstimate < result){result = childEstimate; bestMove = move;}
							if(result <= alpha){return result;} //Prune this node for being worse than the best MAX in the tree above.
							if(childEstimate < beta){ beta = result;}
						}

					}
				}
				//Should never happen, but compiler requires it.
				assert false;
				return 0d;
			}

		}
	}
}