// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;

public interface GameModel {

	public boolean isFinished();
	public void addAction(Action action);
	public void update();
	public void exit();
	public void reset();
	public void reset(int level) throws GameModelException;
	public void addObject(String[] objWords) throws GameModelException;
	public void save(String fileName) throws GameModelException;
	public void load(String fileName) throws GameLoadException;
}
