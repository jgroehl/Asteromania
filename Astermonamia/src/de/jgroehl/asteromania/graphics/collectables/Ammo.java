package de.jgroehl.asteromania.graphics.collectables;

import android.content.Context;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.control.interfaces.EventCallback;
import de.jgroehl.api.graphics.GraphicsObject;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;

public class Ammo extends Collectable
{

	private static final int FRAME_COUNT = 1;
	private static final int ANIMATION_PERIOD = 5000;
	private static final float V_SPEED = 0.02f;
	private static final float RELATIVE_WIDTH = 0.075f;

	public Ammo(float xPosition, float yPosition, final int ammoAmount, Context context)
	{
		super(xPosition, yPosition, de.jgroehl.asteromania.R.drawable.shop_rocket, RELATIVE_WIDTH, FRAME_COUNT,
				ANIMATION_PERIOD, V_SPEED, context, new EventCallback()
				{

					@Override
					public void action(BaseGameHandler gameHandler)
					{
						AsteromaniaGameHandler asteromaniaGameHandler = (AsteromaniaGameHandler) gameHandler;
						asteromaniaGameHandler.getSoundManager().playHealthPowerUpSound();
						asteromaniaGameHandler.getPlayerInfo().incrementAmmo(ammoAmount);
					}
				});
	}

	public static void addToHandler(int amount, AsteromaniaGameHandler gameHandler, GraphicsObject parent)
	{
		gameHandler.add(
				new Ammo(parent.getX() + (parent.getRelativeWidth() / 2) * plusMinusXXPercent(0.5f), parent.getY()
						- (parent.getRelativeHeight() / 3) + (parent.getRelativeHeight() / 2)
						* plusMinusXXPercent(1.0f), amount, gameHandler.getContext()), GameState.MAIN);
	}

}
