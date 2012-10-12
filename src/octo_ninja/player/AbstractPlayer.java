package octo_ninja.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import octo_ninja.model.Board;
import octo_ninja.model.GameState;
import octo_ninja.model.IPlayer;
import octo_ninja.model.Move;
import octo_ninja.model.Piece;

/**An infrastructure for using a Player as a process which communicates its moves over STDIN/STDOUT. */
public abstract class AbstractPlayer implements IPlayer {
	
	public static void main(String[] args) throws IOException {
		
		Board board = new Board();
		Set<Piece> pieces = Piece.getPieceSet();
		GameState state = new GameState(board, null, null, pieces);
		AbstractPlayer player = getInstance();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		Move move = player.chooseMove(state);
		Piece piece = move.getChosenPiece();
		System.out.println(piece.toString());
		
		while(!board.isWon()){
			String response = reader.readLine();
		}
	}

	protected static abstract AbstractPlayer getInstance();


}
