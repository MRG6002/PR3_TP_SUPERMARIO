// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.control.commands;

import tp1.logic.GameModel;

import tp1.view.GameView;
import tp1.view.Messages;

public class ResetCommand extends AbstractCommand {
	private static final String NAME = Messages.COMMAND_RESET_NAME;
	private static final String SHORTCUT = Messages.COMMAND_RESET_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_RESET_DETAILS;
	private static final String HELP = Messages.COMMAND_RESET_HELP;
	
	private int level;
	private boolean hasLevel;

	public ResetCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.hasLevel = false; 
	}
	
	public ResetCommand(int level) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.level = level;
		this.hasLevel = true;
	}
	
	@Override
	public void execute(GameModel game, GameView view) {
		if(this.hasLevel) {
			if(game.reset(this.level)) view.showGame();
			else view.showError(Messages.INVALID_LEVEL_NUMBER);
		}
		else {
			game.reset();
			view.showGame();
		}
	}

	@Override
	public Command parse(String[] commandWords) {
		Command command = null;
		
		if((commandWords.length == 1 || commandWords.length == 2) && super.matchCommandName(commandWords[0])) {
			if(commandWords.length == 1) command = new ResetCommand();
			else command = new ResetCommand(Integer.parseInt(commandWords[1])); // commandWords.length == 2
		}
	return command;
	}
}
