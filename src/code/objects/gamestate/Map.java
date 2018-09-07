package code.objects.gamestate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.tiled.TiledMap;

import code.objects.entity.Monster;
import code.render.RenderCountdown;
import code.utils.MonsterRessources;

public class Map {
	private GameState game;
	private TiledMap tiledMap;
	private int waveActual;
	private ArrayList<Point> points = new ArrayList<Point>();
	private ArrayList<Wave> waves = new ArrayList<Wave>();
	private ArrayList<Tower> towers = new ArrayList<Tower>();
	private ArrayList<TowerRessource> towersRessource = new ArrayList<TowerRessource>();
	private MonsterRessources monsterRessources = new MonsterRessources();
	private boolean[][] caseLibreMap;
	
	public Map(GameState game, String path){
		this.game = game;
		this.waveActual = 0;
		try {
			chargementMap(path);
			chargementEtatMap("Calque 4");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void init(String file) throws SlickException {
		
	}
	
	public void render(Graphics g) throws SlickException {
		this.tiledMap.render(0, 0, 0);
		this.tiledMap.render(0, 0, 1);
		this.tiledMap.render(0, 0, 2);
	}
	
	public void renderWaves(Graphics g) throws SlickException {
		this.waves.get(this.waveActual).renderMonstres(g);
	}
	
	public void updateWaves(int delta) throws SlickException {
		this.waves.get(this.waveActual).updateMonstres(delta);
		if(this.waves.get(this.waveActual).nbMonstersAlive() == 0){
			if(this.waveActual < this.waves.size() - 1){
				this.waveActual++;
				this.game.setCountdown(true);
				this.game.getRenderGlobal().addRenderCountdown(new RenderCountdown(this.game, 10, 500));
			}
		}
	}
	
	public void renderTowers(Graphics g) throws SlickException {
		for(int i = 0; i < this.towers.size(); i++){
			this.towers.get(i).render(g);
		}
	}
	
	public void updateTowers(int delta) throws SlickException {
		for(int i = 0; i < this.towers.size(); i++){
			this.towers.get(i).update(delta);
		}
	}
	
	public void chargementMap(String path) throws SlickException{
		String ligne;
		String tmp;
		InputStream ips;
		InputStreamReader ipsr;
		BufferedReader br;
		int nbVagues;
		
		try {
			ips = new FileInputStream(path);
			ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);
			
			ligne = br.readLine();
			if(!ligne.equals("#Tower Defense v0.1")){
				System.out.println("[ERREUR - Map:chargementMap] format de map incompatible !");
			} else {
				System.out.println("[OK - Map:chargementMap] chargement de la map ...");
				ligne = br.readLine();
				tmp = ligne.split(":")[1];
				this.tiledMap = new TiledMap(tmp);
				System.out.println("[OK - Map:chargementMap] chargement du TiledMap");
				ligne = br.readLine();
				System.out.println("[OK - Map:chargementMap] chargement des points ...");
				ligne = br.readLine();
				tmp = ligne.split(":")[1];
				for(int i = 0; i < Integer.valueOf(tmp); i++){
					int x;
					int y;
					
					ligne = br.readLine();
					System.out.println("[OK - Map:chargementMap] chargement du point " + (i + 1) + " ...");
					ligne = br.readLine();
					x = Integer.valueOf(ligne.split(":")[1]);
					ligne = br.readLine();
					y = Integer.valueOf(ligne.split(":")[1]);
					points.add(new Point(x, y));
				}
				
				ligne = br.readLine();
				ligne = br.readLine();
				tmp = ligne.split(":")[1];
				chargementMonstersMap(tmp);
				
				ligne = br.readLine();
				ligne = br.readLine();
				tmp = ligne.split(":")[1];
				chargementTowersMap(tmp);
				
				ligne = br.readLine();
				System.out.println("[OK - Map:chargementMap] chargement des vagues ...");
				ligne = br.readLine();
				nbVagues = Integer.valueOf(ligne.split(":")[1]);
				System.out.println(tmp);
				
				for(int i = 0; i < nbVagues; i++){
					int nbMonstres;
					ArrayList<Monster> monsters = new ArrayList<Monster>();
					ligne = br.readLine();
					System.out.println("[OK - Map:chargementMap] chargement de la vague " + (i + 1) + " ...");
					ligne = br.readLine();
					nbMonstres = Integer.valueOf(ligne.split(":")[1]);
					for(int j = 0; j < nbMonstres; j++){
						int sprite_id;
						int x;
						int y;
						float speed;
						int lifeFull;
						int lifeActual;
						int gold;
						int direction;
						
						ligne = br.readLine();
						System.out.println("[OK - Map:chargementMap] chargement du monstre " + (j + 1) + " de la vague " + (i + 1) + " ...");
						ligne = br.readLine();
						sprite_id = Integer.valueOf(ligne.split(":")[1]);
						ligne = br.readLine();
						x = Integer.valueOf(ligne.split(":")[1]);
						ligne = br.readLine();
						y = Integer.valueOf(ligne.split(":")[1]);
						ligne = br.readLine();
						speed = Float.valueOf(ligne.split(":")[1]);
						ligne = br.readLine();
						lifeFull = Integer.valueOf(ligne.split(":")[1]);
						ligne = br.readLine();
						lifeActual = Integer.valueOf(ligne.split(":")[1]);
						ligne = br.readLine();
						gold = Integer.valueOf(ligne.split(":")[1]);
						ligne = br.readLine();
						direction = Integer.valueOf(ligne.split(":")[1]);
						monsters.add(new Monster(this.game, this, sprite_id, x, y, speed, lifeFull, lifeActual, gold, direction));
					}
					this.waves.add(new Wave(monsters, 30));
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("[ERREUR - Map:chargementMap] la map est introuvable !");
		} catch (IOException e) {
			System.out.println("[ERREUR - Map:chargementMap] impossible de lire la map !");
		} 
	}
	
	private void chargementMonstersMap(String path){
		String ligne;
		InputStream ips;
		InputStreamReader ipsr;
		BufferedReader br;
		int nbMonstresSprite;
		
		try {
			ips = new FileInputStream(path);
			ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);
			
			ligne = br.readLine();
			System.out.println("[OK - Map:chargementMap] chargement des sprites de monstre ...");
			ligne = br.readLine();
			nbMonstresSprite = Integer.valueOf(ligne.split(":")[1]);
			for(int i = 0; i < nbMonstresSprite; i++){
				String sprite;
				int id;
				int sprite_w;
				int sprite_h;
				int tabRepereMonstre[][] = new int[4][3];
				
				ligne = br.readLine();
				System.out.println("[OK - Map:chargementMap] chargement du sprite du monstre " + (i + 1) + "...");
				ligne = br.readLine();
				id = Integer.valueOf(ligne.split(":")[1]);
				ligne = br.readLine();
				sprite = ligne.split(":")[1];
				ligne = br.readLine();
				sprite_w = Integer.valueOf(ligne.split(":")[1]);
				ligne = br.readLine();
				sprite_h = Integer.valueOf(ligne.split(":")[1]);
				for(int j = 0; j < 4; j++){
					int start;
					int end;
					int pos;
					
					ligne = br.readLine();
					ligne = br.readLine();
					start = Integer.valueOf(ligne.split(":")[1]);
					ligne = br.readLine();
					end = Integer.valueOf(ligne.split(":")[1]);
					ligne = br.readLine();
					pos = Integer.valueOf(ligne.split(":")[1]);
					tabRepereMonstre[j][0] = start;
					tabRepereMonstre[j][1] = end;
					tabRepereMonstre[j][2] = pos;
				}
				this.monsterRessources.addRessource(id, sprite, sprite_w, sprite_h, tabRepereMonstre);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("[ERREUR - Map:chargementMonstersMap] le fichier monsters est introuvable !");
		} catch (IOException e) {
			System.out.println("[ERREUR - Map:chargementMonstersMap] impossible de lire le fichier monsters !");
		}
	}
	
	private void chargementTowersMap(String path){
		String ligne;
		InputStream ips;
		InputStreamReader ipsr;
		BufferedReader br;
		int nbTowers;
		
		try {
			ips = new FileInputStream(path);
			ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);
			
			ligne = br.readLine();
			System.out.println("[OK - Map:chargementMap] chargement des informations de towers ...");
			ligne = br.readLine();
			nbTowers = Integer.valueOf(ligne.split(":")[1]);
			for(int i = 0; i < nbTowers; i++){
				int id;
				int nbLevel;
				String sprite;
				int sprite_w;
				int sprite_h;
				TowerRessource tr;
				
				ligne = br.readLine();
				System.out.println("[OK - Map:chargementMap] chargement des informations de tower " + (i + 1));
				ligne = br.readLine();
				id = Integer.valueOf(ligne.split(":")[1]);
				ligne = br.readLine();
				ligne = br.readLine();
				sprite = ligne.split(":")[1];
				ligne = br.readLine();
				sprite_w = Integer.valueOf(ligne.split(":")[1]);
				ligne = br.readLine();
				sprite_h = Integer.valueOf(ligne.split(":")[1]);			
				ligne = br.readLine();
				ligne = br.readLine();
				nbLevel = Integer.valueOf(ligne.split(":")[1]);
				tr = new TowerRessource(id, sprite, sprite_w, sprite_h, nbLevel);
				for(int j = 0; j < nbLevel; j++){
					int prix;
					int degat;
					int range;
					int timeShoot;
					
					ligne = br.readLine();
					ligne = br.readLine();
					prix = Integer.valueOf(ligne.split(":")[1]);
					ligne = br.readLine();
					degat = Integer.valueOf(ligne.split(":")[1]);
					ligne = br.readLine();
					range = Integer.valueOf(ligne.split(":")[1]);
					ligne = br.readLine();
					timeShoot = Integer.valueOf(ligne.split(":")[1]);
					tr.addLevel(j, prix, degat, range, timeShoot);
				}
				this.towersRessource.add(id, tr);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("[ERREUR - Map:chargementTowersMap] le fichier towers est introuvable !");
		} catch (IOException e) {
			System.out.println("[ERREUR - Map:chargementTowersMap] impossible de lire le fichier towers !");
		}
	}
	
	public boolean caseLibre(int x, int y){
		int tileW = this.tiledMap.getTileWidth();
	    int tileH = this.tiledMap.getTileHeight();
		int xEntier = x / tileW;
		int yEntier = y / tileH;
		
		if(xEntier < this.caseLibreMap.length && yEntier < this.caseLibreMap[0].length){
			return this.caseLibreMap[xEntier][yEntier];
		}
		return false;
	}
	
	private void chargementEtatMap(String logic){
		this.caseLibreMap = new boolean[this.tiledMap.getWidth()][this.tiledMap.getHeight()];
		int logicLayer = this.tiledMap.getLayerIndex(logic);
		for(int i = 0; i < this.tiledMap.getWidth(); i++){
			for(int j = 0; j < this.tiledMap.getHeight(); j++){
				Image tile = this.tiledMap.getTileImage(i, j, logicLayer);
				this.caseLibreMap[i][j] = tile == null;
			}
		}
	}
	
	public void updateSelectedTowers(int x, int y){
		int nbTower = this.towers.size();
		
		for(int i = 0; i < nbTower; i++){
			int xTowerMin = this.towers.get(i).getX() * this.towers.get(i).getSprite_w();
			int yTowerMin = this.towers.get(i).getY() * this.towers.get(i).getSprite_h();
			int xTowerMax = xTowerMin + this.towers.get(i).getSprite_w();
			int yTowerMax = yTowerMin + this.towers.get(i).getSprite_h();
			
			this.towers.get(i).setSelected(false);
			if(x >= xTowerMin && x < xTowerMax && y >= yTowerMin && y < yTowerMax){
				this.towers.get(i).setSelected(true);
				for(int j = i + 1; j < nbTower; j++){
					this.towers.get(j).setSelected(false);
				}
				i = nbTower;
			}
		}
	}

	public TiledMap getTiledMap() {
		return tiledMap;
	}

	public ArrayList<Point> getPoints() {
		return points;
	}

	public ArrayList<Wave> getWaves() {
		return waves;
	}

	public ArrayList<Tower> getTowers() {
		return towers;
	}

	public void setTowers(ArrayList<Tower> towers) {
		this.towers = towers;
	}

	public boolean[][] getCaseLibreMap() {
		return caseLibreMap;
	}

	public void setCaseLibreMap(boolean[][] caseLibreMap) {
		this.caseLibreMap = caseLibreMap;
	}

	public int getWaveActual() {
		return waveActual;
	}

	public void setWaveActual(int waveActual) {
		this.waveActual = waveActual;
	}

	public MonsterRessources getMonsterRessources() {
		return monsterRessources;
	}

	public void setMonsterRessources(MonsterRessources monsterRessources) {
		this.monsterRessources = monsterRessources;
	}

	public ArrayList<TowerRessource> getTowersRessource() {
		return towersRessource;
	}

	public void setTowersRessource(ArrayList<TowerRessource> towersRessource) {
		this.towersRessource = towersRessource;
	}
}
