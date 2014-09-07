package de.jgroehl.asteromania.graphics.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.graphics.GameObject;

public class Label extends GameObject {

	private String text;
	private float textHeight;
	private int textColor;
	private String typeface;
	private Paint textPaint;
	private Rect textBounds = new Rect();

	public Label(String text, float textHeight, int textColor, String typeface,
			float xPosition, float yPosition, Context context) {
		super(xPosition, yPosition, context);

		this.textHeight = textHeight;
		this.textColor = textColor;
		this.typeface = typeface;
	}

	public Label(String text, float xPosition, float yPosition, Context context) {
		super(xPosition, yPosition, context);

		textHeight = 0.1f;
		textColor = Color.WHITE;
		typeface = "CALIBRI";
	}

	@Override
	public void draw(Canvas c) {

		if (textPaint == null) {
			textPaint = new Paint();
			textPaint.setTextSize(textHeight * c.getHeight());
			textPaint.setColor(textColor);
			textPaint.setTypeface(Typeface.create(typeface, Typeface.BOLD));
			textPaint.getTextBounds(text, 0, text.length(), textBounds);
		}

		c.drawText(text, xPosition * c.getWidth(), yPosition * c.getHeight(),
				textPaint);

	}

	@Override
	public void update(GameHandler gameHandler) {
		// Nothing to do here.
	}

	public void setTextHeight(float textHeight) {
		this.textHeight = textHeight;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public void setTypeface(String typeface) {
		this.typeface = typeface;
	}

}
