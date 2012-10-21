package octo_ninja.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import octo_ninja.player.RandomPlayer;
import octo_ninja.ui.HumanPlayer;


public class Game implements Runnable {
	private Result result = null;
	

	private Player[] players = new Player[2]; 
	
	public Game(Player player1, Player player2){
		players[0] = player1;
		players[1] = player2;
	}
	
	enum Result{
		PLAYER_1,
		PLAYER_2,
		TIE;
	}
	@Override
	public void run() {
		Set<Piece> remainingPieces = Piece.getPieceSet();
		Board board = new Board();
		GameState state = new GameState(board, null, remainingPieces);
		int turn = 0;
		while(turn < 17 && !state.isWon() && state.getPieces().size() > 0){
			Move move =players[turn % 2].chooseMove(state);
			state = state.applyMove(move);
			if(state.isWon()){
				result = (turn % 2 == 0 ? Result.PLAYER_1 : Result.PLAYER_2);
			}
			if(state.getPieces().size() == 0){
				result = Result.TIE;
			}
			turn++;
		}
	}
	public Result getResult() {
		return result;
	}
	
	public static void main(String[] args) {
		Player player1 = null;
		Class<Player> ply1Class = null;
		try {
			 ply1Class = (Class<Player>) Class.forName(args[0]);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			player1 = ply1Class.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		Player player2 = null;
		Class<Player> ply2Class = null;
		
		try {
			 ply2Class = (Class<Player>) Class.forName(args[1]);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			player2 = ply2Class.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		int n = 100;
		
		if(args[3] != null && args[3].equalsIgnoreCase("-n")){
			n = Integer.parseInt(args[4]);
		}
		Map<Result,Integer> results = new HashMap<Result,Integer>();
		for (Result res : Result.values()) {
			results.put(res, 0);
		}
		for (int i = 0; i < n; i++) {
			Game game = new Game(player1, player2);
			game.run();
			Result res = game.getResult();
			results.put(res, results.get(res)+1);
		}
		System.out.println(results);
	}

}
