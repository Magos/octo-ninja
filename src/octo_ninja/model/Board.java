package octo_ninja.model;

import octo_ninja.model.Piece.Feature;

public class Board {
	private Piece[][] board;

	public Board(Board original){
		board = new Piece[4][4];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if(original.board[i][j] != null){
					board[i][j] = original.board[i][j].clone();
				}
			}
		}
	}

	public Board(){
		board = new Piece[4][4];
	}

	/** Places a piece on the board and returns whether or not this move won the player the game. X and Y indexes are between 1 and 4 inclusive.*/
	public boolean placePiece(Piece piece, int x, int y) throws IllegalStateException, IllegalArgumentException{
		if(x < 1 || x > 4){throw new IllegalArgumentException("Illegal X argument.");}
		if(y < 1 || y > 4){throw new IllegalArgumentException("Illegal Y argument.");}
		if(piece == null){throw new IllegalArgumentException("Cannot place a null Piece on game board.");}
		x--; y--;
		if(board[x][y] != null){throw new IllegalStateException("Placing a piece on an occupied space.");}
		board[x][y] = piece;
		return checkVictory(piece, x, y);
	}

	private boolean checkVictory(Piece piece, int x, int y) {
		int counter = 0;
		//For every feature of the piece:
		for(Piece.Feature feature : Piece.Feature.values()){
			//Check horizontal for matches.
			for(int i = 0; i < 4;i++){
				counter += featureMatches(board[x][i],piece,feature);
			}
			if(counter == 4){return true;}
			counter = 0;
			//Check vertical for matches.
			for(int i = 0; i < 4;i++){
				counter += featureMatches(board[i][y],piece,feature);
			}
			if(counter == 4){return true;}
			counter = 0;
			//Check diagonal(s) if applicable.
			if(x == y){
				for(int i = 0; i < 4;i++){
					counter += featureMatches(board[i][i],piece,feature);
				}
				if(counter == 4){return true;}
				counter = 0;
			}
			if(y == 3 - x){
				for(int i = 0; i < 4;i++){
					counter += featureMatches(board[i][3-i],piece,feature);
				}
				if(counter == 4){return true;}
				counter = 0;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				buf.append( (board[i][j] != null ? board[i][j].toString() : "____"));
				buf.append(" ");
			}
			buf.append("\n");
		}
		return buf.toString();
	}

	private int featureMatches(Piece piece, Piece piece2, Feature feature) {
		if(piece == null || piece2 == null){return 0;} //Null pieces never match, even to other null pieces.
		return (piece.possessesFeature(feature) == piece2.possessesFeature(feature) ? 1 : 0);
	}

	public boolean isWon(){
		for(Feature feature : Feature.values()){

			int truecounter, nonNullCounter;
			//Check rows
			for (int i = 0; i < board.length; i++) {
				truecounter = nonNullCounter = 0;
				for (int j = 0; j < board[i].length; j++) {
					if(board[i][j] != null){
						nonNullCounter++;
						if(board[i][j].possessesFeature(feature)){
							truecounter++;
						}
					}
				}
				if(nonNullCounter == 4 && (truecounter == 4 || truecounter == 0)){
					return true;
				}
			}
			//Check columns
			for (int j = 0; j < board.length; j++) {
				truecounter = nonNullCounter = 0;
				for (int i = 0; i < board[j].length; i++) {
					if(board[i][j] != null){
						nonNullCounter++;
						if(board[i][j].possessesFeature(feature)){
							truecounter++;
						}
					}
				}
				if(nonNullCounter == 4 && (truecounter == 4 || truecounter == 0)){
					return true;
				}
			}
			//Check diagonals
			truecounter = nonNullCounter = 0;
			for (int i = 0; i < board.length; i++) {
				if(board[i][i] != null){
					nonNullCounter++;
					if(board[i][i].possessesFeature(feature)){
						truecounter++;
					}
				}
			}
			if(nonNullCounter == 4 && (truecounter == 4 || truecounter == 0)){
				return true;
			}

			truecounter = nonNullCounter = 0;
			for (int i = 0; i < board.length; i++) {
				if(board[i][3-i] != null){
					nonNullCounter++;
					if(board[i][3-i].possessesFeature(feature)){
						truecounter++;
					}
				}
			}
			if(nonNullCounter == 4 && (truecounter == 4 || truecounter == 0)){
				return true;
			}
		}
		return false;
	}
}
