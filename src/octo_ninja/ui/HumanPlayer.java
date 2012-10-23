package octo_ninja.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import octo_ninja.model.Game;
import octo_ninja.model.GameState;
import octo_ninja.model.Move;
import octo_ninja.model.Piece;
import octo_ninja.model.Player;
import octo_ninja.player.RandomPlayer;

/** A Player implementation that presents the progress of play on STDOUT and requests moves over STDIN, to be chosen by a (presumed) human. */
public class HumanPlayer implements Player {
	private static final Pattern COORDINATES_LINE = Pattern.compile("[1234] [1234]");
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 

	public static void main(String[] args) {
		Game game = new Game(new HumanPlayer(),new RandomPlayer());
		new Thread(game).start();

	}

	@Override
	public void gameEnded(boolean victory) {
		System.out.println(victory ? "You won!" : "You didn't win.");
	}

	@Override
	public Move chooseMove(GameState state) {
		System.out.println("Player " + ((state.getTurn() % 2 )+1));
		int x = -1, y = -1;
		if(state.getTurn() != 0){			
			System.out.println("Place this piece on the board: " + state.getChosenPiece());
			System.out.println(state.getBoard().toString());
			while(x == -1 || y == -1){
				String input = readLine();
				if(COORDINATES_LINE.matcher(input).matches()){					
					x = Integer.parseInt(input.substring(0,1));
					if(x < 1 || x > 4) x = -1;
					y = Integer.parseInt(input.substring(2,3));
					if(y < 1 || y > 4) y = -1;
				}
			}
		}
		Piece[] pieces =(Piece[]) state.getPieces().toArray(new Piece[0]);
		int chosen = -1;
		if(pieces.length > 0){
			System.out.println("Choose a piece for your opponent.");
			System.out.println("Available pieces are: ");
			for (int i = 0; i < pieces.length; i++) {
				System.out.print(i + ":" + pieces[i].toString() + " ");
			}
			System.out.println();
			while(chosen == -1){
				String input = readLine();
				try{chosen = Integer.parseInt(input);}catch(NumberFormatException e){chosen = -1;}
				if(chosen < 0 || chosen >= pieces.length) chosen = -1;
			}
			System.out.println("You chose " + pieces[chosen].toString());
		}
		return new Move((chosen >-1 ? pieces[chosen] : null), x, y);
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
