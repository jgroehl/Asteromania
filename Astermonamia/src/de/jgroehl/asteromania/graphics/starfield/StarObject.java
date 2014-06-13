package de.jgroehl.asteromania.graphics.starfield;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.graphics.GameObject;
import de.jgroehl.asteromania.graphics.interfaces.Updatable;

public class StarObject extends GameObject implements Updatable {

	private final Paint starColor;
	private final int diameter;
	private final RectF starRect;
	private final float speed;

	public StarObject(float xPosition, float yPosition, int diameter,
			int paintColor, float speed) {
		super(xPosition, yPosition);

		this.speed = speed;
		this.diameter = diameter;
		this.starColor = new Paint();
		starColor.setColor(paintColor);
		this.starRect = new RectF();
	}

	@Override
	public void draw(Canvas c) {
		starRect.set((xPosition * c.getWidth() - diameter),
				(yPosition * c.getHeight() - diameter),
				(xPosition * c.getWidth() + diameter),
				(yPosition * c.getHeight() + diameter));
		c.drawOval(starRect, starColor);
	}

	@Override
	public void update(GameHandler gameHandler) {

		yPosition = yPosition + speed;
		if (yPosition >= 1) {
			xPosition = (float) Math.random();
			yPosition = 0;
		}

	}
}
