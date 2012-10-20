package octo_ninja.player;

import java.io.IOException;
import java.util.Random;
import java.util.Set;

import octo_ninja.model.GameState;
import octo_ninja.model.Move;
import octo_ninja.model.Piece;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomPlayer extends TournamentPlayer {
	private static Random random = new Random();
	private static Logger logger = LoggerFactory.getLogger(RandomPlayer.class);

	public static void main(String[] args) throws IOException{
		try{new RandomPlayer().runGame();
		}catch(Throwable e){
			logger.warn("Problem running game.", e);
		}
	}

	@Override
	public Move chooseMove(GameState state) {
		return getRandomMove(state);
	}

	public static Move getRandomMove(GameState state) {
		Set<Piece> pieces = state.getPieces();
		Piece chosenPiece = null;
		if(pieces.size() > 0){
			int index = random.nextInt(pieces.size());
			for (Piece piece : pieces) {
				if(index-- == 0){
					chosenPiece = piece;
					break;
				}
			}
		}
		int[][] available = state.getBoard().getUnoccupiedPositions();
		if(state.getBoard().getUnoccupiedCount() < 1){ logger.warn("Tried to get random move with no moves remaining"); return null;}
		int[] chosen = available[random.nextInt(available.length)];
		return new Move(chosenPiece, chosen[0], chosen[1]);
	}

	@Override
	public void gameEnded(boolean victory) {
		System.out.println("A WINRAR IS ME!");
	}

}
