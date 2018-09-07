package code.objects.gamestate;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class TowerRessource {
	private int id;
	private SpriteSheet sprite;
	private int sprite_w;
	private int sprite_h;
	private int nbLevel;
	private ArrayList<TowerInfos> levels;
	
	TowerRessource(int id, String spritesheet, int sprite_w, int sprite_h, int nbLevel){
		this.id = id;
		try {
			this.sprite = new SpriteSheet(spritesheet, sprite_w, sprite_h);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.sprite_w = sprite_w;
		this.sprite_h = sprite_h;
		this.nbLevel = nbLevel;
		this.levels = new ArrayList<TowerInfos>();
	}
	
	public void addLevel(int indice, int prix, int degat, int range, int timeShoot){
		if(indice < this.nbLevel){
			System.out.println(prix + " " + degat + " " + range + " " + timeShoot);
			TowerInfos ti = new TowerInfos(prix, degat, range, timeShoot);
			this.levels.add(indice, ti);
		}
	}
	
	public int getId() {
		return id;
	}

	public int getNbLevel() {
		return nbLevel;
	}

	public ArrayList<TowerInfos> getLevels() {
		return levels;
	}

	public SpriteSheet getSprite() {
		return sprite;
	}

	public int getSprite_w() {
		return sprite_w;
	}

	public int getSprite_h() {
		return sprite_h;
	}

	class TowerInfos {
		private int prix;
		private int degat;
		private int range;
		private int timeShoot;
		
		TowerInfos(int prix, int degat, int range, int timeShoot){
			this.prix = prix;
			this.degat = degat;
			this.range = range;
			this.timeShoot = timeShoot;
		}

		public int getPrix() {
			return prix;
		}

		public int getDegat() {
			return degat;
		}

		public int getRange() {
			return range;
		}

		public int getTimeShoot() {
			return timeShoot;
		}
	}
}
