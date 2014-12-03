package de.jgroehl.api.graphics;

import android.content.Context;
import android.graphics.Canvas;
import de.jgroehl.api.control.BaseGameHandler;

public abstract class OptionalGraphicsObject extends GraphicsObject
{

	public OptionalGraphicsObject(float xPosition, float yPosition, int graphicsId, float relativeWidth, Context context)
	{
		super(xPosition, yPosition, graphicsId, relativeWidth, context);
	}

	private boolean canBeDrawn = false;

	@Override
	public void draw(Canvas c)
	{
		if (canBeDrawn)
			super.draw(c);
	}

	@Override
	public void update(BaseGameHandler gameHandler)
	{
		canBeDrawn = canBeDrawn(gameHandler);
	}

	protected abstract boolean canBeDrawn(BaseGameHandler gameHandler);

}
