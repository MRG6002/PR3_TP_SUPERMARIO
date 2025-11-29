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
	
	public static GameObject parse(String objWords[], GameWorld game) {
		GameObject object = null;
		for (GameObject o: availableObjects) {
			object = o.parse(objWords, game);
			if(object != null) return object;
		}
	return object;
	}

}
