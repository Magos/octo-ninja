package octo_ninja.model;

/** An interface for interacting with players of different kinds. */
public interface IPlayer {
	/** Request the player's move for a given board and piece set state. */
	public Move chooseMove(GameState state);
	/** Inform the player of game end, and whether or not they won. */
	public void gameEnded(boolean victory);
}
