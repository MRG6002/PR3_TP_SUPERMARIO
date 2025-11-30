package tp1.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
		String firstLine = null;
		try (BufferedReader in = 
				new BufferedReader(
						new FileReader(fileName));){
			
			firstLine = in.readLine();
			String[] parameters = firstLine.split(" ");
			if(parameters.length != 3) throw new GameLoadException(Messages.INCORRECT_GAME_STATUS.formatted(firstLine));
			this.time = Integer.parseInt(parameters[0]);
			this.points = Integer.parseInt(parameters[1]);
			this.lives = Integer.parseInt(parameters[2]);
			
			String objectWords = in.readLine();
			while(objectWords != null) {
				if(objectWords.toLowerCase().contains(" m ") || objectWords.toLowerCase().contains(" mario ")) {
					mario = (Mario) GameObjectFactory.parse(parameters, game);
				}
				else objects.add(GameObjectFactory.parse(parameters, game));
				objectWords = in.readLine();
			}
			
		} catch (IOException e) {
			throw new GameLoadException(Messages.FILE_NOT_FOUND.formatted(fileName), e);
		} catch (NumberFormatException nfe) {
			throw new GameLoadException(Messages.INCORRECT_GAME_STATUS.formatted(firstLine), nfe);
        } catch (ObjectParseException | OffBoardException e) {
        	throw new GameLoadException(Messages.INVALID_GAME_CONFIG.formatted(fileName), e);
        } catch(Exception e) {}
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
