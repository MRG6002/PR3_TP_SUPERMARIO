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
	
	public MovingObject(Position position, String name, String shortcut, GameWorld game, Action direction) {
		super(position, name, shortcut, game);
		this.direction = direction;
		this.isFalling = false;
	}
	
	@Override
	public boolean isSolid() {
	return false;
	}
	
	protected boolean isInDirection(Action direction) {
	return this.direction == direction;
	}
	
	protected boolean isSolid(Position position, Action direction) {
	return this.game.isSolid(position.go(direction));
	}
	
	@Override
	public void update() {
		if(this.isSolid(this.position, Action.DOWN)) this.doAction(this.direction);
		else this.freeFalling();	
	}
	
	private boolean isValidPosition(Position position, Action direction) {
	return position.go(direction).isValid();
	}
	
	protected void doAction(Action action) { // MODIFICAR JUNTO A BOX?
		this.isFalling = false;
		if(this.isSolid(this.position, action) || this.isSolid(headPosition(), action) || !isValidPosition(this.position, action)) this.direction = Action.opposite(action);
		else {
			this.direction = action;
			super.move(action);
		}
	}
	
	protected boolean freeFalling() {
		boolean freeFalling = false;
		
		if(!this.isSolid(this.position, Action.DOWN) && this.position.isValid()) {
			freeFalling = true;
			this.isFalling = true;
			super.move(Action.DOWN);
			if(!this.position.isValid()) super.dead(); // Si sale del tablero por abajo, muere
		}
	return freeFalling;
	}
	
	protected boolean up() {
		boolean collidedUp = false;
		
		this.isFalling = false;
		if(!this.isSolid(headPosition(), Action.UP) && this.isValidPosition(headPosition(), Action.UP)) this.move(Action.UP);
		else collidedUp = true;
	return collidedUp;
	}
	
	protected void stop() {
		this.isFalling = false;
		this.direction = Action.STOP;
	}
	
	protected boolean isFalling() {
	return this.isFalling;
	}
	
	protected boolean validDirection(Action direction) {
	return direction == Action.LEFT || direction == Action.RIGHT;
	}
	
	private void updateDirection(Action direction) {
		this.direction = direction;
	}
	
	@Override
	public MovingObject parse(String[] objectWords, GameWorld game) throws OffBoardException, ObjectParseException {
		try {
			MovingObject movingObject = (MovingObject) super.parse(objectWords, game);
			
			if(movingObject != null && 3 <= objectWords.length) {
				Action direction = Action.parseAction(objectWords[2]);
				
				if(validDirection(direction)) movingObject.updateDirection(direction);
				else throw new ObjectParseException(Messages.INVALID_MOVING_OBJECT_DIRECTION.formatted(String.join(" ", objectWords)));
			}
			return movingObject;
		}
		catch(ActionParseException ape){
			throw new ObjectParseException(Messages.UNKNOWN_MOVING_OBJECT_DIRECTION.formatted(String.join(" ", objectWords)), ape);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(super.toString()).append(Messages.SPACE).append(this.direction.toString());
	return stringBuilder.toString();
	};
	
	@Override
	public MovingObject newCopy() {
		MovingObject movingObject = (MovingObject) super.newCopy();
		
		movingObject.updateDirection(this.direction);
	return movingObject;
	}
	
	protected abstract Position headPosition();
}
