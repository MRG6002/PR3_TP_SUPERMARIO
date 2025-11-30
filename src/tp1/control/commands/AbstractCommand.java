// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.control.commands;

import tp1.view.Messages;

public abstract class AbstractCommand implements Command {
	private final String name;
	private final String shortcut;
	private final String details;
	private final String help;
	
	public AbstractCommand(String name, String shortcut, String details, String help) {
		this.name = name;
		this.shortcut = shortcut;
		this.details = details;
		this.help = help;
	}

	protected String getName() {
	return name;
	}
	
	protected String getShortcut() {
	return shortcut; 
	}
	
	protected String getDetails() {
	return details;
	}
	
	protected String getHelp() {
	return help; 
	}

	protected boolean matchCommandName(String name) {
	return getShortcut().equalsIgnoreCase(name) || getName().equalsIgnoreCase(name);
	}

	@Override
	public String helpText(){
	return Messages.LINE_TAB.formatted(Messages.COMMAND_HELP_TEXT.formatted(getDetails(), getHelp()));
	}
}
