package octo_ninja.model;

public class Move {
	private final Piece chosenPiece;
	private final int x,y;
	
	public Move(Piece chosenPiece, int x, int y){
		this.chosenPiece = chosenPiece;
		this.x = x;
		this.y = y;
	}
	
	public Piece getChosenPiece() {
		return chosenPiece;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "[" + chosenPiece.toString() + "," + x + "," + y + "]";
	}

}
