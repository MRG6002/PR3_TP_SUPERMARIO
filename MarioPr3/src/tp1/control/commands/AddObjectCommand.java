// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.control.commands;

import java.util.Arrays;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class AddObjectCommand extends AbstractCommand{
	private static final String NAME = Messages.COMMAND_ADDOBJECT_NAME;
	private static final String SHORTCUT = Messages.COMMAND_ADDOBJECT_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_ADDOBJECT_DETAILS;
	private static final String HELP = Messages.COMMAND_ADDOBJECT_HELP;
	private String objWords[];

	public AddObjectCommand(String objWords[]) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.objWords = objWords;
	}

	AddObjectCommand() {
		this(null);
	}
	
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException{
		try {
			game.addObject(objWords);
			view.showGame();
		} catch(OffBoardException | ObjectParseException e) {
			throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, e);
		}
	}

	@Override
	public AddObjectCommand parse(String[] commandWords) throws CommandParseException{
		if (commandWords.length <= 2 && matchCommandName(commandWords[0]))
	 		throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		
		AddObjectCommand command = null;
		if(2 < commandWords.length && this.matchCommandName(commandWords[0])) {
			command = new AddObjectCommand(Arrays.copyOfRange(commandWords, 1, commandWords.length));
		}
		return command;
	}
	
	@Override
	public String helpText(){
	return super.helpText() + Messages.LINE_TAB.formatted(Messages.TAB + Messages.COMMAND_ADDOBJECT_OBJECT_DESCRIPTION);
	}
}
