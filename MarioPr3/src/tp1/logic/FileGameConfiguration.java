package tp1.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.logic.gameobjects.Mario;
import tp1.view.Messages;

public class FileGameConfiguration implements GameConfiguration{
	private List<GameObject> objects;
	private Mario mario;
	private int time;
	private int points;
	private int lives;
	
	public FileGameConfiguration(String fileName, GameWorld game) throws GameLoadException{
		this.objects = new ArrayList<>();
		String firstLine = null, objectWord = null;
		String[] parameters = null, objectWords = null;
		try (BufferedReader in = 
				new BufferedReader(
						new FileReader(fileName));){
			
			loadInitialParameters(firstLine, parameters, in);
			loadObjects(objectWord, objectWords, in, game);
			
		} catch (IOException e) {
			throw new GameLoadException(Messages.FILE_NOT_FOUND.formatted(fileName), e);
		} catch (NumberFormatException nfe) {
			throw new GameLoadException(Messages.INCORRECT_GAME_STATUS.formatted(firstLine), nfe);
        } catch (ObjectParseException | OffBoardException e) {
        	throw new GameLoadException(Messages.INVALID_GAME_CONFIG.formatted(fileName), e);
        } catch (GameLoadException gle) {
        	throw gle;
        } catch(Exception e) {
        	throw new GameLoadException(Messages.INCORRECT_GAME_STATUS.formatted(firstLine), e);
        }
	}
	
	private void loadInitialParameters(String firstLine, String[] parameters, BufferedReader in) 
			throws GameLoadException, NumberFormatException, IOException{
		firstLine = in.readLine();
		if(firstLine != null) parameters = firstLine.split(" ");
		if(parameters == null || parameters.length != 3) 
			throw new GameLoadException(Messages.INCORRECT_GAME_STATUS.formatted(firstLine));
		this.time = Integer.parseInt(parameters[0]);
		this.points = Integer.parseInt(parameters[1]);
		this.lives = Integer.parseInt(parameters[2]);
	}
	
	private void loadObjects(String objectWord, String[] objectWords, BufferedReader in, GameWorld game) throws GameLoadException, IOException, ObjectParseException, OffBoardException{
		objectWord = in.readLine();
		while(objectWord != null) {
			objectWords = objectWord.split(" ");
			if(objectWords.length < 2) throw new GameLoadException(Messages.INCORRECT_GAME_STATUS.formatted(objectWord));
			
			if(this.mario == null) this.mario = new Mario(new Position(0,0), game);
			Mario mario = this.mario.parse(objectWords, game);
			GameObject object = mario;
			
			if(mario != null) this.mario = mario;
			else {
				object = GameObjectFactory.parse(objectWords, game);
				this.objects.add(object);
			}
			objectWord = in.readLine();
		}
	}
	
	@Override
	public int getRemainingTime() {return this.time;}

	@Override
	public int points() {return this.points;}

	@Override
	public int numLives() {return this.lives;}
	
	@Override
	public int getLevel() {return 0;}

	@Override
	public Mario getMario() {
		Mario mario = null;
		if(this.mario != null) mario = this.mario.newCopy();
		return mario;
	}

	@Override
	public List<GameObject> getNPCObjects() {
		List<GameObject> aux = new ArrayList<>();
		for(GameObject o: this.objects) aux.add(o.newCopy());
		return aux;
	}
}
