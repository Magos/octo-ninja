package octo_ninja.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.regex.Pattern;

import octo_ninja.model.Board;
import octo_ninja.model.GameState;
import octo_ninja.model.IPlayer;
import octo_ninja.model.Move;
import octo_ninja.model.Piece;

/**An infrastructure for using a Player as a process which communicates its moves over STDIN/STDOUT. */
public abstract class AbstractPlayer implements IPlayer {

	private static final Pattern COORDINATES_PATTERN = Pattern.compile("[1234] [1234]");

	protected void runGame() throws IOException{

		Board board = new Board();
		Set<Piece> pieces = Piece.getPieceSet();
		GameState state = new GameState(board, null, null, pieces);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		String first = reader.readLine();
		if(first.equals("Make first move.")){
		Move move = chooseMove(state);
		Piece piece = move.getChosenPiece();
		pieces.remove(piece);
		state = new GameState(board,piece,null,pieces);
		System.out.println(piece.toString());
		}else{
			Piece oppnentsChoice = Piece.PieceFromString(first);
			if(oppnentsChoice == null){
				throw new RuntimeException("Opponent chose invalid piece.");
			}
			pieces.remove(oppnentsChoice);
			state = new GameState(board, oppnentsChoice,this,pieces);
		}


		while(!board.isWon()){
			Move move = getOpponentMove(reader);
			state = applyMove(move, state);
			move = chooseMove(state);
			System.out.println(move.getX() + " " + move.getY());
			System.out.println(move.getChosenPiece().toString());
			state = applyMove(move, state);
		}


	}

	private GameState applyMove(Move move, GameState state) {
		Piece placed = state.getChosenPiece();
		Board board = state.getBoard();
		board.placePiece(placed, move.getX(), move.getY());
		Set<Piece> pieces = state.getPieces();
		pieces.remove(move.getChosenPiece());
		return new GameState(board, move.getChosenPiece(), null, pieces);
	}

	private Move getOpponentMove(BufferedReader reader) throws IOException {
		String coordinatesLine = reader.readLine();
		String pieceLine = reader.readLine();
		int x = -1;
		int y = -1;
		if(COORDINATES_PATTERN.matcher(coordinatesLine).matches()){
			x = Integer.parseInt(coordinatesLine.substring(0,1));
			y = Integer.parseInt(coordinatesLine.substring(2,3));
			
		}
		Piece piece = Piece.PieceFromString(pieceLine);
		return new Move(piece,x,y);
	}

	

}
