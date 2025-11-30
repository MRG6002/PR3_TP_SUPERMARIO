// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic;

import java.util.Iterator;		
import java.util.ArrayList;
import java.util.List;

import tp1.view.Messages;

public class ActionList implements Iterable<Action> {
	private List<Action> actionList;
	
	public ActionList() {
		this.actionList = new ArrayList<>();
	}
	
	@Override
	public Iterator<Action> iterator() {
	return this.actionList.iterator();
	}
	
	private int countNumberOf(Action action) {
		int n = 0;
		
		for(Action aux: this.actionList) {
			if(aux == action) n++;
		}
	return n;
	}
	
	private boolean isOppositeOf(Action action) {
		for(Action aux: this.actionList) {
			if(aux == Action.opposite(action)) return true;
		}
	return false;
	}
	
	public void addLast(Action action) {
		if(!this.isOppositeOf(action) && this.countNumberOf(action) < 4) this.actionList.addLast(action);
	}
	
	public void clear() {
		this.actionList.clear();
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		for(Action action: this.actionList) stringBuilder.append(action.toString()).append(Messages.SPACE);
	return stringBuilder.toString();
	}
}
