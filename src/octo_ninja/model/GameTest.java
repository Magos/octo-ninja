package octo_ninja.model;

import static org.junit.Assert.*;

import octo_ninja.player.RandomPlayer;

import org.junit.Test;

public class GameTest {

	@Test
	public void testRun() {
		Game game = new Game(new RandomPlayer(), new RandomPlayer());
		game.run();
		System.out.println(game.getResult());
	}

}
