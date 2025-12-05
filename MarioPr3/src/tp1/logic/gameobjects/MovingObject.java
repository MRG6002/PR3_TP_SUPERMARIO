// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic.gameobjects;

import tp1.exceptions.ActionParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
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
	public MovingObject parse(String objWords[], GameWorld game) throws OffBoardException, ObjectParseException {
		try {
			
			MovingObject obj = (MovingObject) super.parse(objWords, game);
			if(obj != null && objWords.length > 2) {
				Action dir = Action.parseAction(objWords[2]);
				if(validDirection(dir)) obj.direction = dir;
				else throw new ObjectParseException(Messages.INVALID_MO_DIRECTION.formatted(String.join(" ", objWords)));
			}
			return obj;
		} catch (ActionParseException ape) {
			throw new ObjectParseException(Messages.UNKNOWN_MO_DIRECTION.formatted(String.join(" ", objWords)), ape);
		}
		
	}
	
	protected boolean validDirection(Action dir) { return dir == Action.RIGHT || dir == Action.LEFT; }
	
	@Override 
	public MovingObject newCopy() {
		MovingObject mo = (MovingObject) super.newCopy();
		mo.direction = this.direction;
		mo.isFalling = mo.isFalling;
		return mo;
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
		if(lateralCollision(action)) this.direction = action.opposite();
		else {
			this.direction = action;
			super.move(action);
		}
	}
	
	protected boolean lateralCollision(Action action) {
		return this.game.isSolid(this.position.go(action)) || 
				this.position.go(action).isBorder();
	}
	
	protected boolean freeFalling() {
		this.isFalling = false;
		
		if(!this.game.isSolid(this.position.go(Action.DOWN)) && !this.position.isBorder()) {
			this.isFalling = true;
			super.move(Action.DOWN);
			if(this.position.isBorder()) super.dead();
		}
	return this.isFalling;
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
	return super.toString() + Messages.SPACE + this.direction.toString();
	}
}
