package octo_ninja.player.evolved;

import com.anji.integration.Activator;

import octo_ninja.model.Board;
import octo_ninja.model.GameState;
import octo_ninja.model.Piece;
import octo_ninja.player.AlphaBetaPlayer;

/** An alpha-beta player that uses a provided ANN to inform its state estimates. */
public class ANNPlayer extends AlphaBetaPlayer {
	/** The neural net substrate used to evaluate GameStates.*/
	private Activator activator;

	public ANNPlayer(Activator activator){
		this.activator = activator;
	}
	
	@Override
	public void gameEnded(boolean victory) {
		//Do nothing.
	}

	@Override
	public double getEstimate(GameState state) {
		double[][] inputs = convertToANNInput(state);
		double[][] output = activator.next(inputs);
		return output[0][0];
	}

	private double[][] convertToANNInput(GameState state) {
		double[][] ret = new double[5][4];//Board is 4x4, plus turn input and two spares.
		Board board = state.getBoard();
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 3; j++) {
				Piece piece = board.getPiece(i+1, j+1); 
				double[] row = ret[i];
				row[j] = (piece == null ? -1 : piece.hashCode());
			}
		}
		ret[4][0] = (state.getTurn() % 2 == firstTurn ? 1 : 0);
		return ret;
	}

}
