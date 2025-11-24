// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.logic.Action;
import tp1.logic.ActionList;
import tp1.logic.GameWorld;

import tp1.view.Messages;

public class Mario extends MovingObject {
	private boolean big;
	private boolean headCollision;
	private ActionList actionList;

	public Mario(Position position, GameWorld game) {
		super(position, game, Action.RIGHT, "mario", "m");
		this.headCollision = false;
		this.big = true;
		this.actionList = new ActionList();
	}
	
	private Mario(Position position, GameWorld game, Action dir) {
		super(position, game, dir, "mario", "m");
		this.headCollision = false;
		this.big = true;
		this.actionList = new ActionList();
	}
	
	private Mario(Position position, GameWorld game, Action dir, boolean big) {
		super(position, game, dir, "mario", "m");
		this.headCollision = false;
		this.big = big;
		this.actionList = new ActionList();
	}
	
	Mario() {
		super(null, null, Action.RIGHT, "mario", "m");
	}
	
	public void connect() {
		this.game.connect(this);
	}
	
	//movimiento de Mario
	@Override
	public boolean isInPosition(Position position) {
	return super.isInPosition(position) || (this.big && (this.position.go(Action.UP).equals(position)));
	}
	
	@Override
	public void update() {
		Position position = this.position.go(Action.STOP); // Guardamos la position actual
		this.playerMovement();
		if(this.position.equals(position) && (!super.isInDirection(Action.STOP) || !this.game.isSolid(this.position.go(Action.DOWN)))) { // Si Mario no se ha movido tras ejecutar las acciones, se aplica su movimiento automático
			super.update();
			if(!this.isAlive()) this.game.marioDead(); //por si mario se cae por precipicio al actualizar
		}
	}
	
	private void playerMovement() {
		for(Action action: this.actionList) {
			this.headCollision = false;
			if(action == Action.DOWN) {
				if(this.game.isSolid(this.position.go(Action.DOWN))) super.stop();
				else {
					while(super.freeFalling()) this.game.doInteractionsFrom(this);
					if(!super.isAlive()) this.game.marioDead();
				}
			}
			else if(action == Action.UP) {
				this.headCollision = super.up(big);
				this.game.doInteractionsFrom(this);
			}
			else if (action == Action.STOP){
				super.stop();
			}
			else {
				super.doAction(action);
				this.game.doInteractionsFrom(this);
			}
		}
		this.actionList.clear();
	}

	public void addAction(Action action) {
		this.actionList.addLast(action);
	}
	
	public int count(Action action) {
		int n = 0;
		for(Action aux: this.actionList) {
			if(aux == action) n++;
		}
	return n;
	}
	
	public boolean isOpposite(Action action) {
		for(Action aux: this.actionList) if(aux == Action.opposite(action)) return true;
	return false;
	}
	
	//interacciones de Mario
	public  boolean interactWith(GameItem item) {
		boolean interaction = item.isInPosition(this.position) || (this.big && item.isInPosition(this.position.go(Action.UP)));
		if(interaction) {item.receiveInteraction(this);}
		return interaction;
	}
	
	@Override
	public  boolean receiveInteraction(ExitDoor obj) {
		this.game.marioExited();
	return true;
	}
	
	@Override
	public  boolean receiveInteraction(Mushroom obj) {
		boolean interaction = obj.isInPosition(this.position) || (this.big && obj.isInPosition(this.position.go(Action.UP)));
		if(interaction) {
			this.big = true;
		}
	return interaction;
	}
	
	@Override
	public  boolean receiveInteraction(Box obj) {
		boolean interaction = this.headCollision && (obj.isInPosition(this.position.go(Action.UP)) || (this.big && obj.isInPosition(this.position.go(Action.UP).go(Action.UP))));
		if(interaction) { 
			obj.receiveInteraction(this);
			this.game.addPoints(50);
		}
	return interaction;
	}
	
	@Override
	public  boolean receiveInteraction(Goomba obj) {
		boolean interaction = obj.isInPosition(this.position) || (this.big && obj.isInPosition(this.position.go(Action.UP)));
		if(interaction) {
			if(this.big) {
				if(!super.isFalling()) this.big = false;
			}
			else {
				if(!super.isFalling()) {
					this.dead();
					this.game.marioDead();
				}
			}
			this.game.addPoints(100);
		}
	return interaction;
	}
	
	//strings y parse de Mario
	@Override
	public Mario parse(String objWords[], GameWorld game) {
		Mario mario = null;	
		if(objWords.length >= 2 && matchObjectName(objWords[1])) {
			Position pos = Position.stringToPosition(objWords[0]);
			if(pos != null) {
				if(objWords.length == 2) mario = new Mario(pos, game);
				else if(objWords.length >= 3) {
					Action dir = Action.parseAction(objWords[2]);
					boolean correctDir = (dir != Action.DOWN && dir != Action.UP);
					if(correctDir && objWords.length == 3) {
						mario = new Mario(pos, game, dir);
					}
					else if(correctDir && objWords.length == 4) {
						if (isBig(objWords[3])) mario = new Mario(pos, game, dir, true);
						else if(isSmall(objWords[3])) mario = new Mario(pos, game, dir, false);
					}
				}
			}
		}
	return mario;
	}
	
	private boolean isBig(String string) {
		return string.equalsIgnoreCase("big") || string.equalsIgnoreCase("b");
	}

	private boolean isSmall(String string) {
		return string.equalsIgnoreCase("small") || string.equalsIgnoreCase("s");
	}
	
	@Override
	public String getIcon() {
		StringBuilder stringBuilder = new StringBuilder();
		
		if(this.isInDirection(Action.STOP)) stringBuilder.append(Messages.MARIO_STOP);
		else if(this.isInDirection(Action.LEFT)) stringBuilder.append(Messages.MARIO_LEFT);
		else stringBuilder.append(Messages.MARIO_RIGHT);
	return stringBuilder.toString(); 
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("MARIO: ").append(super.toString());
		if(this.big) stringBuilder.append("BIG ");
		else stringBuilder.append("NOT BIG ");
		if(this.isFalling()) stringBuilder.append("FALLING ");
		else stringBuilder.append("NOT FALLING ");
		stringBuilder.append("NOT SOLID");		
	return stringBuilder.toString();
	}
}
