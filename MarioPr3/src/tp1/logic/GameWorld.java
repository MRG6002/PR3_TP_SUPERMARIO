// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic;

import tp1.logic.gameobjects.GameObject;

public interface GameWorld {

	public void addPoints(int num);
	public void doInteractionsFrom(GameObject object);
	public void marioDead();
	public boolean isSolid(Position position);
	public void marioExited();
	public void add(GameObject obj);
}
