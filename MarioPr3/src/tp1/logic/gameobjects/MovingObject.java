// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;

import tp1.view.Messages;

public abstract class MovingObject extends GameObject {
	private Action direction;
	private boolean isFalling;
	
	public MovingObject(Position position, GameWorld game, Action direction, String name, String shortcut) {
		super(position, game, name, shortcut);
		this.direction = direction;
		this.isFalling = false;
	}
	
	@Override
	public GameObject parse(String objWords[], GameWorld game) {
		GameObject obj = super.parse(objWords, game);
		if(obj == null && objWords.length == 3 && matchObjectName(objWords[1])) {
			Position pos = Position.stringToPosition(objWords[0]);
			Action dir = Action.parseAction(objWords[2]);
			if(pos != null && dir != null && validDirection(dir)) 
				return newCopy(pos, game, dir);
		}
	return obj;
	}
	
	protected boolean validDirection(Action dir) { return dir == Action.RIGHT || dir == Action.LEFT; }
	
	protected abstract GameObject newCopy(Position pos, GameWorld game, Action action);
	
	public void update() {
		if(this.game.isSolid(this.position.go(Action.DOWN))) {
			this.isFalling = false;
			this.doAction(this.direction);
		}
		else this.freeFalling();
	}
	
	protected void doAction(Action action) {
		this.isFalling = false;
		if(this.game.isSolid(this.position.go(action)) || 
				this.position.go(action).isBorder()) this.direction = action.opposite();
		else {
			this.direction = action;
			super.move(action);
		}
	}
	
	protected boolean freeFalling() {
		boolean freeFalling = false;
		
		if(!this.game.isSolid(this.position.go(Action.DOWN)) && !this.position.isBorder()) {
			freeFalling = true;
			this.isFalling = true;
			super.move(Action.DOWN);
			if(this.position.isBorder()) super.dead();
		}
	return freeFalling;
	}
	
	protected boolean isInDirection(Action direction) {
	return this.direction == direction;
	}
	
	protected void up() {
		this.isFalling = false;
		if(!headCollision()) this.move(Action.UP);
		else this.move(Action.STOP);
	}
	
	protected boolean headCollision() {
		Position pos = this.position.go(Action.UP);
		return this.game.isSolid(pos) || position.isBorder();
	}
	
	protected void stop() {
		this.isFalling = false;
		this.direction = Action.STOP;
	}
	
	protected boolean isFalling() {
	return this.isFalling;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(this.position.toString()).append(Messages.SPACE).append(this.direction.toString()).append(Messages.SPACE);
		if(this.isAlive()) stringBuilder.append("ALIVE");
		else stringBuilder.append("DEAD");
	return stringBuilder.toString();
	}
}
