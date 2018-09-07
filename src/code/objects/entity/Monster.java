package code.objects.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import code.objects.gamestate.GameState;
import code.objects.gamestate.Map;

public class Monster {
	private float x;
	private float y;
	private float speed;
	private int sprite_id;
	private int lifeFull;
	private int lifeActual;
	private int direction;
	private int lastPoint;
	private int gold_income;
	private boolean isAlive;
	private boolean canGo;
	private Animation[] animations = new Animation[4];
	private GameState game;
	private Map map;
	
	public Monster(GameState game, Map map, int sprite_id, int x, int y, float speed, int lifeFull, int lifeActual, int gold, int direction){
		this.game = game;
		this.map = map;
		this.x = x;
		this.y = y;
		this.sprite_id = sprite_id;
		this.speed = speed;
		this.lifeFull = lifeFull;
		this.lifeActual = lifeActual;
		this.gold_income = gold;
		this.canGo = false;
		this.isAlive = true;
		this.direction = direction;
		this.animations = this.map.getMonsterRessources().getRessources().get(sprite_id).getAnimations();
	}
	
	public void init() throws SlickException {
		
	}
	
	public void render(Graphics g) throws SlickException {
		if(this.canGo && this.isAlive){
			g.drawAnimation(this.animations[this.direction], (int) this.x - this.map.getMonsterRessources().getRessources().get(this.sprite_id).getSprite_w() / 2, (int) this.y - this.map.getMonsterRessources().getRessources().get(this.sprite_id).getSprite_h() / 2);
			if(this.game.isShowLife()){
				this.renderLife(g);
			}
		}
	}
	
	private void renderLife(Graphics g) throws SlickException {
		float pourcentage = (float) this.lifeActual / (float) this.lifeFull;
		int widthLife = (int) ((float) this.map.getMonsterRessources().getRessources().get(this.sprite_id).getSprite_w() * pourcentage);
		g.setLineWidth(1);
		if(pourcentage > 0.5){
			g.setColor(Color.green);
		} else if(pourcentage > 0.25){
			g.setColor(Color.orange);
		} else {
			g.setColor(Color.red);
		}
		g.fillRect(this.x - this.map.getMonsterRessources().getRessources().get(this.sprite_id).getSprite_w() / 2, this.y - this.map.getMonsterRessources().getRessources().get(this.sprite_id).getSprite_h() / 2, widthLife, 5);
		g.setColor(Color.black);
		g.drawRect(this.x - this.map.getMonsterRessources().getRessources().get(this.sprite_id).getSprite_w() / 2, this.y - this.map.getMonsterRessources().getRessources().get(this.sprite_id).getSprite_h() / 2, this.map.getMonsterRessources().getRessources().get(this.sprite_id).getSprite_w(), 5);
	}
	
	// G = 0, H = 1, D = 2, B = 3 
	public void update(int delta) throws SlickException {
		if(this.canGo && this.isAlive){
			if(this.lastPoint < this.map.getPoints().size() - 1){
				Point actual = this.map.getPoints().get(this.lastPoint);
				Point next = this.map.getPoints().get(this.lastPoint + 1);
				this.direction = findDirection(actual, next);
				
				if(direction == 0){
					this.x -= 5 * speed;
					if(this.x <= next.getX()){
						this.x = next.getX();
						this.lastPoint++;
					}
				} else if(direction == 1){
					this.y -= 5 * speed;
					if(this.y <= next.getY()){
						this.y = next.getY();
						this.lastPoint++;
					}
				} else if(direction == 2){
					this.x += 5 * speed;
					if(this.x >= next.getX()){
						this.x = next.getX();
						this.lastPoint++;
					}
				} else {
					this.y += 5 * speed;
					if(this.y >= next.getY()){
						this.y = next.getY();
						this.lastPoint++;
					}
				}
				
				if(this.lastPoint == this.map.getPoints().size() - 1){
					int newLife = this.game.getPlayer().getLifeActual() > 0 ? this.game.getPlayer().getLifeActual() - 1 : 0;
					this.game.getPlayer().setLifeActual(newLife);
					this.isAlive = false;
				}
			}
		}
	}
	
	// G = 0, H = 1, D = 2, B = 3 
	public int findDirection(Point actual, Point next){
		if(actual.getX() == next.getX()){
			if(actual.getY() < next.getY()){
				return 3;
			} else {
				return 1;
			}
		} else {
			if(actual.getX() < next.getX()){
				return 2;
			} else {
				return 0;
			} 
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getSprite_w() {
		return this.map.getMonsterRessources().getRessources().get(this.sprite_id).getSprite_w();
	}

	public int getSprite_h() {
		return this.map.getMonsterRessources().getRessources().get(this.sprite_id).getSprite_h();
	}

	public int getLifeActual() {
		return lifeActual;
	}

	public void setLifeActual(int lifeActual) {
		this.lifeActual = lifeActual;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public int getGold_income() {
		return gold_income;
	}

	public void setGold_income(int gold_income) {
		this.gold_income = gold_income;
	}

	public boolean isCanGo() {
		return canGo;
	}

	public void setCanGo(boolean canGo) {
		this.canGo = canGo;
	}
}
