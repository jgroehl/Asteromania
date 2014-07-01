package de.jgroehl.asteromania.graphics.game;

import android.content.Context;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.GameState;
import de.jgroehl.asteromania.graphics.GraphicsObject;
import de.jgroehl.asteromania.graphics.animated.AnimatedGraphicsObject;

public class Coin extends AnimatedGraphicsObject {

	private static final int FRAME_COUNT = 13;
	private static final int ANIMATION_PERIOD = 50;
	private static final float V_SPEED = 0.02f;

	public Coin(float xPosition, float yPosition, Context context) {
		super(xPosition, yPosition,
				de.jgroehl.asteromania.R.drawable.rotating_coin, FRAME_COUNT,
				ANIMATION_PERIOD, context);
	}

	@Override
	public void update(GameHandler gameHandler) {

		yPosition += V_SPEED;
		if (yPosition > GraphicsObject.SCREEN_MAXIMUM)
			gameHandler.remove(this);

		if (imagesOverlap(this.xPosition, this.yPosition, this.relativeWidth,
				this.relativeHeight, gameHandler.getPlayer().getX(),
				gameHandler.getPlayer().getY(), gameHandler.getPlayer()
						.getRelativeWidth(), gameHandler.getPlayer()
						.getRelativeHeight())) {
			gameHandler.getSoundManager().playCoinSound();
			gameHandler.getPlayerInfo().addCoins(1);
			gameHandler.remove(this);
		}

		setFrame((getFrame() + 1) % getMaxFrame());

	}

	public static void addToHandler(GameHandler gameHandler,
			GraphicsObject parent) {
		gameHandler.add(
				new Coin(parent.getX() + (parent.getRelativeWidth() / 2)
						* plusMinusXXPercent(0.5f), parent.getY()
						- (parent.getRelativeHeight() / 3)
						+ (parent.getRelativeHeight() / 2)
						* plusMinusXXPercent(1.0f), gameHandler.getContext()),
				GameState.MAIN);
	}

	private static float plusMinusXXPercent(float xx) {
		return (float) ((1.0f - xx) + Math.random() * 2 * xx);
	}

}
