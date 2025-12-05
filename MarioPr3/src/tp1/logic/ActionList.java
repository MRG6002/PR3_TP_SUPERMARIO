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
	
	public void addLast(Action action) {
		if (action == Action.STOP || (!containsOpposite(action) && times(action) < 4)) 
			this.actionList.addLast(action);
	}
	
	public void clear() {
		this.actionList.clear();
	}
	
	private int times(Action action) {
		int n = 0;
		for(Action aux: this.actionList) {
			if(aux == action) n++;
		}
	return n;
	}
	
	private boolean containsOpposite(Action action) {
		for(Action aux: this.actionList) if(aux == action.opposite()) return true;
	return false;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		for(Action action: this.actionList) stringBuilder.append(action.toString()).append(Messages.SPACE);
	return stringBuilder.toString();
	}
}
