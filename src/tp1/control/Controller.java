// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.control;

import tp1.control.commands.CommandGenerator;

import tp1.exceptions.CommandException;

import tp1.logic.GameModel;

import tp1.view.GameView;

/**
 *  Accepts user input and coordinates the game execution logic
 */
public class Controller {
	private GameModel game;
	private GameView view;

	public Controller(GameModel game, GameView view) {
		this.game = game;
		this.view = view;
	}

	/**
	 * Runs the game logic, coordinate Model(game) and View(view)
	 */	
	public void run() {
		view.showWelcome();
		view.showGame();
		while (!game.isFinished()) {
			try {	
				String[] words = view.getPrompt();
				
				CommandGenerator.parse(words).execute(game, view);
			}
		 	catch(CommandException ce) {
		 		view.showError(ce.getMessage());
		 		
		 		Throwable cause = ce.getCause();
		 		
		 		while(cause != null) {
		 			view.showError(cause.getMessage());
		 			cause = cause.getCause();
		 		}
		 	}
		}
		view.showEndMessage();
	}
}
