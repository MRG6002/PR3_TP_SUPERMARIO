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
	public GameObject parse(String objWords[], GameWorld game) throws OffBoardException, ObjectParseException {
		try {
			
			GameObject obj = super.parse(objWords, game);
			if(objWords.length >= 2) {
				Action dir = Action.parseAction(objWords[2]);
				if(validDirection(dir)) this.direction = dir;
				else throw new ObjectParseException(Messages.INVALID_MO_DIRECTION.formatted(String.join(" ", objWords)));
			}
			return obj;
		} catch (ActionParseException ape) {
			throw new ObjectParseException(Messages.UNKNOWN_MO_DIRECTION.formatted(String.join(" ", objWords)), ape);
		}
		
	}
	
	protected boolean validDirection(Action dir) { return dir == Action.RIGHT || dir == Action.LEFT; }
	
	protected abstract GameObject newObject(Position pos, GameWorld game, Action action);
	
	@Override 
	public GameObject newCopy(Position pos, GameWorld game) {
		return this.newCopy(pos, game, this.direction);
	}
	
	public GameObject newCopy(Position pos, GameWorld game, Action direction) {
		return this.newObject(pos, game, direction);
	}
	
	public Mario marioNewCopy() {
		return this.marioNewCopy(this.position.copy(), this.game, this.direction);
	}
	
	protected Mario marioNewCopy(Position pos, GameWorld game, Action direction) {return null;}
	
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
	return super.toString() + Messages.SPACE + this.direction.toString();
	}
}
