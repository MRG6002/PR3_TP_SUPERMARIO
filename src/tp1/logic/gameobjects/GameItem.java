// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic.gameobjects;

import tp1.logic.Position;

public interface GameItem {
	
	public boolean isSolid();
	public boolean isAlive();
	public boolean isInPosition(Position position);

	public boolean interactWith(GameItem gameItem);

	public boolean receiveInteraction(Land land);
	public boolean receiveInteraction(ExitDoor exitDoor);
	public boolean receiveInteraction(Mario mario);
	public boolean receiveInteraction(Goomba goomba);
	public boolean receiveInteraction(Mushroom mushroom);
	public boolean receiveInteraction(Box box);	
}
