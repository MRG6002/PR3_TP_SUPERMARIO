// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic.gameobjects;

import java.util.Arrays;

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
	
	protected void doAction(Action action) {
		this.isFalling = false;
		if(this.isSolid(this.position, action) || !isValidPosition(this.position, action)) this.direction = Action.opposite(action);
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
			this.isFalling = false;
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
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(this.position.toString()).append(Messages.SPACE).append(this.direction.toString()).append(Messages.SPACE).append("NOT SOLID").append(Messages.SPACE);
		if(this.isAlive()) stringBuilder.append("ALIVE");
		else stringBuilder.append("DEAD");
	return stringBuilder.toString();
	}
	
	private int sizedTo(String[] objectWords) {
		int to = objectWords.length;
		
		if(to == 3) to--;
	return to;
	}
	
	protected boolean validDirection(Action direction) {
	return direction == Action.LEFT || direction == Action.RIGHT;
	}
	
	private void updateDirection(Action direction) {
		this.direction = direction;
	}
	
	@Override
	public MovingObject parse(String[] objectWords, GameWorld game) {
		int to = this.sizedTo(objectWords);
		MovingObject movingObject = (MovingObject) super.parse(Arrays.copyOfRange(objectWords, 0, to), game);
		
		if(movingObject != null && objectWords.length == 3) {
			Action direction = Action.parseAction(objectWords[2]);
				
			if(validDirection(direction)) movingObject.updateDirection(direction);
			else movingObject = null;
		}
	return movingObject;
	}
	
	protected abstract Position headPosition();
}
