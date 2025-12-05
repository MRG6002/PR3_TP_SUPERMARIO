// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.logic.gameobjects.Mario;
import tp1.view.Messages;

public class Game implements GameModel, GameStatus, GameWorld {
	public static final int DIM_X = 30;
	public static final int DIM_Y = 15;
	
	private GameConfiguration fileloader;
	
	private GameObjectContainer gameObjectContainer;
	private Mario mario;
	private int time;
	private int points;
	private int lives;
	private boolean exit;
	private boolean victory;

	public Game(int nLevel) {
		try {
			this.fileloader = new LevelGameConfiguration(nLevel, this);
		} catch (GameModelException gme) {
			this.fileloader = new LevelGameConfiguration(this);
		} finally {
			loadFileInfo();
			this.points = 0;
			this.lives = 3;
			this.exit = false;
			this.victory = false;
		}
	}
	
	public void reset() {
		loadFileInfo();
		if(this.fileloader.resetsAll()) {
			this.points = this.fileloader.points();
			this.lives = this.fileloader.numLives();
		}
	}
	
	public void reset(int level) throws GameModelException {
		this.fileloader = new LevelGameConfiguration(level, this);
		reset();
		this.exit = false;
		this.victory = false;
	}
	
	public void save(String fileName) throws GameModelException {
		try (BufferedWriter out = 
				new BufferedWriter(
						new FileWriter(fileName));){
			out.write(this.time + Messages.SPACE + this.points + Messages.SPACE + this.lives); out.newLine();
			out.write(this.gameObjectContainer.toString());
		} catch (IOException e) {
			throw new GameModelException(Messages.FILE_NOT_OPENED, e);
		}
	}
	
	public void load(String fileName) throws GameLoadException {
		this.fileloader = new FileGameConfiguration(fileName, this);
		this.lives = this.fileloader.numLives();
		this.points = this.fileloader.points();
		loadFileInfo();
	}
	
	private void loadFileInfo() {
		this.gameObjectContainer = new GameObjectContainer();
		this.time = this.fileloader.getRemainingTime();
		this.mario = this.fileloader.getMario(); 
		if(this.mario != null) add(this.mario);
		List<GameObject> aux = this.fileloader.getNPCObjects();
		for(GameObject o: aux) add(o);
	}
	
	//funciones generales
	public String positionToString(int col, int row) {
		return this.gameObjectContainer.positionToString(new Position(row, col));
	}

	public boolean playerWins() {
	return this.victory;
	}

	public boolean playerLoses() {
	return this.time == 0 || this.lives == 0;
	}
	
	public void exit() {
		this.exit = true;
	}
	
	public boolean isFinished() {
	return this.playerWins() || this.playerLoses() || this.exit == true;
	}
	
	public boolean isSolid(Position position) {
	return this.gameObjectContainer.isSolid(position);
	}
	
	public void update() {
		this.time--;
		this.gameObjectContainer.update();
	}
	
	public int getRemainingTime() {
	return this.time;
	}

	public int points() {
	return this.points;
	}

	public int numLives() {
	return this.lives;
	}
	
	//Mario
	public void marioDead() {
		this.lives--;
		if(0 < this.lives) this.reset();
	}
	
	public void addAction(Action action) {
		if(this.mario != null) this.mario.addAction(action);
	}
	
	public void marioExited() {
		this.points += this.time * 10;
		this.time = 0;
		this.victory = true;
	}
	
	public void addPoints(int num) {
		this.points += num;
	}
	 
	public void doInteractionsFrom(GameObject obj) {
		this.gameObjectContainer.doInteractionsFrom(obj);
	}
	
	public void addObject(String [] objWords) throws OffBoardException, ObjectParseException{
		if(this.mario == null) this.mario = new Mario(new Position(0,0), this);
		Mario mario = this.mario.parse(objWords, this);
		GameObject object = mario;
		
		if(mario != null) {
			this.mario.dead();
			this.mario = mario;
		}
		else object = GameObjectFactory.parse(objWords, this);
		this.add(object);
	}
	
	@Override
	public void add(GameObject obj) {
		this.gameObjectContainer.add(obj);
	}

	//toString
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("GAME: ").append(Messages.SPACE).append(this.time).append("s ").append(this.points).append("pts ").append(this.lives).append("lives ");
		if(this.exit) stringBuilder.append("EXITED");
		else stringBuilder.append("NOT EXITED YET");
		if(this.victory) stringBuilder.append(Messages.LINE.formatted(" VICTORY"));
		else stringBuilder.append(Messages.LINE.formatted(" NO VICTORY YET"));
		stringBuilder.append(this.gameObjectContainer.toString());
	return stringBuilder.toString();
	}
}