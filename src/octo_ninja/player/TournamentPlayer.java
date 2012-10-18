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
public abstract class TournamentPlayer implements Player {
	private static final Pattern COORDINATES_PATTERN = Pattern.compile("[0123] [0123]");
	private static Logger logger = LoggerFactory.getLogger(TournamentPlayer.class);

	protected void runGame(){

		Board board = new Board();
		Set<Piece> remainingPieces = Piece.getPieceSet();
		GameState state = new GameState(board, null, null, remainingPieces);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String first = null;
		try {
			first = readLine(reader);
		} catch (IOException e) {
			logger.debug("IOException", e);
		}
		if(first.equalsIgnoreCase(".")){
			Move move = chooseMove(state);
			Piece piece = move.getChosenPiece();
			remainingPieces.remove(piece);
			state = new GameState(board,piece,null,remainingPieces);
			output(piece.toString());
			move = getOpponentMove(reader);
			state = state.applyMove(move);
		}else{
			Piece opponentsChoice = Piece.PieceFromString(first);
			if(opponentsChoice == null){
				throw new RuntimeException("Opponent chose invalid piece.");
			}
			remainingPieces.remove(opponentsChoice);
			state = new GameState(board, opponentsChoice,this,remainingPieces);			
		}

		while(true){
			int hash = hashCode();
			logger.trace("{} choosing move, state is {}.", hash, state);
			Move move = chooseMove(state);
			logger.trace("{} chose {}. Attempting to output.",hash, move);
			outputMove(move);
			logger.trace("{} updating state, state is {}", hash,state);
			state = state.applyMove(move);
			if(state.getBoard().isWon() || state.getPieces().isEmpty()){break;}
			logger.trace("{} trying to read opponent's move.",hash);
			move = getOpponentMove(reader);
			logger.trace("{} updating after opponent's move, state is {}.", hash, state);
			state = state.applyMove(move);
			logger.trace("{} finished updating after opponent's move, state is {}.", hash, state);
			if(state.getBoard().isWon() || state.getPieces().isEmpty()){break;}
		}
//		Move move = chooseMove(state);
//		outputMove(move);
		try {
			String last = readLine(reader);
			if(last != null && last.equals("Victory")){
				gameEnded(true);
			}else{
				gameEnded(false);
			}
		} catch (IOException e) {
			logger.warn("Could not read final game master message.",e);
		}

	}

	public void outputMove(Move move) {
		output((move.getX()-1) + " " + (move.getY()-1)); //Subtract 1 because tournament uses 0-indexed X/Y
		output(move.getChosenPiece().toString());
	}

	

	private Move getOpponentMove(BufferedReader reader){
		String pieceLine = null;
		String coordinatesLine = null;
		try{
			coordinatesLine = readLine(reader);
			pieceLine = readLine(reader);
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
