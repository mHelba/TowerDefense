package code.utils;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class MonsterRessources {
	private HashMap<Integer, MonsterInfo> ressources = new HashMap<Integer, MonsterInfo>();
	
	public MonsterRessources(){
		
	}
	
	public void addRessource(int key, String sprite, int sprite_w, int sprite_h, int tabRepere[][]){
		this.ressources.put(key, new MonsterInfo(sprite, sprite_w, sprite_h, tabRepere));
	}
	
	public HashMap<Integer, MonsterInfo> getRessources() {
		return ressources;
	}

	public void setRessources(HashMap<Integer, MonsterInfo> ressources) {
		this.ressources = ressources;
	}

	public class MonsterInfo {
		private int sprite_w;
		private int sprite_h;
		private SpriteSheet spritesheet;
		private Animation[] animations = new Animation[4];
		private int tabRepere[][] = new int[4][3];
		
		public MonsterInfo(String sprite, int sprite_w, int sprite_h, int tabRepere[][]){
			this.sprite_w = sprite_w;
			this.sprite_h = sprite_h;
			this.tabRepere = tabRepere;
			try {
				this.spritesheet = new SpriteSheet(sprite, sprite_w, sprite_h);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			for(int i = 0; i < 4; i++){
				this.animations[i] = loadAnimation(this.spritesheet, this.tabRepere[i][0], this.tabRepere[i][1], this.tabRepere[i][2]);
			}
		}
		
		private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
		    Animation animation = new Animation();
		    for (int x = startX; x < endX; x++) {
		        animation.addFrame(spriteSheet.getSprite(x, y), 60);
		    }
		    return animation;
		}

		public Animation[] getAnimations() {
			return animations;
		}

		public void setAnimations(Animation[] animations) {
			this.animations = animations;
		}

		public int getSprite_w() {
			return sprite_w;
		}

		public void setSprite_w(int sprite_w) {
			this.sprite_w = sprite_w;
		}

		public int getSprite_h() {
			return sprite_h;
		}

		public void setSprite_h(int sprite_h) {
			this.sprite_h = sprite_h;
		}
	}
}
