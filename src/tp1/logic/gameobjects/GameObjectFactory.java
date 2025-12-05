// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic.gameobjects;

import java.util.Arrays;
import java.util.List;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.view.Messages;

public class GameObjectFactory {
	private static final List<GameObject> availableObjects = Arrays.asList(
		new Land(),
		new ExitDoor(),
		new Goomba(),
		new Mario(),
		new Mushroom(),
		new Box()
	);
	
	public static GameObject parse(String[] objectWords, GameWorld game) throws OffBoardException, ObjectParseException {
		for(GameObject aux: availableObjects) {
			GameObject gameObject = aux.parse(objectWords, game);
			
			if(gameObject != null) return gameObject;
		}
		throw new ObjectParseException(Messages.UNKNOWN_GAMEOBJECT.formatted(String.join(" ", objectWords)));
	}
}
