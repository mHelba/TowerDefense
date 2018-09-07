package code.objects.gamestate;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import code.objects.entity.Monster;
import code.render.RenderIncomeMonster;

public class Tower {
	GameState game;
	Map map;
	private int id;
	private int x;
	private int y;
	private int cptShoot = 0;
	private int actualLevel = 0;
	private boolean asTarget = false;
	private boolean fire = false;
	private boolean selected = false;
	private Monster actualTarget = null;
	
	public Tower(GameState game, Map map, int id, int x, int y){
		this.game = game;
		this.map = map;
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public void render(Graphics g) throws SlickException {
		this.map.getTowersRessource().get(this.id).getSprite().draw(this.x * this.map.getTowersRessource().get(this.id).getSprite_w(), this.y * this.map.getTowersRessource().get(this.id).getSprite_h());
		if(this.fire){
			renderFire(g);
		}
		if(this.selected){
			renderSelected(g);
		}
	}
	
	private void renderFire(Graphics g){
		g.setColor(Color.blue);
		g.drawLine(this.x * this.map.getTowersRessource().get(this.id).getSprite_w() + this.map.getTowersRessource().get(this.id).getSprite_w() / 2, this.y * this.map.getTowersRessource().get(this.id).getSprite_h() + this.map.getTowersRessource().get(this.id).getSprite_h() / 2, this.actualTarget.getX(), this.actualTarget.getY());
	}
	
	private void renderSelected(Graphics g){
		g.setColor(new Color(1f, 0f, 0f, 1f));
		g.setLineWidth(1f);
		g.drawRect(this.x * this.map.getTowersRessource().get(this.id).getSprite_w(), this.y * this.map.getTowersRessource().get(this.id).getSprite_h(), this.map.getTowersRessource().get(this.id).getSprite_w(), this.map.getTowersRessource().get(this.id).getSprite_h());
		g.setColor(new Color(0f, 0f, 0f, 0.25f));
		g.fillOval(this.x * this.map.getTowersRessource().get(this.id).getSprite_w() + this.map.getTowersRessource().get(this.id).getSprite_w() / 2 - this.map.getTowersRessource().get(this.id).getLevels().get(this.actualLevel).getRange(), this.y * this.map.getTowersRessource().get(this.id).getSprite_h() + this.map.getTowersRessource().get(this.id).getSprite_h() / 2 - this.map.getTowersRessource().get(this.id).getLevels().get(this.actualLevel).getRange(), this.map.getTowersRessource().get(this.id).getLevels().get(this.actualLevel).getRange() * 2, this.map.getTowersRessource().get(this.id).getLevels().get(this.actualLevel).getRange() * 2);
	}
	
	public void update(int delta) throws SlickException {
		this.fire = false;
		this.cptShoot++;
		
		if(this.cptShoot > this.map.getTowersRessource().get(this.id).getLevels().get(this.actualLevel).getTimeShoot()){
			this.cptShoot = this.map.getTowersRessource().get(this.id).getLevels().get(this.actualLevel).getTimeShoot();
		}
		
		if(this.asTarget){
			if(!this.actualTarget.isAlive()){
				this.asTarget = false;
			} else {
				int xCenterTower = this.x * this.map.getTowersRessource().get(this.id).getSprite_w() + (this.map.getTowersRessource().get(this.id).getSprite_w() / 2);
				int yCenterTower = this.y * this.map.getTowersRessource().get(this.id).getSprite_h() + (this.map.getTowersRessource().get(this.id).getSprite_h() / 2);
				if(distance(xCenterTower, yCenterTower, (int) this.actualTarget.getX(), (int) this.actualTarget.getY()) > this.map.getTowersRessource().get(this.id).getLevels().get(this.actualLevel).getRange()){
					this.asTarget = false;
				}
			}
		}
		
		if(!asTarget){
			this.actualTarget = findTarget();
			if(this.actualTarget != null){
				this.asTarget = true;
			}
		}
		
		if(asTarget && this.cptShoot == this.map.getTowersRessource().get(this.id).getLevels().get(this.actualLevel).getTimeShoot()){
			int life;
			
			this.fire = true;
			this.cptShoot = 0;
			life = this.actualTarget.getLifeActual() - this.map.getTowersRessource().get(this.id).getLevels().get(this.actualLevel).getDegat() >= 0 ? this.actualTarget.getLifeActual() - this.map.getTowersRessource().get(this.id).getLevels().get(this.actualLevel).getDegat() : 0;
			this.actualTarget.setLifeActual(life);
			if(life == 0){
				this.actualTarget.setAlive(false);
				this.game.getPlayer().setGold(this.game.getPlayer().getGold() + this.actualTarget.getGold_income());
				this.game.getRenderGlobal().addRenderIncomeMonster(new RenderIncomeMonster(50, this.actualTarget.getGold_income(), (int) this.actualTarget.getX(), (int) this.actualTarget.getY(), 50));
			}
		}
	}
	
	private Monster findTarget(){
		for(int i = 0; i < this.map.getWaves().size(); i++){
			for(int j = 0; j < this.map.getWaves().get(i).getMonsters().size(); j++){
				int xCenterMonster = (int) this.map.getWaves().get(i).getMonsters().get(j).getX();
				int yCenterMonster = (int) this.map.getWaves().get(i).getMonsters().get(j).getY();
				int xCenterTower = this.x * this.map.getTowersRessource().get(this.id).getSprite_w() + (this.map.getTowersRessource().get(this.id).getSprite_w() / 2);
				int yCenterTower = this.y * this.map.getTowersRessource().get(this.id).getSprite_h() + (this.map.getTowersRessource().get(this.id).getSprite_h() / 2);
				
				double distance = distance(xCenterTower, yCenterTower, xCenterMonster, yCenterMonster);
				if(distance <= this.map.getTowersRessource().get(this.id).getLevels().get(this.actualLevel).getRange() && this.map.getWaves().get(i).getMonsters().get(j).isAlive() && this.map.getWaves().get(i).getMonsters().get(j).isCanGo()){
					return this.map.getWaves().get(i).getMonsters().get(j);
				}
			}
		}
		return null;
	}
	
	private double distance(int xA, int yA, int xB, int yB){
		return Math.sqrt(Math.pow(xB - xA, 2) + Math.pow(yB - yA, 2));
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getSprite_w() {
		return this.map.getTowersRessource().get(this.id).getSprite_w();
	}

	public int getSprite_h() {
		return this.map.getTowersRessource().get(this.id).getSprite_h();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
