// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
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
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		try {
			if(this.hasLevel) game.reset(this.level);
			else game.reset();
			view.showGame();
		}
		catch(GameModelException gme) {
			throw new CommandExecuteException(Messages.INVALID_LEVEL_NUMBER);
		}
	}
	
	@Override
	public ResetCommand parse(String[] commandWords) throws CommandParseException {
		try {
			ResetCommand resetCommand = null;
			
			if (0 < commandWords.length && matchCommandName(commandWords[0])) {
				if (commandWords.length == 1) resetCommand = new ResetCommand();
				else if (commandWords.length == 2) resetCommand = new ResetCommand(Integer.parseInt(commandWords[1]));
				else throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			}
			return resetCommand;
		} 
		catch(NumberFormatException nfe) {
			throw new CommandParseException(Messages.LEVEL_NOT_A_NUMBER_ERROR.formatted(commandWords[1]), nfe);
		}
	}
}
