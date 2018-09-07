package code;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import code.objects.gamestate.GameState;
import code.objects.mainscreenstate.MainScreenState;
import code.utils.EnumGameState;

public class MainGame extends StateBasedGame{
	private EnumGameState actualState = EnumGameState.MAINSCREEN;
	
	public MainGame(String titre) {
		super(titre);
	}

	public static void main(String[] args) {
		String titre = "Tower Defense - 0.1 - Helba";
		AppGameContainer app;
		try {
			app = new AppGameContainer(new MainGame(titre), 960, 640, false);
			app.setTargetFrameRate(60);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			System.out.println("Erreur d'initialisation de Slick2D !");
		}
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new MainScreenState(this));
		addState(new GameState(this));
	}

	public EnumGameState getActualState() {
		return actualState;
	}

	public void setActualState(EnumGameState actualState) {
		this.actualState = actualState;
	}
}
