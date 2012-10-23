package octo_ninja.model;

import java.util.Set;


public class Game implements Runnable {
	private Result result = null;
	

	private Player[] players = new Player[2]; 
	
	public Game(Player player1, Player player2){
		players[0] = player1;
		players[1] = player2;
	}
	
	public enum Result{
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
				players[turn % 2].gameEnded(true);
				players[(turn+1) % 2].gameEnded(false);
			}
			if(state.getPieces().size() == 0){
				result = Result.TIE;
				players[0].gameEnded(false);
				players[1].gameEnded(false);
			}
			turn++;
		}
	}
	public Result getResult() {
		return result;
	}

}
