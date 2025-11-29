// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.control.commands;

import java.util.Arrays;
import java.util.List;

import tp1.exceptions.CommandParseException;
import tp1.view.Messages;

public class CommandGenerator {
	private static final List<Command> availableCommands = Arrays.asList(
		new AddObjectCommand(),
		new ActionCommand(),
		new UpdateCommand(),
		new ResetCommand(),
		new HelpCommand(),
		new ExitCommand()
	);

	public static Command parse(String[] commandWords) throws CommandParseException{
		Command command = null;
		 
		for (Command aux: availableCommands) {
			command = aux.parse(commandWords);
			if(command != null) return command;
		}
		throw new CommandParseException(Messages.UNKNOWN_COMMAND.formatted(String.join(" ", commandWords)));
	}
		
	public static String commandHelp() {
		StringBuilder commands = new StringBuilder();
		
		commands.append(Messages.HELP_AVAILABLE_COMMANDS).append(Messages.LINE_SEPARATOR);
		for (Command command: availableCommands) commands.append(command.helpText());
	return commands.toString();
	}
}
