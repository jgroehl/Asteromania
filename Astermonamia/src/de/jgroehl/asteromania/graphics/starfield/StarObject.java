package de.jgroehl.asteromania.graphics.starfield;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.graphics.GameObject;
import de.jgroehl.api.graphics.interfaces.Updatable;

public class StarObject extends GameObject implements Updatable
{

	private final Paint starColor;
	private final float relativeDiameter;
	private final RectF starRect;
	private final float speed;
	private float speedFactor = 1.0f;

	public StarObject(float xPosition, float yPosition, float relativeDiameter, int paintColor, float speed,
			Context context)
	{
		super(xPosition, yPosition, context);

		this.speed = speed;
		this.relativeDiameter = relativeDiameter;
		this.starColor = new Paint();
		starColor.setColor(paintColor);
		this.starRect = new RectF();
	}

	@Override
	public void draw(Canvas c)
	{
		int diameter = (int) (relativeDiameter * c.getWidth());

		starRect.set((getX() * c.getWidth() - diameter), (getY() * c.getHeight() - diameter),
				(getX() * c.getWidth() + diameter), (getY() * c.getHeight() + diameter));
		c.drawOval(starRect, starColor);
	}

	@Override
	public void update(BaseGameHandler gameHandler)
	{

		setPosition(getX(), getY() + speed * speedFactor);
		if (getY() >= SCREEN_MAXIMUM)
		{
			setPosition((float) Math.random(), (float) -(Math.random() * 0.1));
		}

	}

	public void accelerate(float factor)
	{
		speedFactor += factor;
	}

	public float getAcceleration()
	{
		return speedFactor;
	}

	public void reset()
	{
		speedFactor = 1;
	}
}
