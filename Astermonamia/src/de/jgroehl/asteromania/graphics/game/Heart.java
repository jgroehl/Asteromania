package de.jgroehl.asteromania.graphics.game;

import android.content.Context;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.GameState;
import de.jgroehl.asteromania.graphics.GraphicsObject;
import de.jgroehl.asteromania.graphics.animated.AnimatedGraphicsObject;

public class Heart extends AnimatedGraphicsObject {

	private static final int FRAME_COUNT = 15;
	private static final int ANIMATION_PERIOD = 50;
	private static final float V_SPEED = 0.02f;
	private int healAmount;

	public Heart(int healAmount, float xPosition, float yPosition,
			Context context) {
		super(xPosition, yPosition,
				de.jgroehl.asteromania.R.drawable.heart_small, FRAME_COUNT,
				ANIMATION_PERIOD, context);
		this.healAmount = healAmount;
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
			if (gameHandler.getPlayerInfo().isMissingHealth()) {
				gameHandler.getSoundManager().playHealthPowerUpSound();
				gameHandler
						.getPlayerInfo()
						.getHealthPoints()
						.setCurrentValue(
								gameHandler.getPlayerInfo().getHealthPoints()
										.getCurrentValue()
										+ healAmount);
			}
			gameHandler.remove(this);
		}

		setFrame((getFrame() + 1) % getMaxFrame());

	}

	public static void addToHandler(int healAmount, GameHandler gameHandler,
			GraphicsObject parent) {
		gameHandler.add(
				new Heart(healAmount < 1 ? 1 : healAmount, parent.getX()
						+ (parent.getRelativeWidth() / 2)
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
