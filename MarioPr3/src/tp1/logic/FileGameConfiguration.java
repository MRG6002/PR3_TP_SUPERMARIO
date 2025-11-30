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
			if(objectWords[1].equalsIgnoreCase(Messages.MARIO_NAME) 
					|| objectWords[1].equalsIgnoreCase(Messages.MARIO_SHORTCUT)) {
				mario = (Mario) GameObjectFactory.parse(objectWords, game);
			}
			else {
				objects.add(GameObjectFactory.parse(objectWords, game));
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
	public Mario getMario() {return this.mario;}

	@Override
	public List<GameObject> getNPCObjects() {return this.objects;}

}
