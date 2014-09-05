package de.jgroehl.asteromania.graphics.game.statusBars;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.graphics.GraphicsObject;

public class StatusBar extends GraphicsObject {

	public static final int EMPTY = 0;
	public static final int FULL = 1;

	private int maximum = 100;
	private int currentValue = 100;
	private Paint contentsPaint = new Paint();
	private RectF bounds = new RectF();

	public StatusBar(int maximum, float xPosition, float yPosition,
			float relativeWidth, float relativeHeight, Context context,
			int type, int color) {
		super(xPosition, yPosition, relativeWidth, relativeHeight, context);
		contentsPaint.setStyle(Paint.Style.FILL);
		contentsPaint.setColor(color);

		this.maximum = maximum;
		switch (type) {
		case FULL:
			currentValue = maximum;
			break;
		case EMPTY:
		default:
			currentValue = 0;
			break;

		}
	}

	@Override
	public void update(GameHandler gameHandler) {
	}

	@Override
	public void draw(Canvas c) {
		bounds.set(xPosition * c.getWidth(), yPosition * c.getHeight(),
				xPosition
						* c.getWidth()
						+ (relativeWidth * c.getWidth() * (currentValue < 0 ? 0
								: currentValue)) / maximum,
				yPosition * c.getHeight() + relativeHeight * c.getHeight());
		c.drawRect(bounds, contentsPaint);
	}

	public void setColor(int color) {
		contentsPaint.setColor(color);
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	/**
	 * Sets the current value.
	 * 
	 * @param currentValue
	 *            must be 0 <= currentValue <= maximum else currentValue will be
	 *            set to 0 or maximum
	 */
	public void setCurrentValue(int currentValue) {
		if (currentValue > maximum)
			currentValue = maximum;
		if (currentValue < 0)
			currentValue = 0;
		this.currentValue = currentValue;
	}

	public int getCurrentValue() {
		return currentValue;
	}

	public int getMaximum() {
		return maximum;
	}

	public void reset() {
		currentValue = maximum;
	}
}
