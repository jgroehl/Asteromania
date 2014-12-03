package de.jgroehl.asteromania.graphics.collectables;

import android.content.Context;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.control.interfaces.EventCallback;
import de.jgroehl.api.graphics.GraphicsObject;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;

public class Coin extends Collectable
{

	private static final int FRAME_COUNT = 13;
	private static final int ANIMATION_PERIOD = 50;
	private static final float V_SPEED = 0.02f;
	private static final float RELATIVE_WIDTH = 0.075f;

	public Coin(final int coinAmount, float xPosition, float yPosition, Context context)
	{
		super(xPosition, yPosition, de.jgroehl.asteromania.R.drawable.rotating_coin, RELATIVE_WIDTH, FRAME_COUNT,
				ANIMATION_PERIOD, V_SPEED, context, new EventCallback()
				{

					@Override
					public void action(BaseGameHandler gameHandler)
					{
						AsteromaniaGameHandler asteromaniaGameHandler = (AsteromaniaGameHandler) gameHandler;
						asteromaniaGameHandler.getSoundManager().playCoinSound();
						asteromaniaGameHandler.getPlayerInfo().addCoins(coinAmount);

					}
				});
	}

	public static void addToHandler(int coinAmount, AsteromaniaGameHandler gameHandler, GraphicsObject parent)
	{
		gameHandler.add(
				new Coin(coinAmount, parent.getX() + (parent.getRelativeWidth() / 2) * plusMinusXXPercent(0.5f), parent
						.getY()
						- (parent.getRelativeHeight() / 3)
						+ (parent.getRelativeHeight() / 2)
						* plusMinusXXPercent(1.0f), gameHandler.getContext()), GameState.MAIN);
	}

}
