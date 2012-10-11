package octo_ninja.model;

import java.util.Set;

public class GameState {
	private Board board;
	private Piece chosenPiece;
	private IPlayer next;
	private Set<Piece> pieces;
	

	public GameState(Board board, Piece chosenPiece, IPlayer next, Set<Piece> pieces){
		this.board = board;
		this.chosenPiece = chosenPiece;
		this.pieces = pieces;
		this.next = next;
	}
	
	public IPlayer getNext(){
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

}
