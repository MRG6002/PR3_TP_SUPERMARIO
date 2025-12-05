// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.control.commands;

import java.util.ArrayList;
import java.util.List;

import tp1.exceptions.ActionParseException;
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
		this.actionList = actionList;
	}
	
	ActionCommand() {
		this(null);
	}

	@Override
	public void execute(GameModel game, GameView view) {
		for(Action action: this.actionList) game.addAction(action);
		game.update();
		view.showGame();	
	}
	
	@Override
	public ActionCommand parse(String[] commandWords) throws CommandParseException {
		ActionCommand actionCommand = null;
		
		if(0 < commandWords.length && super.matchCommandName(commandWords[0])) {
			if(commandWords.length == 1) throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			else {
				int i = 1; // commandWords[0] == action
				List<Action> actionList = new ArrayList<>();
				
				while(i < commandWords.length) {
					try {
						actionList.addLast(Action.parseAction(commandWords[i++]));
					} 
					catch(ActionParseException ape) {
					}
				}
				if(actionList.size() == 0) throw new CommandParseException(Messages.EMPTY_ACTION_LIST);
				actionCommand = new ActionCommand(actionList);
			}
		}
	return actionCommand;
	}
}
