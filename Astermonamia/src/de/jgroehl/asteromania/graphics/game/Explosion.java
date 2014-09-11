package de.jgroehl.asteromania.graphics.game;

import android.content.Context;
import android.graphics.Canvas;
import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.GameState;
import de.jgroehl.asteromania.graphics.GraphicsObject;
import de.jgroehl.asteromania.graphics.animated.AnimatedGraphicsObject;

public class Explosion extends AnimatedGraphicsObject {

	private Explosion(float xPosition, float yPosition, float relativeWidth,
			Context context) {
		super(xPosition, yPosition, R.drawable.kaboom, relativeWidth, 6, 50,
				context);
	}

	@Override
	public void update(GameHandler gameHandler) {
		setFrame(getFrame() + 1);
		if (getFrame() == getMaxFrame() - 1) {
			gameHandler.remove(this);
		}
	}

	public static void addExplosion(GameHandler gameHandler,
			GraphicsObject graphicsObject) {
		gameHandler.add(
				new Explosion(graphicsObject.getX(), graphicsObject.getY(),
						0.15f, gameHandler.getContext()), GameState.MAIN);
	}

	public static void createGameOver(GameHandler gameHandler) {
		gameHandler.add(
				new Explosion(0.38f, 0.38f, 0.15f, gameHandler.getContext()),
				GameState.GAME_OVER);
	}

	@Override
	public void draw(Canvas c) {
		super.draw(c);
	}
}
