package de.jgroehl.asteromania.graphics;

import android.content.Context;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.graphics.GraphicsObject;
import de.jgroehl.api.graphics.animated.AnimatedGraphicsObject;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;

public class Heart extends AnimatedGraphicsObject {

	private static final float RELATIVE_WIDTH = 0.09f;
	private static final int FRAME_COUNT = 15;
	private static final int ANIMATION_PERIOD = 50;
	private static final float V_SPEED = 0.02f;
	private int healAmount;

	public Heart(int healAmount, float xPosition, float yPosition,
			Context context) {
		super(xPosition, yPosition,
				de.jgroehl.asteromania.R.drawable.heart_small, RELATIVE_WIDTH,
				FRAME_COUNT, ANIMATION_PERIOD, context);
		this.healAmount = healAmount;
	}

	@Override
	public void update(BaseGameHandler gameHandler) {

		if (gameHandler instanceof AsteromaniaGameHandler) {

			AsteromaniaGameHandler asteromaniaGameHandler = (AsteromaniaGameHandler) gameHandler;
			yPosition += V_SPEED;
			if (yPosition > GraphicsObject.SCREEN_MAXIMUM)
				gameHandler.remove(this);

			if (imagesOverlap(this.xPosition, this.yPosition,
					this.relativeWidth, this.relativeHeight,
					asteromaniaGameHandler.getPlayer().getX(),
					asteromaniaGameHandler.getPlayer().getY(),
					asteromaniaGameHandler.getPlayer().getRelativeWidth(),
					asteromaniaGameHandler.getPlayer().getRelativeHeight())) {
				if (asteromaniaGameHandler.getPlayerInfo().isMissingHealth()) {
					asteromaniaGameHandler.getSoundManager()
							.playHealthPowerUpSound();
					asteromaniaGameHandler
							.getPlayerInfo()
							.getHealthPoints()
							.setCurrentValue(
									asteromaniaGameHandler.getPlayerInfo()
											.getHealthPoints()
											.getCurrentValue()
											+ healAmount);
				}
				asteromaniaGameHandler.remove(this);
			}

			setFrame((getFrame() + 1) % getMaxFrame());
		}

	}

	public static void addToHandler(int healAmount,
			AsteromaniaGameHandler gameHandler, GraphicsObject parent) {
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
