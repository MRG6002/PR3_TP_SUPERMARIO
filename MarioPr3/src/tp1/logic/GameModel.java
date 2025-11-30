// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic;

import tp1.exceptions.GameModelException;

public interface GameModel {

	public boolean isFinished();
	public void addAction(Action action);
	public void update();
	public void exit();
	public void reset();
	public boolean reset(int level);
	public void addObject(String[] objWords) throws GameModelException;
	public void save(String fileName) throws GameModelException;
}
