// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic;

import java.util.Objects;

import tp1.view.Messages;

/**
 * 
 * Immutable class to encapsulate and manipulate positions in the game board
 * 
 */
public class Position {
	private final int row;
	private final int col;

	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Position go(Action action) {
	return new Position(this.row + action.getX(), this.col + action.getY());
	}
	
	public static boolean rightFormat(String string) {
	return string.startsWith("(") && string.contains(",") && string.endsWith(")");
	}
		
	public static Position parseString(String string) { // string = "(n,m)"
		String trimmedString = string.substring(1, string.length() - 1); 
		String[] strings = trimmedString.split(",");
		Position position = null;
		
		if(strings.length == 2) {
			position = new Position(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
			
			if(!position.isValid()) position = null;
		}
	return position;
	}
	
	@Override
	public boolean equals(Object obj) {
	return this == obj || (obj != null && getClass() == obj.getClass() && this.row == ((Position) obj).row && this.col == ((Position) obj).col);
	}
	
	@Override
	public int hashCode() {
	return Objects.hash(row, col);
	}
	
	public boolean isValid() {
	return 0 <= this.row && this.row < Game.DIM_Y && 0 <= this.col && this.col < Game.DIM_X;
	}
	
	@Override
	public String toString() {
	return Messages.POSITION.formatted(this.row, this.col);		
	}
}
