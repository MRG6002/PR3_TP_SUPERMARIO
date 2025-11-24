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
				this.position.go(action).isBorder()) this.direction = Action.opposite(action);
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
	
	protected boolean up(boolean big) {
		Position position = this.position.go(Action.UP);
		boolean headCollision = false;
		this.isFalling = false;
		if(big) position = position.go(Action.UP);
		
		if(!this.game.isSolid(position) && !position.isBorder()) this.move(Action.UP);
		else headCollision = true;

		return headCollision;
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
