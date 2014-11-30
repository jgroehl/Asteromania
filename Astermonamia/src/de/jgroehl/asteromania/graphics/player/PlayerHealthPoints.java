package de.jgroehl.asteromania.graphics.player;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.jgroehl.api.graphics.statusBars.FramedHpBar;

public class PlayerHealthPoints extends FramedHpBar
{

	private final Paint textPaint = new Paint();

	public PlayerHealthPoints(int maximum, int currentValue, float xPosition, float yPosition, float relativeWidth,
			float relativeHeight, Context context, int frameId)
	{
		super(maximum, currentValue, xPosition, yPosition, relativeWidth, relativeHeight, context, frameId);
	}

	@Override
	public void draw(Canvas c)
	{
		super.draw(c);
		float textX = (getX() + 0.01f) * c.getWidth();
		float textY = (getY() + getRelativeHeight() * 0.9f) * c.getHeight();
		textPaint.setTextSize(getRelativeHeight() * 0.8f * c.getHeight());
		textPaint.setColor(Color.BLACK);
		c.drawText(context.getResources().getString(de.jgroehl.asteromania.R.string.hp) + ": " + getCurrentValue()
				+ "/" + getMaximum(), textX + 1, textY + 1, textPaint);
		textPaint.setColor(Color.GRAY);
		c.drawText(context.getResources().getString(de.jgroehl.asteromania.R.string.hp) + ": " + getCurrentValue()
				+ "/" + getMaximum(), textX, textY, textPaint);
	}

}
