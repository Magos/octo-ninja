package octo_ninja.model;

import java.util.Set;

public class GameState {
	private Board board;
	private Piece chosenPiece;
	private Player next;
	private Set<Piece> pieces;
	

	public GameState(Board board, Piece chosenPiece, Player next, Set<Piece> pieces){
		this.board = board;
		this.chosenPiece = chosenPiece;
		this.pieces = pieces;
		this.next = next;
	}
	
	public Player getNext(){
		return this.next;
	}
	
	public Piece getChosenPiece() {
		return chosenPiece;
	}

	public Board getBoard() {
		return board;
	}
	
	public Set<Piece> getPieces() {
		return pieces;
	}

	@Override
	public String toString() {
		return chosenPiece.toString() + pieces.toString();
	}
}
