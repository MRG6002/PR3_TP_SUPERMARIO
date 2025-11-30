// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic;

public interface GameStatus {
	
	public String positionToString(int col, int row);
	public int getRemainingTime();
	public int points();
	public int numLives();
	public boolean playerWins();
	public boolean playerLoses();
}
