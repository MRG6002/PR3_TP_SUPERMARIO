package tp1.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;

import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.logic.gameobjects.Mario;

import tp1.view.Messages;

public class FileGameConfiguration implements GameConfiguration {
	private int time;
	private int points;
	private int lives;
	private Mario mario;
	private List<GameObject> npcObjects;
	
	public FileGameConfiguration(String fileName, GameWorld game) throws GameLoadException {
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
			String line = bufferedReader.readLine();
			String[] words = line.split(" ");
			
			if(this.loadGameState(words)) {
				line = bufferedReader.readLine();
				while(line != null) {
					words = line.split(" ");
					this.loadGameObject(words, game);
					line = bufferedReader.readLine();
				}
			}
			else throw new GameLoadException(Messages.INCORRECT_GAME_STATUS.formatted(line));
		}
		catch(FileNotFoundException fnfe) {
			throw new GameLoadException(Messages.FILE_NOT_FOUND.formatted(fileName), fnfe);
		}
		catch(GameLoadException gle) {
			throw gle;
		}
		catch(Exception e) {
			throw new GameLoadException(Messages.INVALID_FILE_CONFIGURATION.formatted(fileName), e);
		}
	}
	
	private boolean loadGameState(String[] words) throws NumberFormatException {
		boolean loaded = false;
			
		if(words.length == 3) {
			loaded = true;
			this.time = Integer.parseInt(words[0]);
			this.points = Integer.parseInt(words[1]);
			this.lives = Integer.parseInt(words[2]);
			this.mario = null; // Una configuración puede no tener un mario
			this.npcObjects = new ArrayList<>();
		}
	return loaded;
	}
	
	private void loadGameObject(String[] words, GameWorld game) throws OffBoardException, ObjectParseException {
		Mario mario = new Mario(null, null).parse(words, game);
			
		if(mario != null) this.mario = mario;
		else this.npcObjects.addLast(GameObjectFactory.parse(words, game));
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
	return 0; // Número arbitrario
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
}
