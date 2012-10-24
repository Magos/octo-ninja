import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import octo_ninja.model.Game;
import octo_ninja.model.Game.Result;
import octo_ninja.model.Player;
import octo_ninja.player.NovicePlayer;
import octo_ninja.player.RandomPlayer;
import octo_ninja.player.TournamentPlayer;
import octo_ninja.player.evolved.ANNPlayer;
import octo_ninja.ui.HumanPlayer;


/** An entry point class which gives access to the various tournament players, and runs games. */
public class Main {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length < 2){
			usage();
			System.exit(0);
		}
		switch(args[0]){
		case "-t":
			Player ply = getPlayer(args[1]);
			if(ply == null || ! (ply instanceof TournamentPlayer)){
				System.err.println("Error: Cannot use player " + args[1]);
				System.exit(1);
			}
			TournamentPlayer tply = (TournamentPlayer) ply;
			tply.runGame();
			break;
		case "-g":
			int n = 1;
			boolean numberSpecified = false;
			try{
				n = Math.abs(Integer.parseInt(args[1]));
				numberSpecified = true; 
			}catch(NumberFormatException e){
				//Use default value.
			}
			Player player1 = getPlayer(args[(numberSpecified ? 2 : 1)]);
			Player player2 = getPlayer(args[(numberSpecified ? 3 : 2)]);
			Map<Result,Integer> results = new HashMap<Result,Integer>();
			for (Result res : Result.values()) {
				results.put(res, 0);
			}
			boolean verbose = false;
			if((!numberSpecified &&args.length > 3)  || (numberSpecified && args.length > 4)){
				String verb = args[(numberSpecified ? 4 : 3)];
				if("-v".equalsIgnoreCase(verb)){
					verbose = true;
				}
			}
			Game[] games = new Game[n];
			ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
			for (int i = 0; i < n; i++) {
				Game game = new Game(player1, player2,verbose);
				games[i] = game;
				pool.execute(game);
			}
			pool.shutdown();
			try {
				pool.awaitTermination(5, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				System.err.println("Error while simulating games.");
				e.printStackTrace();
			}
			for (Game game : games) {				
				Result res = game.getResult();
				results.put(res, results.get(res)+1);
			}
			System.out.println("Games finished. Results were: ");
			System.out.println(results);
			break;
		default:
			usage();
		}
	}

	@SuppressWarnings("unchecked")
	private static Player getPlayer(String string) {
		Player ret = null;
		if("RandomPlayer".equalsIgnoreCase(string)){
			ret = new RandomPlayer();
		}else if("NovicePlayer".equalsIgnoreCase(string)){
			ret = new NovicePlayer();
		}else if("ANNPlayer".equalsIgnoreCase(string)){
			try {
				ret = new ANNPlayer(new File("champion.activator"));
			} catch (IOException e) {
				System.err.println("Failed to load ANN for ANNPlayer.");
				e.printStackTrace();
			}
		}else if("ANNPlayer".equalsIgnoreCase(string.substring(0,string.length()-1))){
			int n = 3;
			try{n = Integer.parseInt(string.substring(string.length()-1));}
			catch(NumberFormatException e){
				System.err.println("Couldn't understand plydepth specified." );
				e.printStackTrace();
			}
			try {
				ret = new ANNPlayer(new File("champion.activator"), n);
			} catch (IOException e) {
				System.err.println("Failed to load ANN for ANNPlayer.");
				e.printStackTrace();
			}
		}else if("HumanPlayer".equalsIgnoreCase(string)){
			ret = new HumanPlayer();
		}else{
			try {
				Class<Player> plyClass = (Class<Player>) Class.forName(string);
				ret = plyClass.newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				System.err.println("Failed to instantiate provided player class " + string);
				e.printStackTrace();
			}

		}
		return ret;
	}

	private static void usage() {
		System.out.println("Usage:");
		System.out.println("-t <Player> runs the indicated player in tournament mode. ");
		System.out.println("-g (<Natural number>) <Player> <Player> (-v) runs one or optionally more games between the indicated players and shows the results.");
		System.out.println("-v will make the game simulator run verbosely, outputting the board state after every move.");
		System.out.println("Use HumanPlayer to play interactively. It does not support tournament mode.");
	}

}
