package de.jgroehl.asteromania.graphics.collectables;

import android.content.Context;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.interfaces.EventCallback;
import de.jgroehl.api.graphics.GraphicsObject;
import de.jgroehl.api.graphics.animated.AnimatedGraphicsObject;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;

public class Collectable extends AnimatedGraphicsObject
{

	private final float vSpeed;
	private final EventCallback onCollect;

	public Collectable(float xPosition, float yPosition, int graphicsId, float relativeWidth, int frameCount,
			int animationPeriod, float vSpeed, Context context, EventCallback onCollect)
	{
		super(xPosition, yPosition, graphicsId, relativeWidth, frameCount, animationPeriod, context);
		this.onCollect = onCollect;
		this.vSpeed = vSpeed;
	}

	@Override
	public void update(BaseGameHandler gameHandler)
	{
		if (gameHandler instanceof AsteromaniaGameHandler)
		{

			AsteromaniaGameHandler asteromaniaGameHandler = (AsteromaniaGameHandler) gameHandler;
			yPosition += vSpeed;
			if (yPosition > GraphicsObject.SCREEN_MAXIMUM)
				gameHandler.remove(this);

			if (onCollect != null)
				if (imagesOverlap(this.xPosition, this.yPosition, this.relativeWidth, this.relativeHeight,
						asteromaniaGameHandler.getPlayer().getX(), asteromaniaGameHandler.getPlayer().getY(),
						asteromaniaGameHandler.getPlayer().getRelativeWidth(), asteromaniaGameHandler.getPlayer()
								.getRelativeHeight()))
				{
					onCollect.action(asteromaniaGameHandler);
					asteromaniaGameHandler.remove(this);
				}

			setFrame((getFrame() + 1) % getMaxFrame());
		}

	}

	protected static float plusMinusXXPercent(float xx)
	{
		return (float) ((1.0f - xx) + Math.random() * 2 * xx);
	}

}
