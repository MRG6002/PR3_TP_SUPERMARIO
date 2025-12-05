package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.exceptions.GameModelException;

import tp1.logic.gameobjects.Box;
import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Goomba;
import tp1.logic.gameobjects.Land;
import tp1.logic.gameobjects.Mario;
import tp1.logic.gameobjects.Mushroom;

public class LevelGameConfiguration implements GameConfiguration {
	private int time;
	private int points;
	private int lives;
	private int level;
	private Mario mario;
	private List<GameObject> npcObjects;
	
	public LevelGameConfiguration(int level, GameWorld game) throws GameModelException {
		if(level < -1 || 2 < level) throw new GameModelException();
		if(level == -1) this.initLevel_1(game);
		else if(level == 0) this.initLevel0(game);
		else if(level == 1) this.initLevel1(game);
		else this.initLevel2(game);
	}
	
	public LevelGameConfiguration(GameWorld game) {
		this.initLevel1(game);
	}	
	
	@Override
	public int getRemainingTime() {
	return this.time;
	}

	@Override
	public int points() {
	return this.points;
	}

	@Override
	public int numLives() {
	return this.lives;
	}
	
	@Override
	public int getLevel() {
	return this.level;
	}

	@Override
	public Mario getMario() {
		Mario mario = null;
		
		if(this.mario != null) mario = this.mario.newCopy();
	return mario;
	}

	@Override
	public List<GameObject> getNPCObjects() {
		List<GameObject> aux = new ArrayList<>();
		
		for(GameObject gameObject: this.npcObjects) aux.addLast(gameObject.newCopy());
	return aux;
	}
	
	private void initLevel_1(GameWorld game) {
		this.time = 100;
		this.points = 0;
		this.lives = 3;	
		this.level = -1;
		this.mario = null;
		this.npcObjects = new ArrayList<>();
	}
	
	private void initLevel0(GameWorld game) {
		this.initLevel_1(game);
		this.level = 0;
		// 1. Lands
		for(int col = 0; col < 15; col++) {
			this.npcObjects.add(new Land(new Position(13, col), game));
			this.npcObjects.add(new Land(new Position(14, col), game));		
		}
		this.npcObjects.add(new Land(new Position(Game.DIM_Y - 3, 9), game));
		this.npcObjects.add(new Land(new Position(Game.DIM_Y - 3, 12), game));
		for(int col = 17; col < Game.DIM_X; col++) {
			this.npcObjects.add(new Land(new Position(Game.DIM_Y - 2, col), game));
			this.npcObjects.add(new Land(new Position(Game.DIM_Y - 1, col), game));		
		}
		this.npcObjects.add(new Land(new Position(9, 2), game));
		this.npcObjects.add(new Land(new Position(9, 5), game));
		this.npcObjects.add(new Land(new Position(9, 6), game));
		this.npcObjects.add(new Land(new Position(9, 7), game));
		this.npcObjects.add(new Land(new Position(5, 6), game));

		int tamX = 8;
		int posIniX = Game.DIM_X - 3 - tamX, posIniY = Game.DIM_Y - 3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col + 1; fila++) this.npcObjects.add(new Land(new Position(posIniY - fila, posIniX + col), game));
		}
		// 2. ExitDoor
		this.npcObjects.add(new ExitDoor(new Position(Game.DIM_Y - 3, Game.DIM_X - 1), game));
		// 3. Mario
		this.mario = new Mario(new Position(Game.DIM_Y - 3, 0), game);
		// 4. Goomba
		this.npcObjects.add(new Goomba(new Position(0, 19), game));
	}
	
	private void initLevel1(GameWorld game) {
		this.initLevel0(game);
		this.level = 1;
		// 4. Goombas adicionales
		this.npcObjects.add(new Goomba(new Position(4, 6), game));
		this.npcObjects.add(new Goomba(new Position(12, 6), game));
		this.npcObjects.add(new Goomba(new Position(12, 8), game));
		this.npcObjects.add(new Goomba(new Position(10, 10), game));
		this.npcObjects.add(new Goomba(new Position(12, 11), game));
		this.npcObjects.add(new Goomba(new Position(12, 14), game));
	}
	
	private void initLevel2(GameWorld game) {
		this.initLevel1(game);
		this.level = 2;
		// 5. Mushrooms
		this.npcObjects.add(new Mushroom(new Position(12, 8), game));
		this.npcObjects.add(new Mushroom(new Position(2, 20), game));
		// 6. Box
		this.npcObjects.add(new Box(new Position(9, 4), game));
	}
}
