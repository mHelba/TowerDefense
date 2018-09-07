package code.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class RenderIncomeMonster {
	private int start;
	private int durationEffect;
	private int income;
	private int startX;
	private int startY;
	private int height;
	private boolean end = false;
	
	public RenderIncomeMonster(int duration, int income, int startX, int startY, int height) {
		this.start = 0;
		this.durationEffect = duration;
		this.income = income;
		this.startX = startX;
		this.startY = startY;
		this.height = height;
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		String s = "+" + income;
		float opacity;
		float percent = (float) this.start / (float) this.durationEffect;
		int y = (int) (this.startY - this.height * percent);
		
		if(percent < 0.20){
			opacity = percent / 0.20f * 1.0f;
		} else if(percent >= 0.80){
			float per = percent - 0.80f;
			per = per / 0.20f * 1.0f;
			opacity = 1.0f - per;
		} else {
			opacity = 1;
		}
		g.setColor(new Color(246.0f / 255.0f, 219.0f / 255.0f, 38.0f / 255.0f, opacity));
		g.drawString(s, this.startX - g.getFont().getWidth(s), y);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		this.start++;
		if(this.start > this.durationEffect){
			this.start = this.durationEffect;
		}
		if(this.start == this.durationEffect){
			this.end = true;
		}
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}
}
