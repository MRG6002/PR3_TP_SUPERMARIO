// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;

import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.logic.gameobjects.Mario;

import tp1.view.Messages;

public class Game implements GameModel, GameStatus, GameWorld {
	public static final int DIM_X = 30;
	public static final int DIM_Y = 15;
	
	private GameConfiguration fileLoader;
	private int time;
	private int points;
	private int lives;
	private Mario mario;
	private GameObjectContainer gameObjectContainer;
	private boolean exit;
	private boolean victory;

	public Game(int level) {
		try {
			this.gameObjectContainer = new GameObjectContainer();
			this.fileLoader = new LevelGameConfiguration(level, this);
		}
		catch(GameModelException gme) {
			this.fileLoader = new LevelGameConfiguration(this);
		}
		this.points = this.fileLoader.points();
		this.lives = this.fileLoader.numLives();
		this.loadConfiguration();
		this.exit = false;
		this.victory = false;
	}
	
	public String positionToString(int col, int row) {
		Position position = new Position(row, col);
		
	return this.gameObjectContainer.postitionToString(position);
	}

	public boolean playerWins() {
	return this.victory;
	}

	public boolean playerLoses() {
	return this.time == 0 || this.lives == 0;
	}
	
	// Not mandatory but recommended
	public void exit() {
		this.exit = true;
	}
	
	public boolean isFinished() {
	return this.playerWins() || this.playerLoses() || this.exit == true;
	}
	
	public void reset() {
		this.loadConfiguration();
		if(this.fileLoader.getLevel() == -1) {
			this.points = this.fileLoader.points();
			this.lives = this.fileLoader.numLives();
		}
	}
	
	public void reset(int level) throws GameModelException {
		this.fileLoader = new LevelGameConfiguration(level, this);
		this.reset();
	}
	
	public boolean isSolid(Position position) {
	return this.gameObjectContainer.isSolid(position);
	}
	
	public void marioDead() {
		this.lives--;
		if(0 < this.lives) this.reset();
	}
	
	public void update() {
		this.time--;
		this.gameObjectContainer.update();
	}
	
	public int remainingTime() {
	return this.time;
	}

	public int points() {
	return this.points;
	}

	public int numLives() {
	return this.lives;
	}
	
	public void addObject(String[] objectWords) throws OffBoardException, ObjectParseException {
		Mario mario = new Mario(null, null).parse(objectWords, this);
		GameObject gameObject = mario;
		
		if(mario != null) {
			if(this.mario != null) this.mario.dead();
			this.mario = mario;
		}
		else gameObject = GameObjectFactory.parse(objectWords, this);
		this.gameObjectContainer.add(gameObject);
	}
	
	public void add(GameObject gameObject) {
		this.gameObjectContainer.add(gameObject);
	}
	
	public void addAction(Action action) {
		if(this.mario != null) this.mario.addAction(action);
	}
	
	public void marioExited() {
		this.points += this.time * 10;
		this.time = 0;
		this.victory = true;
	}
	
	public void addPoints(int points) {
		this.points += points;
	}
	 
	public void doInteractionsFrom(GameItem gameItem) {
		this.gameObjectContainer.doInteraction(gameItem);
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(this.time).append(Messages.SPACE).append(this.points).append(Messages.SPACE).append(this.lives).append(Messages.LINE_SEPARATOR);
		stringBuilder.append(this.gameObjectContainer.toString());
	return stringBuilder.toString();
	}
	
	public void save(String fileName) throws GameModelException {
		try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
			bufferedWriter.write(this.toString());
		} 
		catch(IOException ioe) {
			throw new GameModelException(ioe);
		}
	}
	
	private void loadConfiguration() {
		this.time = this.fileLoader.getRemainingTime();
		this.mario = this.fileLoader.getMario();
		this.gameObjectContainer = new GameObjectContainer();
		if(this.mario != null) this.add(this.mario);
		for(GameObject gameObject: this.fileLoader.getNPCObjects()) this.add(gameObject);
	}
	
	public void load(String fileName) throws GameLoadException {
		this.fileLoader = new FileGameConfiguration(fileName, this);
		this.points = this.fileLoader.points();
		this.lives = this.fileLoader.numLives();
		this.loadConfiguration();
	}
}
