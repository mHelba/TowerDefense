package code.objects.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Player {
	private int lifeMax;
	private int lifeActual;
	private int gold;
	private int cptGold = 0;
	private int goldReady = 50;
	private int income = 1;
	
	public Player(int life, int gold){
		this.lifeMax = life;
		this.lifeActual = life;
		this.gold = gold;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		this.cptGold++;
		if(this.cptGold > this.goldReady){
			this.cptGold = this.goldReady;
		}
		if(this.cptGold == this.goldReady){
			this.gold += this.income;
			this.cptGold = 0;
		}
	}
	
	public boolean lost(){
		return this.lifeActual == 0;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getLifeMax() {
		return lifeMax;
	}

	public int getLifeActual() {
		return lifeActual;
	}

	public void setLifeMax(int lifeMax) {
		this.lifeMax = lifeMax;
	}

	public void setLifeActual(int lifeActual) {
		this.lifeActual = lifeActual;
	}
}
