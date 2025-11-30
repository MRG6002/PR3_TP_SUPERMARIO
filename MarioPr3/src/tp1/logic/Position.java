// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic;

import java.util.Objects;

import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
import tp1.view.Messages;

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
	
	@Override
	public boolean equals(Object obj) {
	return this == obj || 
			(obj != null && getClass() == obj.getClass() && this.row == ((Position) obj).row && this.col == ((Position) obj).col);
	}
	
	@Override
	public int hashCode() {
	return Objects.hash(row, col);
	}
	
	public boolean isBorder() {
	return this.row == -1 || this.row == Game.DIM_Y || this.col == -1 || this.col == Game.DIM_X;
	}
	
	@Override
	public String toString() {
	return Messages.POSITION.formatted(this.row, this.col);		
	}
	
	
	public static Position stringToPosition(String string) throws OffBoardException, PositionParseException{
		
		try {
			Position pos = null;
			if(string != null && validStringFormat(string)) {
				String[] posiciones = parsePositions(string);
				int posx = Integer.parseInt(posiciones[0]);
				int posy = Integer.parseInt(posiciones[1]);
				
				if(Position.validPosition(posx, posy)) pos = new Position(posx, posy);
				else throw new OffBoardException();
				
			}
			else throw new PositionParseException(Messages.INVALID_POSITION.formatted(string));
			return pos;
		} catch (NumberFormatException nfe) {
			throw new PositionParseException(Messages.INVALID_POSITION.formatted(string), nfe);
		}
	}
	
	private static boolean validStringFormat(String string) {
		return string.startsWith("(") && string.endsWith(")") 
				&& string.contains(",") && string.split(",").length == 2;
	}
	
	private static String[] parsePositions(String string) {
		return string.substring(1, string.length() - 1).split(",");
	}

	private static boolean validPosition(int posx, int posy) {
		return (posx >= 0 && posx < Game.DIM_Y && posy >= 0 && posy < Game.DIM_X);
	}

	public Position copy() {
		return new Position(this.row, this.col);
	}
}
