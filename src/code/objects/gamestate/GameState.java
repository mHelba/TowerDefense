package code.objects.gamestate;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import code.MainGame;
import code.objects.entity.Player;
import code.objects.gamestate.KeyController;
import code.render.RenderCinematics;
import code.render.RenderCountdown;
import code.render.RenderGlobal;
import code.utils.EnumGameState;

public class GameState extends BasicGameState {
	public static final EnumGameState state = EnumGameState.GAME;
	private GameContainer container;
	private MainGame mainGame;
	private Map map;
	private Player player;
	private RenderGlobal renderGlobal;
	private boolean paused = false;
	private boolean towerPressed = false;
	private boolean showLife = true;
	private boolean countdown = true;
	private int xMouse;
	private int yMouse;
	private int gameCpt = 0;
	private KeyController kController = new KeyController(this);
	private MouseController mController = new MouseController(this);

	public GameState(MainGame game) {
		this.mainGame = game;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game){
		this.mainGame.setActualState(EnumGameState.GAME);
		this.container = container;
		this.map = new Map(this, "ressources/maps/map1.txt");
		this.player = new Player(5, 100);
		this.renderGlobal = new RenderGlobal(this);
		container.getInput().addKeyListener(kController);
		container.getInput().addMouseListener(mController);
		this.renderGlobal.addRenderCountdown(new RenderCountdown(this, 10, 500));
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		int xEntier = this.xMouse / this.map.getTiledMap().getTileWidth();
		int yEntier = this.yMouse / this.map.getTiledMap().getTileHeight();
		g.setColor(Color.black);
		g.fillRect(0, 0, container.getWidth(), container.getHeight());
		this.map.render(g);
		this.map.renderWaves(g);
		this.map.renderTowers(g);
		this.renderGlobal.render(container, game, g);
		if(this.towerPressed){
			g.setLineWidth(4);
			if(this.map.caseLibre(this.xMouse, this.yMouse)){
				g.setColor(Color.black);
			} else {
				g.setColor(Color.red);
			}
			g.drawRect(xEntier * this.map.getTiledMap().getTileWidth(), yEntier * this.map.getTiledMap().getTileHeight(), this.map.getTiledMap().getTileWidth(), this.map.getTiledMap().getTileHeight());
		}
		if(this.paused){
			RenderCinematics.renderPause(container, game, g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(!this.paused){
			if(!this.countdown){
				this.map.updateWaves(delta);
				this.map.updateTowers(delta);
				this.player.update(container, game, delta);
			}
			this.renderGlobal.update(container, game, delta);
			if(!this.countdown){
				this.gameCpt++;
			}
		}
	}
	
	@Override
	public int getID() {
		return state.getID();
	}

	public static EnumGameState getState() {
		return state;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	public MainGame getMainGame() {
		return mainGame;
	}

	public boolean isTowerPressed() {
		return towerPressed;
	}

	public void setTowerPressed(boolean towerPressed) {
		this.towerPressed = towerPressed;
	}

	public int getxMouse() {
		return xMouse;
	}

	public void setxMouse(int xMouse) {
		this.xMouse = xMouse;
	}

	public int getyMouse() {
		return yMouse;
	}

	public void setyMouse(int yMouse) {
		this.yMouse = yMouse;
	}

	public int getGameCpt() {
		return gameCpt;
	}

	public void setGameCpt(int gameCpt) {
		this.gameCpt = gameCpt;
	}

	public boolean isShowLife() {
		return showLife;
	}

	public void setShowLife(boolean showLife) {
		this.showLife = showLife;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public RenderGlobal getRenderGlobal() {
		return renderGlobal;
	}

	public void setRenderGlobal(RenderGlobal renderGlobal) {
		this.renderGlobal = renderGlobal;
	}

	public GameContainer getContainer() {
		return container;
	}

	public void setContainer(GameContainer container) {
		this.container = container;
	}

	public boolean isCountdown() {
		return countdown;
	}

	public void setCountdown(boolean countdown) {
		this.countdown = countdown;
	}
}
