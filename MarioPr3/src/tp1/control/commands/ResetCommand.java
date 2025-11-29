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
	private boolean levelExists;
	private int level;

	public ResetCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.levelExists = false;
	}
	
	public ResetCommand(int level) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.level = level;
		this.levelExists = true;
	}

	@Override
	public void execute(GameModel game, GameView view) {
		if(!this.levelExists) {
			game.reset();
			view.showGame();
		}
		else if(game.reset(level)) view.showGame();
		else view.showError(Messages.INVALID_LEVEL_NUMBER);
	}

	@Override
	public Command parse(String[] commandWords) {
		Command command = null;
		
		if((commandWords.length == 1 || commandWords.length == 2) && this.matchCommandName(commandWords[0])) {
			if(commandWords.length == 1) command = new ResetCommand();
			else command = new ResetCommand(Integer.parseInt(commandWords[1]));
		}
	return command;
	}
}
