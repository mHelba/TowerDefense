package code.render;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import code.objects.gamestate.GameState;

public class RenderGlobal {
	private GameState game;
	private ArrayList<RenderIncomeMonster> renderIncomeMonster = new ArrayList<RenderIncomeMonster>();
	private ArrayList<RenderCountdown> renderCountdown = new ArrayList<RenderCountdown>();
	
	public RenderGlobal(GameState game){
		this.game = game;
	}
	
	public void addRenderIncomeMonster(RenderIncomeMonster r){
		this.renderIncomeMonster.add(r);
	}
	
	public void addRenderCountdown(RenderCountdown r){
		this.renderCountdown.add(r);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		renderListIncomeMonster(container, game, g);
		renderListCountdown(container, game, g);
		renderHUD(container, game, g);
		if(this.game.getPlayer().lost()){
			RenderCinematics.renderEndGame(container, game, g, "Défaite ...");
		} else {
			if(this.game.getMap().getWaveActual() == this.game.getMap().getWaves().size() - 1 && this.game.getMap().getWaves().get(this.game.getMap().getWaveActual()).nbMonstersAlive() == 0){
				RenderCinematics.renderEndGame(container, game, g, "Victoire !");
			}
		}
	}
	
	private void renderListIncomeMonster(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		for(int i = 0; i < this.renderIncomeMonster.size(); i++){
			this.renderIncomeMonster.get(i).render(container, game, g);
		}
	}
	
	private void renderListCountdown(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		for(int i = 0; i < this.renderCountdown.size(); i++){
			this.renderCountdown.get(i).render(container, game, g);
		}
	}
	
	private void renderHUD(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		String life = "Vie restante : " + this.game.getPlayer().getLifeActual() + " / " + this.game.getPlayer().getLifeMax();
		String gold = "Gold : " + this.game.getPlayer().getGold();
		String wave = "Vague : " + (this.game.getMap().getWaveActual() + 1) + " / " + this.game.getMap().getWaves().size();
		String monstre = "Monstres restants : " + this.game.getMap().getWaves().get(this.game.getMap().getWaveActual()).nbMonstersAlive() + " / " + this.game.getMap().getWaves().get(this.game.getMap().getWaveActual()).getMonsters().size();
		int widthLife = g.getFont().getWidth(life);
		int widthGold = g.getFont().getWidth(gold);
		int widthWave = g.getFont().getWidth(wave);
		int widthMonstre = g.getFont().getWidth(monstre);
		int height = g.getFont().getHeight(life);
		int widthMax;
		ArrayList<Integer> liste = new ArrayList<Integer>();
		
		liste.add(widthLife);
		liste.add(widthGold);
		liste.add(widthWave);
		liste.add(widthMonstre);
		widthMax = maxValue(liste);
		
		g.setColor(Color.white);
		g.drawString(life, container.getWidth() - widthMax - 10, 10);
		g.drawString(gold, container.getWidth() - widthMax - 10, 10 * 2 + height);
		g.drawString(wave, container.getWidth() - widthMax - 10, 10 * 3 + 2 * height);
		g.drawString(monstre, container.getWidth() - widthMax - 10, 10 * 4 + 3 * height);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		updateListIncomeMonster(container, game, delta);
		updateListCountdown(container, game, delta);
	}
	
	private void updateListIncomeMonster(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		for(int i = 0; i < this.renderIncomeMonster.size(); i++){
			this.renderIncomeMonster.get(i).update(container, game, delta);
			if(this.renderIncomeMonster.get(i).isEnd()){
				this.renderIncomeMonster.remove(i);
			}
		}
	}
	
	private void updateListCountdown(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		for(int i = 0; i < this.renderCountdown.size(); i++){
			this.renderCountdown.get(i).update(container, game, delta);
			if(this.renderCountdown.get(i).isEnd()){
				this.renderCountdown.remove(i);
			}
		}
	}
	
	private int maxValue(ArrayList<Integer> liste){
		int max;
		
		if(liste.size() > 0){
			max = liste.get(0);
			for(int i = 0; i < liste.size(); i++){
				if(liste.get(i) > max){
					max = liste.get(i);
				}
			}
			return max;
		} else {
			return -1;
		}
	}
}
