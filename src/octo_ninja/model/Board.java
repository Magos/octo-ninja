package octo_ninja.model;

public class Board {
	private Piece[][] board;

	public Board(){
		board = new Piece[4][4];
	}

	public void placePiece(Piece piece, int x, int y){
		if(x < 1 || x > 4){return;}
		if(y < 1 || y > 4){return;}
		if(piece == null){return;}
		if(board[x-1][y-1] != null){return;}
		board[x-1][y-1] = piece;
	}

	public boolean isWon(){
		int roundCounter, redCounter,smoothCounter,largeCounter;
		//Check rows
		for (int i = 0; i < board.length; i++) {
			roundCounter = redCounter = smoothCounter = largeCounter = 0;
			for (int j = 0; j < board[i].length; j++) {
				if(board[i][j] != null){
					Piece piece = board[i][j];
					if(piece.isRound()){roundCounter++;}
					if(piece.isRed()){redCounter++;}
					if(piece.isSmooth()){smoothCounter++;}
					if(piece.isLarge()){largeCounter++;}
				}
			}
			if(roundCounter==4 || redCounter == 4 || smoothCounter == 4 || largeCounter == 4){
				return true;
			}
		}
		//Check columns
		for (int j = 0; j < board.length; j++) {
			roundCounter = redCounter = smoothCounter = largeCounter = 0;
			for (int i = 0; i < board[j].length; i++) {
				if(board[i][j] != null){
					Piece piece = board[i][j];
					if(piece.isRound()){roundCounter++;}
					if(piece.isRed()){redCounter++;}
					if(piece.isSmooth()){smoothCounter++;}
					if(piece.isLarge()){largeCounter++;}
				}
			}
			if(roundCounter==4 || redCounter == 4 || smoothCounter == 4 || largeCounter == 4){
				return true;
			}
		}
		//Check diagonals
		roundCounter = redCounter = smoothCounter = largeCounter = 0;
		for (int i = 0; i < board.length; i++) {
			if(board[i][i] != null){
				Piece piece = board[i][i];
				if(piece.isRound()){roundCounter++;}
				if(piece.isRed()){redCounter++;}
				if(piece.isSmooth()){smoothCounter++;}
				if(piece.isLarge()){largeCounter++;}
			}
		}
		if(roundCounter==4 || redCounter == 4 || smoothCounter == 4 || largeCounter == 4){
			return true;
		}
		
		roundCounter = redCounter = smoothCounter = largeCounter = 0;
		for (int i = 0; i < board.length; i++) {
			if(board[i][board.length-i] != null){
				Piece piece = board[i][board.length - i];
				if(piece.isRound()){roundCounter++;}
				if(piece.isRed()){redCounter++;}
				if(piece.isSmooth()){smoothCounter++;}
				if(piece.isLarge()){largeCounter++;}
			}
		}
		if(roundCounter==4 || redCounter == 4 || smoothCounter == 4 || largeCounter == 4){
			return true;
		}
		return false;
	}
}
