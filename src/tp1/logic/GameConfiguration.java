package tp1.logic;

import java.util.List;

import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Mario;

public interface GameConfiguration {

	// Game status
	public int getRemainingTime();
	public int points();
	public int numLives();
	public int getLevel();
	// Game objects
	public Mario getMario();
	public List<GameObject> getNPCObjects();
}
