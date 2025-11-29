// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic;

public interface GameModel {

	public boolean isFinished();
	public void addAction(Action action);
	public void update();
	public void exit();
	public void reset();
	public boolean reset(int level);
	public boolean addObject(String[] objWords);
}
