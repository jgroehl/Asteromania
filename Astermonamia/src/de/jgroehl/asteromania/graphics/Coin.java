package de.jgroehl.asteromania.graphics;

import android.content.Context;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.graphics.GraphicsObject;
import de.jgroehl.api.graphics.animated.AnimatedGraphicsObject;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;

public class Coin extends AnimatedGraphicsObject {

	private static final int FRAME_COUNT = 13;
	private static final int ANIMATION_PERIOD = 50;
	private static final float V_SPEED = 0.02f;
	private static final float RELATIVE_WIDTH = 0.075f;

	public Coin(float xPosition, float yPosition, Context context) {
		super(xPosition, yPosition,
				de.jgroehl.asteromania.R.drawable.rotating_coin,
				RELATIVE_WIDTH, FRAME_COUNT, ANIMATION_PERIOD, context);
	}

	@Override
	public void update(BaseGameHandler gameHandler) {

		if (gameHandler instanceof AsteromaniaGameHandler) {

			AsteromaniaGameHandler asteromaniaGameHandler = (AsteromaniaGameHandler) gameHandler;

			yPosition += V_SPEED;
			if (yPosition > GraphicsObject.SCREEN_MAXIMUM)
				asteromaniaGameHandler.remove(this);

			if (imagesOverlap(this.xPosition, this.yPosition,
					this.relativeWidth, this.relativeHeight,
					asteromaniaGameHandler.getPlayer().getX(),
					asteromaniaGameHandler.getPlayer().getY(),
					asteromaniaGameHandler.getPlayer().getRelativeWidth(),
					asteromaniaGameHandler.getPlayer().getRelativeHeight())) {
				asteromaniaGameHandler.getSoundManager().playCoinSound();
				asteromaniaGameHandler.getPlayerInfo().addCoins(1);
				asteromaniaGameHandler.remove(this);
			}

			setFrame((getFrame() + 1) % getMaxFrame());
		}

	}

	public static void addToHandler(AsteromaniaGameHandler gameHandler,
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
