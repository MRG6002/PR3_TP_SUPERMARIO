// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
import tp1.logic.GameModel;

import tp1.view.GameView;
import tp1.view.Messages;

public class SaveCommand extends AbstractCommand {
	private static final String NAME = Messages.COMMAND_SAVE_NAME;
	private static final String SHORTCUT = Messages.COMMAND_SAVE_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_SAVE_DETAILS;
	private static final String HELP = Messages.COMMAND_SAVE_HELP;
	private String fileName;
	
	public SaveCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	public SaveCommand(String fileName) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.fileName = fileName;
	}

	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException{
		try {
			game.save(this.fileName);
			view.showMessage(Messages.COMMAND_SAVE_OK.formatted(this.fileName));
		} catch (GameModelException gme) {
			throw new CommandExecuteException(Messages.INVALID_LEVEL_NUMBER);
		}
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException{
		if ((commandWords.length > 2 || commandWords.length == 1) && matchCommandName(commandWords[0]))
	 		throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);

		Command command = null;
		if(commandWords.length == 2 && this.matchCommandName(commandWords[0])) {
			command = new SaveCommand(commandWords[1]);
		}
		return command;
	}
}
