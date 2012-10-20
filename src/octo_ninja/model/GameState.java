package octo_ninja.model;

import java.util.HashSet;
import java.util.Set;

public class GameState implements Cloneable{
	private Board board;
	private Piece chosenPiece;
	private int turn;
	private Set<Piece> pieces;

	public GameState(Board board, Piece chosenPiece, Set<Piece> pieces){
		this.board = board;
		this.chosenPiece = chosenPiece;
		this.pieces = pieces;
		turn = (chosenPiece != null && pieces.size() == 15 ? 1 : 0);
	}

	private GameState(Board board, Piece chosenPiece, Set<Piece> pieces, int turn){
		this.board = board;
		this.chosenPiece = chosenPiece;
		this.pieces = pieces;
		this.turn = turn;
	}

	public int getTurn(){
		return turn;
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
		return "Turn: " + turn + " Finished: " + board.isWon() + " Chosen piece:" + chosenPiece.toString() + " Available pieces:" + pieces.toString();
	}

	public GameState applyMove(Move move) {
		if(turn > 0 && turn < 16){
			Piece placed = getChosenPiece();
			Board board = getBoard();
			board.placePiece(placed, move.getX(), move.getY());
		}
		Set<Piece> pieces = getPieces();
		Set<Piece> next = new HashSet<Piece>(pieces);
		next.remove(move.getChosenPiece());
		return new GameState(board, move.getChosenPiece(), next,turn+1);
	}
	
	
	public GameState clone() throws CloneNotSupportedException {
		GameState ret = (GameState) super.clone();
		ret.board = board.clone();
		ret.pieces = new HashSet<Piece>(pieces);
		return ret;
	}
}
