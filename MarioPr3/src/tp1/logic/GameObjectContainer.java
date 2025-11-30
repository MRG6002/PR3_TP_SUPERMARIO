// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;
import tp1.view.Messages;

public class GameObjectContainer {
	private List<GameObject> objects;
	
	public GameObjectContainer() {
		objects = new ArrayList<>();
	}
	
	public void add(GameObject object) {objects.add(object);}
	
	private List<GameObject> auxiliarObjects() {
		List<GameObject> aux = new ArrayList<>();
		for (GameObject object : objects) aux.add(object);
		return aux;
	}
	
	public void update() {
		List<GameObject> aux = auxiliarObjects();
		for (GameObject object : aux) {
			if(object.isAlive()) {
				object.update();
			}
		}
		removeDead();
	}
	
	public void doInteractionsFrom(GameItem object) {
		List<GameObject> aux = auxiliarObjects();
		for(GameObject o: aux) { 
			object.interactWith(o);
			o.interactWith(object);
		}
	}
	
	private void removeDead() {
		List<GameObject> aux = new ArrayList<>();
		for(GameObject o: this.objects) if(o.isAlive()) aux.add(o);
		this.objects = aux;
	}
	
	public boolean isSolid(Position position) {
		for(GameObject o:this.objects) {
			if(o.isInPosition(position)) return o.isSolid();
		}
	return false;
	}

	public String positionToString(Position position) {
		StringBuilder stringBuilder = new StringBuilder();
		for(GameObject o: this.objects) {
			if(o.isAlive() && o.isInPosition(position)) stringBuilder.append(o.getIcon());
		}
		return stringBuilder.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for(GameObject o: this.objects) stringBuilder.append(Messages.LINE.formatted(o.toString()));
	return stringBuilder.toString();
	}
}
