// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic;

import java.util.Objects;

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
	
	public static Position stringToPosition(String string) {
		Position pos = null;
		if(string.startsWith("(") && string.endsWith(")") && string.contains(",")) {
			String aux = string.substring(1, string.length() - 1);
			String[] posiciones = aux.split(",");
			if(posiciones.length == 2) {
				int posx = Integer.parseInt(posiciones[0]);
				int posy = Integer.parseInt(posiciones[1]);
				if(Position.validPosition(posx, posy)) pos = new Position(posx, posy);
			}
			
		}
		return pos;
	}

	private static boolean validPosition(int posx, int posy) {
		return (posx >= 0 && posx < Game.DIM_Y && posy >= 0 && posy < Game.DIM_X);
	}
}
