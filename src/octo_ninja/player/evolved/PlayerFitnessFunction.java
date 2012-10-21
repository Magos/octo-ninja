package octo_ninja.player.evolved;

import octo_ninja.model.Game;
import octo_ninja.model.Player;
import octo_ninja.player.RandomEstimator;
import ojc.ahni.hyperneat.HyperNEATFitnessFunction;

import org.jgapcustomised.Chromosome;

import com.anji.integration.Activator;

/** A fitness function for evaluating neural nets as state evaluators in Quarto. It does this by playing a series of games with them and counting victories.  */
public class PlayerFitnessFunction extends HyperNEATFitnessFunction {
	private static final long serialVersionUID = -1645407480972420545L;
	private static final int runs = 100;

	@Override
	public int getMaxFitnessValue() {
		return runs;
	}

	@Override
	protected int evaluate(Chromosome genotype, Activator substrate,
			int evalThreadIndex) {
		Player player = new ANNPlayer(substrate);
		Player opponent = new RandomEstimator();
		int wins = 0;
		for (int i = 0; i < runs; i++) {
			Game game = new Game(player, opponent);
			game.run();
			if(game.getResult() == Game.Result.PLAYER_1) wins++;
		}
		return wins;
	}

}
