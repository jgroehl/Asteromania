package de.jgroehl.api.graphics;

import android.content.Context;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.time.Timer;

public class TimeDependendGraphicsObject extends GraphicsObject
{

	private final Timer timer;

	public TimeDependendGraphicsObject(float xPosition, float yPosition, int graphicsId, float relativeWidth,
			int showingTime, Context context)
	{
		super(xPosition, yPosition, graphicsId, relativeWidth, context);
		timer = new Timer(showingTime);
	}

	@Override
	public void update(BaseGameHandler gameHandler)
	{
		if (timer.isPeriodOver())
			gameHandler.remove(this);
	}

}
