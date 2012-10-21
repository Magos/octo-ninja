package octo_ninja.model;

import octo_ninja.player.RandomPlayer;

import org.junit.Test;

public class GameTest {

	//Basic as can be: Run a game by random players and verify nothing throws exceptions and it ends in a somewhat reasonable timeframe.
	@Test(timeout=500)
	public void testRun() {
		Game game = new Game(new RandomPlayer(), new RandomPlayer());
		game.run();
		System.out.println(game.getResult());
	}

}
