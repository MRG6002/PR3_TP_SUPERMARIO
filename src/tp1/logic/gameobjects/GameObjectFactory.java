// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic.gameobjects;

import java.util.Arrays;
import java.util.List;

import tp1.logic.GameWorld;

public class GameObjectFactory {
	private static final List<GameObject> availableObjects = Arrays.asList(
		new Land(),
		new ExitDoor(),
		new Goomba(),
		new Mario(),
		new Mushroom(),
		new Box()
	);
	
	public static GameObject parse(String[] objectWords, GameWorld game) {
		GameObject gameObject = null;
		 
		for(GameObject aux: availableObjects) {
			gameObject = aux.parse(objectWords, game);
			if(gameObject != null) return gameObject;
		}
	return gameObject;
	}
}
