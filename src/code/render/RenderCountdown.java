package code.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import code.objects.gamestate.GameState;

public class RenderCountdown {
	private GameState game;
	private int countMax;
	private int startTime;
	private int durationEffect;
	private boolean end;
	
	public RenderCountdown(GameState game, int countMax, int durationEffect){
		this.game = game;
		this.countMax = countMax;
		this.startTime = 0;
		this.durationEffect = durationEffect;
		this.end = false;
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		int countActual;
		int avancement;
		float timePerNumber;
		float percent;
		float opacity;
		String s;
		
		timePerNumber = this.durationEffect / this.countMax;
		countActual = this.startTime / (int) timePerNumber;
		countActual = this.countMax - countActual;
		avancement = this.startTime % (int) timePerNumber;
		percent = (float) avancement / timePerNumber;
		if(percent < 0.5f){
			opacity = percent * 2;
		} else {
			opacity = 1.0f - ((percent - 0.5f) * 2f);
		}
		s = String.valueOf(countActual);
		g.setColor(new Color(0f, 0f, 0f, opacity));
		g.drawString(s, container.getWidth() / 2 - g.getFont().getWidth(s) / 2, container.getHeight() / 2 - g.getFont().getHeight(s) / 2);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		this.startTime++;
		if(this.startTime > this.durationEffect){
			this.startTime = this.durationEffect;
		}
		if(this.startTime == this.durationEffect){
			this.end = true;
			this.game.setCountdown(false);
		}
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}
}
