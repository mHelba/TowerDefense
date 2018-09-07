package code.objects.mainscreenstate;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import code.utils.EnumGameState;

public class KeyController implements KeyListener {
	private MainScreenState game;
	
	public KeyController(MainScreenState game){
		this.game = game;
	}

	@Override
	public void inputEnded() {}

	@Override
	public void inputStarted() {}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void setInput(Input input) {}

	@Override
	public void keyPressed(int arg0, char arg1) {}

	@Override
	public void keyReleased(int key, char arg1) {
		if(this.game.getMainGame().getActualState() == MainScreenState.getState() && key != Input.KEY_F9){
			this.game.getMainGame().enterState(EnumGameState.GAME.getID());
		}
	}
}
