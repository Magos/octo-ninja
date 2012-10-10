package octo_ninja.model;

/** An interface for interacting with players of different kinds. */
public interface Player {
	/** Request the player's move for a given board and piece set state. */
	public Move chooseMove(Board board, Piece[] pieces);
	/** Inform the player of game end, and whether or not they won. */
	public void gameEnded(boolean victory);
}
