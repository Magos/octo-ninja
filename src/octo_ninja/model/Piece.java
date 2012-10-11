package octo_ninja.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/** A Quarto piece, with its 4 boolean attributes.*/
public class Piece implements Cloneable{
	private static final int ROUND_FLAG = 1;
	private static final int RED_FLAG = 2;
	private static final int LARGE_FLAG = 4;
	private static final int SMOOTH_FLAG = 8;
	private int value;

	private Piece(int value){
		this.value = (value < 0 ? 0 : (value > 15 ? 15 : value));
	}

	public boolean isRound(){
		return ((value & ROUND_FLAG) != 0);
	}

	public boolean isRed(){
		return ((value & RED_FLAG) != 0);
	}

	public boolean isLarge(){
		return ((value & LARGE_FLAG) != 0);
	}
	public boolean isSmooth(){
		return ((value & SMOOTH_FLAG) != 0);
	}

	/** Returns whether this piece possesses the given feature.  A synonym for their individual getters which is easier to loop over.*/
	public boolean possessesFeature(Feature feature){
		switch(feature){
		case ROUND:
			return isRound();
		case RED:
			return isRed();
		case LARGE:
			return isLarge();
		case SMOOTH:
			return isSmooth();
		default:
			return false;
		}
	}

	/** Creates a complete set of game pieces. */
	public static Set<Piece> getPieceSet(){
		Set<Piece> ret = new HashSet<Piece>();
		for (int i = 0; i < 16; i++) {
			ret.add(new Piece(i));
		}
		return ret;
	}

	/** The features a piece can either posess or not posess. */
	enum Feature{
		ROUND,
		RED,
		LARGE,
		SMOOTH;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		boolean round = isRound();
		buf.append( round ? "(" : " ");
		if(isLarge()){
			if(isRed()){
				buf.append("R");
			}else{
				buf.append("B");
			}
		}else{
			if(isRed()){
				buf.append("r");
			}else{
				buf.append("b");
			}
		}
		buf.append(isSmooth() ? "*" : " ");
		buf.append( round ? ")" : " ");
		return buf.toString();
	}
	
	public static Piece PieceFromString(String s){
		s = s.replaceAll(" ", "");
		int value = 0;
		if(s.startsWith("(") && s.endsWith(")")) 	value += ROUND_FLAG;
		if(s.toUpperCase().contains("R")) 			value += RED_FLAG;
		if(s.equals(s.toUpperCase())) 				value += LARGE_FLAG;
		if(s.length() > 1 && s.contains("*")) 		value += SMOOTH_FLAG;
		return new Piece(value);
	}
	
	public Piece clone(){
		return new Piece(value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Piece){
			return this.value == ((Piece) obj).value;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return value;
	}
}
