package code.objects.gamestate;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

public class KeyController implements KeyListener {
	private GameState game;
	
	public KeyController(GameState game){
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
		boolean b;
		
		if(this.game.getMainGame().getActualState() == GameState.getState()){
			switch (key) {
			case Input.KEY_T:
				b = this.game.isTowerPressed() ? false : true;
				this.game.setTowerPressed(b);
				break;
			case Input.KEY_TAB:
				b = this.game.isShowLife() ? false : true;
				this.game.setShowLife(b);
				break;
			case Input.KEY_ENTER:
				b = this.game.isPaused() ? false : true;
				this.game.setPaused(b);
				break;
			case Input.KEY_ESCAPE:
				this.game.getContainer().exit();
				break;
			}
		}
	}
}
