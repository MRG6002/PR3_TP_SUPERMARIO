// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.control.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.Action;
import tp1.logic.GameModel;

import tp1.view.GameView;
import tp1.view.Messages;

public class ActionCommand extends AbstractCommand {
	private static final String NAME = Messages.COMMAND_ACTION_NAME;
	private static final String SHORTCUT = Messages.COMMAND_ACTION_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_ACTION_DETAILS;
	private static final String HELP = Messages.COMMAND_ACTION_HELP;
	private List<Action> actionList;

	public ActionCommand(List<Action> actionList) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		if(actionList != null) this.actionList = actionList;
		else this.actionList = new ArrayList<>();
	}

	ActionCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.actionList = new ArrayList<>();
	}

	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException{
		for(Action action: this.actionList) {
			game.addAction(action);
		}
		game.update();
		view.showGame();
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException{
		Command command = null;
		if(1 < commandWords.length && this.matchCommandName(commandWords[0])) {
			List<Action> actionList = new ArrayList<>();
			ActionCommand.parseActions(Arrays.copyOfRange(commandWords, 1, commandWords.length), actionList);
			command = new ActionCommand(actionList);
		}
	return command;
	}
	
	private static List<Action> parseActions(String[] commandWords, List<Action> actionList) {
		Action action = null;
		for(String s: commandWords) {
			action = Action.parseAction(s);
			actionList.addLast(action);
		}
		return actionList;
	}
}
