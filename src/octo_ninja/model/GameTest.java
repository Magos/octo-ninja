package octo_ninja.model;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import octo_ninja.player.NovicePlayer;
import octo_ninja.player.RandomEstimator;
import octo_ninja.player.RandomPlayer;
import octo_ninja.player.evolved.ANNPlayer;

import org.junit.Test;

public class GameTest {

	//Basic as can be: Run a game by random players and verify nothing throws exceptions and it ends in a somewhat reasonable timeframe.
	@Test(timeout=500)
	public void testRun() {
		Game game = new Game(new RandomPlayer(), new RandomPlayer());
		game.run();
		System.out.println(game.getResult());
	}
	
	@Test
	public void testAlphaBeta() {
		Game game = new Game(new RandomPlayer(), new RandomEstimator());
		game.run();
		System.out.println(game.getResult());
	}
	
	@Test
	public void testANNPlayer() throws Exception {
		Player ply1 = new RandomPlayer();
		Player ply2 = new ANNPlayer(new File("champion.activator"));
		runGames(ply1, ply2);
		runGames(new RandomEstimator(),ply2);
		
	}
	
	@Test
	public void testNovicePlayer() throws InterruptedException{
		runGames(new RandomPlayer(), new NovicePlayer());
		runGames(new NovicePlayer(), new NovicePlayer());
	}

	private void runGames(Player ply1, Player ply2) throws InterruptedException {
		int[] results = new int[3];
		ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		int runs = 100;
		Game[] games = new Game[runs];
		for (int i = 0; i < runs; i++) {
			Game game = new Game(ply1,ply2);
			games[i] = game;
			pool.execute(game);
		}
		pool.shutdown();
		pool.awaitTermination(1, TimeUnit.MINUTES);
		for (Game game : games) {
			results[game.getResult().ordinal()]++;
		}
		System.out.println(ply1.getClass().getName() + " won " + results[0]);
		System.out.println(ply2.getClass().getName() + " won " + results[1]);
		System.out.println("Tied games: " + results[2]);
	}
}
