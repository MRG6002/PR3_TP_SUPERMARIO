// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.control.commands;

import java.util.Arrays;

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
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	@Override
	public void execute(GameModel game, GameView view) {
		if(game.addObject(objWords)) view.showGame();
		else view.showError(Messages.INVALID_GAME_OBJECT.formatted(String.join(" ", this.objWords)));
	}

	@Override
	public Command parse(String[] commandWords) {
		Command command = null;
		if(2 <= commandWords.length && this.matchCommandName(commandWords[0])) {
			command = new AddObjectCommand(Arrays.copyOfRange(commandWords, 1, commandWords.length));
		}
		return command;
	}
	
	@Override
	public String helpText(){
	return super.helpText() + Messages.LINE_TAB.formatted(Messages.TAB + Messages.COMMAND_ADDOBJECT_OBJECT_DESCRIPTION);
	}
}
