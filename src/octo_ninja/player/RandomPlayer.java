package octo_ninja.player;

import java.io.IOException;
import java.util.Random;
import java.util.Set;

import octo_ninja.model.GameState;
import octo_ninja.model.Move;
import octo_ninja.model.Piece;

public class RandomPlayer extends AbstractPlayer {
	private Random random;

	public RandomPlayer(){
		random = new Random();
	}

	public static void main(String[] args) throws IOException{
		new RandomPlayer().runGame();
	}

	@Override
	public Move chooseMove(GameState state) {
		Set<Piece> pieces = state.getPieces();
		int index = random.nextInt(pieces.size());
		Piece chosenPiece = null;
		for (Piece piece : pieces) {
			if(index-- == 0){
				chosenPiece = piece;
				break;
			}
		}

		int x = random.nextInt(3) + 1;
		int y = random.nextInt(3) + 1;
		while(state.getBoard().isOccupied(x,y)){
			x = random.nextInt(3) + 1;
			y = random.nextInt(3) + 1;
		}

		return new Move(chosenPiece, x, y);
	}

	@Override
	public void gameEnded(boolean victory) {
		System.out.println("A WINRAR IS ME!");
	}

}
