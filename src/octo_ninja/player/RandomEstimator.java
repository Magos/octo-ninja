package octo_ninja.player;

import java.util.Random;

import octo_ninja.model.GameState;

/** A board estimator which returns values randomly. Intended for use as a testbed for AlphaBetaPlayer.*/
public class RandomEstimator extends AlphaBetaPlayer {
	private Random random = new Random();

	@Override
	public void gameEnded(boolean victory) {
		//Do nothing.
	}

	@Override
	public double getEstimate(GameState state) {
		double ret = random.nextDouble();
		ret = (ret - 0.5d)*2; //Shift the range to [-1,1]
		return ret;
	}

}
