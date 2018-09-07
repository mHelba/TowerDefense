package code.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class RenderCinematics {

	public static void renderPause(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		String s = "PAUSE !";
		g.setColor(new Color(0f, 0f, 0f, 0.5f));
		g.fillRect(0, 0, container.getWidth(), container.getHeight());
		g.setColor(Color.white);
		g.drawString(s, container.getWidth() / 2 - g.getFont().getWidth(s) / 2, container.getHeight() / 2 - g.getFont().getHeight(s) / 2);
	}
	
	public static void renderEndGame(GameContainer container, StateBasedGame game, Graphics g, String end) throws SlickException {
		g.setColor(new Color(0f, 0f, 0f, 0.5f));
		g.fillRect(0, 0, container.getWidth(), container.getHeight());
		g.setColor(Color.white);
		g.drawString(end, container.getWidth() / 2 - g.getFont().getWidth(end) / 2, container.getHeight() / 2 - g.getFont().getHeight(end) / 2);
	}
}
