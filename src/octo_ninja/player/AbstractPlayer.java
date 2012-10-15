package octo_ninja.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import octo_ninja.model.Board;
import octo_ninja.model.GameState;
import octo_ninja.model.Move;
import octo_ninja.model.Piece;
import octo_ninja.model.Player;

/**An infrastructure for using a Player as a process which communicates its moves over STDIN/STDOUT. Intended for tournament use. */
public abstract class AbstractPlayer implements Player {
	private static final Pattern COORDINATES_PATTERN = Pattern.compile("[1234] [1234]");
	private static Logger logger = LoggerFactory.getLogger(AbstractPlayer.class);

	protected void runGame(){

		Board board = new Board();
		Set<Piece> pieces = Piece.getPieceSet();
		GameState state = new GameState(board, null, null, pieces);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String first = null;
		try {
			first = readLine(reader);
		} catch (IOException e) {
			logger.debug("IOException", e);
		}
		if(first.equalsIgnoreCase("Make first move.")){
			Move move = chooseMove(state);
			Piece piece = move.getChosenPiece();
			pieces.remove(piece);
			state = new GameState(board,piece,null,pieces);
			output(piece.toString());
			move = getOpponentMove(reader);
			state = applyMove(move, state);
		}else{
			Piece opponentsChoice = Piece.PieceFromString(first);
			if(opponentsChoice == null){
				throw new RuntimeException("Opponent chose invalid piece.");
			}
			pieces.remove(opponentsChoice);
			state = new GameState(board, opponentsChoice,this,pieces);
		}

		while(true){
			Move move = chooseMove(state);
			output(move.getChosenPiece().toString());
			output((move.getX()-1) + " " + (move.getY()-1)); //Subtract 1 because tournament uses 0-indexed X/Y
			state = applyMove(move, state);
			move = getOpponentMove(reader);
			state = applyMove(move, state);
		}


	}

	private GameState applyMove(Move move, GameState state) {
		Piece placed = state.getChosenPiece();
		Board board = state.getBoard();
		board.placePiece(placed, move.getX(), move.getY());
		Set<Piece> pieces = state.getPieces();
		pieces.remove(placed);
		return new GameState(board, move.getChosenPiece(), null, pieces);
	}

	private Move getOpponentMove(BufferedReader reader){
		String pieceLine = null;
		String coordinatesLine = null;
		try{
			pieceLine = readLine(reader);
			coordinatesLine = readLine(reader);
		}catch(IOException e){
			logger.warn("IOException",e);
		}
		int x = -1;
		int y = -1;
		if(COORDINATES_PATTERN.matcher(coordinatesLine).matches()){
			//The tournament uses 0-indexed coordinates, not 1-indexed ones.
			x = Integer.parseInt(coordinatesLine.substring(0,1)) + 1;
			y = Integer.parseInt(coordinatesLine.substring(2,3)) + 1;

		}
		Piece piece = Piece.PieceFromString(pieceLine);
		return new Move(piece,x,y);
	}

	private String readLine(BufferedReader reader) throws IOException {
		String ret = reader.readLine();
		logger.trace("Player {} received: {}", hashCode(),ret);
		return ret;
	}

	private void output(String s){
		logger.trace("Player {} output: {}",hashCode(),s);
		System.out.println(s);
	}


}
