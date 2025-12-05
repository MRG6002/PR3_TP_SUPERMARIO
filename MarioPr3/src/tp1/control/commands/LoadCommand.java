// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameLoadException;
import tp1.logic.GameModel;

import tp1.view.GameView;
import tp1.view.Messages;

public class LoadCommand extends AbstractCommand {
	private static final String NAME = Messages.COMMAND_LOAD_NAME;
	private static final String SHORTCUT = Messages.COMMAND_LOAD_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_LOAD_DETAILS;
	private static final String HELP = Messages.COMMAND_LOAD_HELP;
	private String fileName;
	
	LoadCommand() {
		this(null);
	}
	
	public LoadCommand(String fileName) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.fileName = fileName;
	}

	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException{
		try {
			game.load(this.fileName);
			view.showGame();
		} catch (GameLoadException gle) {
			throw new CommandExecuteException(Messages.UNABLE_TO_LOAD.formatted(this.fileName), gle);
		}
	}

	@Override
	public LoadCommand parse(String[] commandWords) throws CommandParseException{
		if ((commandWords.length > 2 || commandWords.length == 1) && matchCommandName(commandWords[0]))
	 		throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);

		LoadCommand command = null;
		if(commandWords.length == 2 && this.matchCommandName(commandWords[0])) {
			command = new LoadCommand(commandWords[1]);
		}
		return command;
	}
}
