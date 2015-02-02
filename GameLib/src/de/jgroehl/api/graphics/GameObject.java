package de.jgroehl.api.graphics;

import android.content.Context;
import de.jgroehl.api.graphics.interfaces.Drawable;
import de.jgroehl.api.graphics.interfaces.Updatable;

public abstract class GameObject implements Drawable, Updatable
{

	public enum Level
	{
		BOTTOM, TOP
	}

	protected final static float SCREEN_MINIMUM = 0.0f;
	protected final static float SCREEN_MAXIMUM = 1.0f;
	private float xPosition;
	private float yPosition;
	private final Context context;
	private Level level = Level.TOP;

	public GameObject(float xPosition, float yPosition, Context context)
	{
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.context = context;
	}

	public void setPosition(float xPosition, float yPosition)
	{
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}

	public float getX()
	{
		return xPosition;
	}

	public float getY()
	{
		return yPosition;
	}

	public Level getLevel()
	{
		return level;
	}

	public void setLevel(Level level)
	{
		this.level = level;
	}

	public Context getContext()
	{
		return context;
	}

	public boolean imagesOverlap(float x1, float y1, float width1, float height1, float x2, float y2, float width2,
			float height2)
	{

		boolean isLeft = x1 + width1 < x2;
		boolean isRight = x1 > x2 + width2;
		boolean isBelow = y1 + height1 < y2;
		boolean isAbove = y1 > y2 + height2;

		return !(isLeft || isRight || isBelow || isAbove);
	}
}
