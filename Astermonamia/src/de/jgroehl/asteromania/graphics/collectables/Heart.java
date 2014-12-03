package de.jgroehl.asteromania.graphics.collectables;

import android.content.Context;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.control.interfaces.EventCallback;
import de.jgroehl.api.graphics.GraphicsObject;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;

public class Heart extends Collectable
{

	private static final float RELATIVE_WIDTH = 0.09f;
	private static final int FRAME_COUNT = 15;
	private static final int ANIMATION_PERIOD = 50;
	private static final float V_SPEED = 0.02f;

	public Heart(final int healAmount, float xPosition, float yPosition, Context context)
	{
		super(xPosition, yPosition, de.jgroehl.asteromania.R.drawable.heart_small, RELATIVE_WIDTH, FRAME_COUNT,
				ANIMATION_PERIOD, V_SPEED, context, new EventCallback()
				{

					@Override
					public void action(BaseGameHandler gameHandler)
					{
						AsteromaniaGameHandler asteromaniaGameHandler = (AsteromaniaGameHandler) gameHandler;
						if (asteromaniaGameHandler.getPlayerInfo().isMissingHealth())
						{
							asteromaniaGameHandler.getSoundManager().playHealthPowerUpSound();
							asteromaniaGameHandler
									.getPlayerInfo()
									.getHealthPoints()
									.setCurrentValue(
											asteromaniaGameHandler.getPlayerInfo().getHealthPoints().getCurrentValue()
													+ healAmount);
						}

					}
				});
	}

	public static void addToHandler(int healAmount, AsteromaniaGameHandler gameHandler, GraphicsObject parent)
	{
		gameHandler.add(
				new Heart(healAmount < 1 ? 1 : healAmount, parent.getX() + (parent.getRelativeWidth() / 2)
						* plusMinusXXPercent(0.5f), parent.getY() - (parent.getRelativeHeight() / 3)
						+ (parent.getRelativeHeight() / 2) * plusMinusXXPercent(1.0f), gameHandler.getContext()),
				GameState.MAIN);
	}

}
