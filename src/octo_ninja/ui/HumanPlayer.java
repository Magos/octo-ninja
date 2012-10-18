package octo_ninja.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import octo_ninja.model.Game;
import octo_ninja.model.GameState;
import octo_ninja.model.Move;
import octo_ninja.model.Piece;
import octo_ninja.model.Player;
import octo_ninja.player.RandomPlayer;

/** A Player implementation that presents the progress of play on STDOUT and requests moves over STDIN, to be chosen by a (presumed) human. */
public class HumanPlayer implements Player {
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 

	public static void main(String[] args) {
		Game game = new Game(new HumanPlayer(),new RandomPlayer());
		new Thread(game).start();
		
	}
	
	@Override
	public void gameEnded(boolean victory) {
		System.out.println(victory ? "You won!" : "You lost!");
	}

	@Override
	public Move chooseMove(GameState state) {
		System.out.println("Place this piece on the board: " + state.getChosenPiece());
		System.out.println(state.getBoard().toString());
		int x = -1, y = -1;
		while(x == -1 || y == -1){
			String input = readLine();
			x = Integer.parseInt(input.substring(0,1));
			if(x < 1 || x > 4) x = -1;
			y = Integer.parseInt(input.substring(2,3));
			if(y < 1 || y > 4) y = -1;
		}
		Piece[] pieces =(Piece[]) state.getPieces().toArray();
		System.out.println("Choose a piece for your opponent.");
		System.out.println("Available pieces are: ");
		for (int i = 0; i < pieces.length; i++) {
			System.out.print(i + ":" + pieces[i].toString() + " ");
		}
		System.out.println();
		int chosen = -1;
		while(chosen == -1){
			String input = readLine();
			try{x = Integer.parseInt(input);}catch(NumberFormatException e){chosen = -1;}
			if(chosen < 0 || chosen >= pieces.length) chosen = -1;
		}
		return new Move(pieces[chosen], x, y);
	}

	private String readLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
