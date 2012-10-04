package octo_ninja.model;

/** A Quarto piece, with its 4 boolean attributes.*/
public class Piece {
	private static final int ROUND_FLAG = 1;
	private static final int RED_FLAG = 2;
	private static final int LARGE_FLAG = 4;
	private static final int SMOOTH_FLAG = 8;
	private int value;
	
	private Piece(int value){
		this.value = (value < 1 ? 1 : (value > 16 ? 16 : value));
	}
	
	public boolean isRound(){
		return (value & ROUND_FLAG) != 0;
	}
	
	public boolean isRed(){
		return (value & RED_FLAG) != 0;
	}
	
	public boolean isLarge(){
		return (value & LARGE_FLAG) != 0;
	}
	public boolean isSmooth(){
		return (value & SMOOTH_FLAG) != 0;
	}
	
	/** Creates a complete set of game pieces. */
	public static Piece[] getPieceSet(){
		Piece[] ret = new Piece[16];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new Piece(i+1);
		}
		return ret;
	}
}
