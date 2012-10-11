package octo_ninja.player;

import java.util.Set;

import octo_ninja.model.Board;
import octo_ninja.model.IPlayer;
import octo_ninja.model.Piece;

/**An infrastructure for using a Player as a process which communicates its moves over STDIN/STDOUT. */
public abstract class AbstractPlayer implements IPlayer {
	
	public static void main(String[] args) {
		Board board = new Board();
		Set<Piece> pieces = Piece.getPieceSet();
		while(!board.isWon()){
			
		}
	}


}
