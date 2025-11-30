// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic.gameobjects;

import tp1.logic.Position;

import java.util.Arrays;

import tp1.logic.Action;
import tp1.logic.ActionList;
import tp1.logic.GameWorld;

import tp1.view.Messages;

public class Mario extends MovingObject {
	private static final String NAME = Messages.GAMEOBJECT_MARIO_NAME;
	private static final String SHORTCUT = Messages.GAMEOBJECT_MARIO_SHORTCUT;
	
	private boolean big;
	private boolean collidedUp;
	private ActionList actionList;
	
	private Mario(Position position, GameWorld game, Action direction, boolean big) {
		super(position, NAME, SHORTCUT, game, direction);
		this.big = big;
		this.collidedUp = false;
		this.actionList = new ActionList();
	}
	
	public Mario(Position position, GameWorld game) {
		this(position, game, Action.RIGHT, true);
	}
	
	Mario() {
		this(null, null, null, true);
	}

	@Override
	protected Position headPosition() {
		Position position = this.position.go(Action.STOP);
		
		if(this.big) position = this.position.go(Action.UP);
	return position;
	}
	
	@Override
	public boolean isInPosition(Position position) {
	return super.isInPosition(position) || (this.isAlive() && this.headPosition().equals(position));
	}
	
	@Override
	public void update() {
		Position position = this.position.go(Action.STOP);

		this.playerMovement();
		if(this.position.equals(position)) { // Si Mario no se ha movido tras ejecutar las acciones, se aplica su movimiento automático
			super.update();
			if(!this.position.isValid()) this.game.marioDead();
		}
	}
	
	@Override
	public String getIcon() {
		StringBuilder stringBuilder = new StringBuilder();
		
		if(this.isInDirection(Action.STOP)) stringBuilder.append(Messages.MARIO_STOP);
		else if(this.isInDirection(Action.LEFT)) stringBuilder.append(Messages.MARIO_LEFT);
		else stringBuilder.append(Messages.MARIO_RIGHT); // this.direction == Action.RIGHT (nunca a ser UP o DOWN)
	return stringBuilder.toString(); 
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("MARIO: ").append(super.toString());
		if(this.big) stringBuilder.append("BIG ");
		else stringBuilder.append("NOT BIG ");
		if(this.isFalling()) stringBuilder.append("FALLING");
		else stringBuilder.append("NOT FALLING");	
	return stringBuilder.toString();
	}
	
	private void playerMovement() {
		for(Action action: this.actionList) {
			if(action == Action.DOWN) {
				if(super.isSolid(this.position, Action.DOWN)) super.stop();
				else {
					while(super.freeFalling()) {
						if(!super.isAlive()) this.game.marioDead();
					}
				}
			}
			else if(action == Action.UP) {
				this.collidedUp = super.up();
				super.move(Action.STOP);
				this.collidedUp = false;
			}
			else if (action == Action.STOP) super.stop();
			else super.doAction(action); // action == Action.LEFT || action == Action.RIGHT
		}
		this.actionList.clear();
	}

	public void addAction(Action action) {
		this.actionList.addLast(action);
	}

	@Override
	public boolean receiveInteraction(ExitDoor exitDoor) {
	return true;
	}

	@Override
	public boolean receiveInteraction(Goomba goomba) {
		if(this.big) {
			if(!super.isFalling()) this.big = false;
		}
		else {
			if(!super.isFalling()) {
				super.dead();
				this.game.marioDead();
			}
		}
	return true;
	}
	
	@Override
	public boolean receiveInteraction(Mushroom mushroom) {
		if(!this.big) this.big = true;
	return true;
	}
	
	@Override
	public boolean receiveInteraction(Box box) {
	return this.collidedUp;
	}	

	@Override
	Mario newInstance(Position position, GameWorld game) {
	return new Mario(position, game, Action.RIGHT, true);
	}
	
	@Override
	protected boolean validDirection(Action direction) {
	return super.validDirection(direction) || direction == Action.STOP;
	}
	
	private int sizedTo(String[] objectWords) {
		int to = objectWords.length;
		
		if(to == 4) to--;
	return to;
	}
	
	private boolean validSize(String size) {
	return size.equalsIgnoreCase("big") || size.equalsIgnoreCase("b") || size.equalsIgnoreCase("small") || size.equalsIgnoreCase("s");
	}
	
	private void updateSize(String size) {
		if(size.equalsIgnoreCase("small") || size.equalsIgnoreCase("s")) this.big = false;
	}
	
	@Override
	public Mario parse(String[] objectWords, GameWorld game) {
		int to = this.sizedTo(objectWords);
		Mario mario = (Mario) super.parse(Arrays.copyOfRange(objectWords, 0, to), game);
		
		if(mario != null && objectWords.length == 4) {
			if(this.validSize(objectWords[3])) mario.updateSize(objectWords[3]);
			else mario = null;
		}
	return mario;
	}
}
