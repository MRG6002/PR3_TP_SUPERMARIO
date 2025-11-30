// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;

import tp1.view.Messages;

public class GameObjectContainer {
	private List<GameObject> gameObjects;

	public GameObjectContainer() {
		this.gameObjects = new ArrayList<>();
	}
	
	public void add(GameObject gameObject) {
		this.gameObjects.addLast(gameObject);
	}
	
	public boolean isSolid(Position position) {
		for(GameObject gameObject: this.gameObjects) {
			if(gameObject.isInPosition(position) && gameObject.isSolid()) return true;
		}
	return false;
	}
	
	private void removeDead() {
		List<GameObject> auxList = new ArrayList<>();
		
		for(GameObject gameObject: this.gameObjects) {
			if(gameObject.isAlive()) auxList.addLast(gameObject);
		}
		this.gameObjects = auxList;
	}
	
	public void update() {
		List<GameObject> auxList = new ArrayList<>();
		
		auxList.addAll(this.gameObjects);
		for(GameObject gameObject: auxList) {
			if(gameObject.isAlive()) gameObject.update();
		}
		this.removeDead();
	}
	
	public void doInteraction(GameItem gameItem) {
		List<GameObject> auxList = new ArrayList<>();
		
		auxList.addAll(this.gameObjects);
		for(GameObject gameObject: auxList) {
			gameItem.interactWith(gameObject);
			gameObject.interactWith(gameItem);
		}
	}
	
	public String postitionToString(Position position) {		
		StringBuilder stringBuilder = new StringBuilder();
		
		for(GameObject gameObject: this.gameObjects) {
			if(gameObject.isInPosition(position)) stringBuilder.append(gameObject.getIcon());
		}
	return stringBuilder.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(Messages.LINE.formatted("GAMEOBJECTCONTAINER:"));
		for(GameObject gameObject: this.gameObjects) stringBuilder.append(Messages.LINE.formatted(gameObject.toString()));
	return stringBuilder.toString();
	}
}
