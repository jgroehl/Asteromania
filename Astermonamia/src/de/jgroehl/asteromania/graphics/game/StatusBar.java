package de.jgroehl.asteromania.graphics.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.graphics.GameObject;

public class StatusBar extends GameObject {

	private int color = Color.GREEN;
	private int maximum = 100;
	private int currentValue = 100;
	private String text;
	private Paint framePaint = new Paint();
	private Paint contentsPaint = new Paint();
	private Paint textPaint = new Paint();
	private float relativeHeight;
	private float relativeWidth;
	private RectF bounds;

	public StatusBar(float xPosition, float yPosition, float realativeWidth,
			float relativeHeight) {
		super(xPosition, yPosition);
		this.relativeHeight = relativeHeight;
		this.relativeWidth = realativeWidth;
		textPaint.setColor(Color.WHITE);
		contentsPaint.setStyle(Paint.Style.FILL);
		contentsPaint.setColor(color);
		framePaint.setStyle(Paint.Style.STROKE);
		framePaint.setColor(Color.GRAY);
	}

	@Override
	public void update(GameHandler gameHandler) {
	}

	@Override
	public void draw(Canvas c) {

		c.drawRect(bounds, contentsPaint);

		bounds = new RectF(xPosition * c.getWidth(), yPosition * c.getHeight(),
				xPosition * c.getWidth() + relativeWidth * c.getWidth(),
				yPosition * c.getHeight() + relativeHeight * c.getHeight());
		c.drawRect(bounds, framePaint);

		if (text != null) {
			textPaint.setTextSize(relativeHeight * c.getHeight());
			c.drawText(text, xPosition * c.getWidth(),
					yPosition * c.getHeight() + relativeHeight * c.getHeight(),
					textPaint);
		}

	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;

		if (currentValue > maximum)
			maximum = currentValue;
	}

	public int getCurrentValue() {
		return currentValue;
	}

	public void setText(String text) {
		this.text = text;
	}

}
