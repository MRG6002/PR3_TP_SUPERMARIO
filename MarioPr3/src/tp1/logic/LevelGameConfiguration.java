package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.exceptions.CommandExecuteException;
import tp1.logic.gameobjects.Box;
import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Goomba;
import tp1.logic.gameobjects.Land;
import tp1.logic.gameobjects.Mario;
import tp1.logic.gameobjects.Mushroom;

public class LevelGameConfiguration implements GameConfiguration{
	private List<GameObject> objects;
	private Mario mario;
	private int time;
	private int points;
	private int lives;
	
	public LevelGameConfiguration(int level, GameWorld game) throws CommandExecuteException{
		switch (level) {
		case 0:{this.initLevel0(game);} break;
		case 1:{this.initLevel1(game);} break;
		case 2:{this.initLevel2(game);} break;
		case -1:{this.initLevelMinus1(game);} break;
		default: throw new CommandExecuteException();
		}
	}
	
	public LevelGameConfiguration(GameWorld game) {
		this.initLevel1(game);
	}
	
	@Override
	public int getRemainingTime() {return this.time;}

	@Override
	public int points() {return this.points;}

	@Override
	public int numLives() {return this.lives;}

	@Override
	public Mario getMario() {return this.mario;}

	@Override
	public List<GameObject> getNPCObjects() {return this.objects;}
	
	
	
	private void initLevelMinus1(GameWorld game) {
		this.time = 100;
		this.lives = 3;
		this.points = 0;
		this.objects = new ArrayList<>();
	}
	
	private void initLevel0(GameWorld game) {
		this.time = 100;
		// 1. Lands
		this.objects = new ArrayList<>();
		for(int col = 0; col < 15; col++) {
			this.objects.add(new Land(new Position(13, col), game));
			this.objects.add(new Land(new Position(14, col), game));		
		}
		this.objects.add(new Land(new Position(Game.DIM_Y - 3, 9), game));
		this.objects.add(new Land(new Position(Game.DIM_Y - 3, 12), game));
		for(int col = 17; col < Game.DIM_X; col++) {
			this.objects.add(new Land(new Position(Game.DIM_Y - 2, col), game));
			this.objects.add(new Land(new Position(Game.DIM_Y - 1, col), game));		
		}
		this.objects.add(new Land(new Position(9, 2), game));
		this.objects.add(new Land(new Position(9, 5), game));
		this.objects.add(new Land(new Position(9, 6), game));
		this.objects.add(new Land(new Position(9, 7), game));
		this.objects.add(new Land(new Position(5, 6), game));

		int tamX = 8;
		int posIniX = Game.DIM_X - 3 - tamX, posIniY = Game.DIM_Y - 3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col + 1; fila++) {
				this.objects.add(new Land(new Position(posIniY - fila, posIniX + col), game));
			}
		}
		// 2. ExitDoor
		this.objects.add(new ExitDoor(new Position(Game.DIM_Y - 3, Game.DIM_X - 1), game));
		// 3. Mario
		this.mario = new Mario(new Position(Game.DIM_Y - 3, 0), game);
		// 4. Goombas
		this.objects.add(new Goomba(new Position(0, 19), game));
	}
	
	private void initLevel1(GameWorld game) {
		this.initLevel0(game);
		// 4. Goombas adicionales
		this.objects.add(new Goomba(new Position(12, 6), game));
		this.objects.add(new Goomba(new Position(12, 8), game));
		this.objects.add(new Goomba(new Position(10, 10), game));
		this.objects.add(new Goomba(new Position(12, 11), game));
		this.objects.add(new Goomba(new Position(12, 14), game));
		this.objects.add(new Goomba(new Position(4, 6), game));
	}
	
	private void initLevel2(GameWorld game) {
		this.initLevel1(game);
		//5. Mushrooms
		this.objects.add(new Mushroom(new Position(2, 20), game));
		this.objects.add(new Mushroom(new Position(12, 8), game));
		//6. Boxes
		this.objects.add(new Box(new Position(9, 4), game));
	}

}
