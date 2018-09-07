package code.objects.gamestate;

import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

public class MouseController implements MouseListener {
	private GameState game;
	
	public MouseController(GameState game){
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
	public void setInput(Input arg0) {}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		int tileW = this.game.getMap().getTiledMap().getTileWidth();
	    int tileH = this.game.getMap().getTiledMap().getTileHeight();
		int xEntier = x / tileW;
		int yEntier = y / tileH;
		
		if(this.game.isTowerPressed() && !this.game.isPaused()){
			if(this.game.getMap().caseLibre(x, y) && this.game.getPlayer().getGold() >= 100){
				this.game.getMap().getCaseLibreMap()[xEntier][yEntier] = false;
				this.game.getMap().getTowers().add(new Tower(this.game, this.game.getMap(), 0, xEntier, yEntier));
				this.game.getPlayer().setGold(this.game.getPlayer().getGold() - this.game.getMap().getTowersRessource().get(0).getLevels().get(0).getPrix());
			}
		} else if(!this.game.isTowerPressed() && !this.game.isPaused()){
			this.game.getMap().updateSelectedTowers(x, y);
		}
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		this.game.setxMouse(newx);
		this.game.setyMouse(newy);
	}

	@Override
	public void mousePressed(int button, int x, int y) {}

	@Override
	public void mouseReleased(int button, int x, int y) {}

	@Override
	public void mouseWheelMoved(int change) {}

}
