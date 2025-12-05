// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.control.commands;

import tp1.exceptions.CommandParseException;

import tp1.view.Messages;

public abstract class NoParamsCommand extends AbstractCommand {

	public NoParamsCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	@Override
	public NoParamsCommand parse(String[] commandWords) throws CommandParseException {
		if (1 < commandWords.length && matchCommandName(commandWords[0])) throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		 	
		NoParamsCommand noParamsCommand = null;
		 
		if (commandWords.length == 1 && matchCommandName(commandWords[0])) noParamsCommand = this;
	return noParamsCommand;
	}
}
