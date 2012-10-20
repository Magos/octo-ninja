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
	private int turn;

	protected void runGame(){

		Board board = new Board();
		Set<Piece> remainingPieces = Piece.getPieceSet();
		GameState state = new GameState(board, null,remainingPieces);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String first = null;
		try {
			first = readLine(reader);
		} catch (IOException e) {
			logger.debug("IOException", e);
		}
		if(first.equalsIgnoreCase(".")){
			turn = 0;
			Move move = chooseMove(state);
			Piece piece = move.getChosenPiece();
			remainingPieces.remove(piece);
			state = new GameState(board,piece,remainingPieces);
			output(piece.toString());
		}else{
			turn = 1;
			Piece opponentsChoice = Piece.PieceFromString(first);
			if(opponentsChoice == null){
				throw new RuntimeException("Opponent chose invalid piece.");
			}
			remainingPieces.remove(opponentsChoice);
			state = new GameState(board, opponentsChoice,remainingPieces);			
		}

		while(state.getTurn() < 16 && !state.getBoard().isWon() && !state.getPieces().isEmpty()){
			Move move = null;
			logger.trace("Player {} starting turn {}.",turn,state.getTurn());
			if(state.getTurn() % 2 == turn){
				logger.trace("Player {} choosing move, state is {}.", turn, state);
				move = chooseMove(state); 
				logger.trace("Player {} chose {}. Attempting to output.",turn, move);
				outputMove(move);
			}else{
				logger.trace("Player {} trying to read opponent's move.",turn);
				move = getOpponentMove(reader);				
			}
			logger.trace("Player {} updating state, state is {}", turn,state);
			state = state.applyMove(move);
			logger.trace("Player {} finished updating after move, state is {}.", turn, state);
		}
		if(turn == 0 && state.getPieces().isEmpty() && state.getBoard().getUnoccupiedCount() == 1){ //Make sure we output our last move even when we can't do anything else.
			logger.trace("Player {} outputting final move.", turn);
			int[] position = state.getBoard().getUnoccupiedPositions()[0];
			output((position[0]-1) + " " + (position[1]-1));
			output(null);
		}
		
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
		logger.trace("Player {} received: {}", turn,ret);
		return ret;
	}

	private void output(String s){
		logger.trace("Player {} output: {}",turn,s);
		System.out.println(s);
	}


}
