package code.objects.gamestate;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import code.objects.entity.Monster;

public class Wave {
	private int cptWave;
	private int monsterActual;
	private int timeBetweenMonsters;
	private ArrayList<Monster> monsters = new ArrayList<Monster>();
	
	public Wave(ArrayList<Monster> monsters, int timeBetweenMonsters){
		this.cptWave = 0;
		this.monsterActual = 0;
		this.timeBetweenMonsters = timeBetweenMonsters;
		this.monsters = monsters;
	}
	
	public void addMonster(Monster m){
		this.monsters.add(m);
	}
	
	public void renderMonstres(Graphics g) throws SlickException {
		for(int i = 0; i < this.monsters.size(); i++){
			this.monsters.get(i).render(g);
		}
	}
	
	public void updateMonstres(int delta) throws SlickException {
		if(this.monsterActual == 0){
			this.cptWave = this.timeBetweenMonsters;
		} 
		if(this.monsterActual < this.monsters.size()){
			this.cptWave++;
			if(this.cptWave > this.timeBetweenMonsters){
				this.cptWave = this.timeBetweenMonsters;
			}
			if(this.cptWave == this.timeBetweenMonsters){
				this.monsters.get(this.monsterActual++).setCanGo(true);
				this.cptWave = 0;
			}
		}
		for(int i = 0; i < this.monsters.size(); i++){
			this.monsters.get(i).update(delta);
		}
	}
	
	public int nbMonstersAlive(){
		int cpt = 0;
		
		for(int i = 0; i < this.monsters.size(); i++){
			if(this.monsters.get(i).isAlive()){
				cpt++;
			}
		}
		return cpt;
	}

	public ArrayList<Monster> getMonsters() {
		return monsters;
	}
}
