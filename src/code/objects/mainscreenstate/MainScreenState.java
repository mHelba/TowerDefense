package code.objects.mainscreenstate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import code.MainGame;
import code.utils.EnumGameState;

public class MainScreenState extends BasicGameState {
	public static final EnumGameState state = EnumGameState.MAINSCREEN;
	private MainGame mainGame;
	private Image background;
	private KeyController kController = new KeyController(this);
	
	public MainScreenState(MainGame game){
		this.mainGame = game;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game){
		this.mainGame.setActualState(EnumGameState.MAINSCREEN);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.background = new Image("ressources/background/sand_template.jpg");
		container.getInput().addKeyListener(kController);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		String texteAccueil = "Appuyez sur une touche pour démarrer le jeu";
		
		background.draw(0, 0, container.getWidth() - 1, container.getHeight() - 1);
		g.drawString(texteAccueil, container.getWidth() / 2 - g.getFont().getWidth(texteAccueil) / 2, container.getHeight() / 2 - g.getFont().getHeight(texteAccueil) / 2);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {}

	@Override
	public int getID() {
		return state.getID();
	}

	public static EnumGameState getState() {
		return state;
	}

	public MainGame getMainGame() {
		return mainGame;
	}
}
