package octo_ninja.model;

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
		while(true){
			turn++;
			Move move =players[turn % 2].chooseMove(state);
			state = state.applyMove(move);
			if(state.getBoard().isWon()){
				result = (turn % 2 == 0 ? Result.PLAYER_1 : Result.PLAYER_2);
			}
			if(state.getPieces().size() == 0){
				result = Result.TIE;
			}
		}
	}
	public Result getResult() {
		return result;
	}
	
	public static void main(String[] args) {
		Game game = new Game(new RandomPlayer(),new HumanPlayer());
		new Thread(game).start();
	}

}
