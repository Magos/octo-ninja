package octo_ninja.ui;

import octo_ninja.model.Board;
import octo_ninja.model.Move;
import octo_ninja.model.Piece;
import octo_ninja.model.Player;

/** A Player implementation that presents the progress of play on STDOUT and requests moves over STDIN, to be chosen by a (presumed) human. */
public class HumanPlayer implements Player {

	@Override
	public Move chooseMove(Board board, Piece[] pieces) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void gameEnded(boolean victory) {
		// TODO Auto-generated method stub
		
	}

}
