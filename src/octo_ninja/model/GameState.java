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
		try {
			return board.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}catch (NullPointerException e){
			e.printStackTrace();
		}
		return null;
	}

	public Set<Piece> getPieces() {
		return new HashSet<Piece>(pieces);
	}
	
	public boolean isWon(){
		return board.isWon();
	}

	@Override
	public String toString() {
		return "Turn: " + turn + " Finished: " + isWon() + " Chosen piece:" + chosenPiece.toString() + " Available pieces:" + pieces.toString();
	}

	public GameState applyMove(Move move) {
		if(move== null){
			return this;//The result of no move is the same state..?
		}
		Board nextBoard = null;
		if(turn > 0 && turn < 16){
			Piece placed = getChosenPiece();
			nextBoard = getBoard();
			nextBoard.placePiece(placed, move.getX(), move.getY());
		}else{
			nextBoard = this.board;
		}
		Set<Piece> pieces = getPieces();
		Set<Piece> next = new HashSet<Piece>(pieces);
		next.remove(move.getChosenPiece());
		return new GameState(nextBoard, move.getChosenPiece(), next,turn+1);
	}

}
