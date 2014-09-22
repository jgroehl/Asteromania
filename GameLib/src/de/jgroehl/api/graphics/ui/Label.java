package de.jgroehl.api.graphics.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.graphics.GameObject;

public class Label extends GameObject {

	private String text;
	private float textHeight;
	private int textColor;
	private String typeface;
	private Paint textPaint;
	private Rect textBounds = new Rect();
	private boolean textChanged = false;
	private Alignment alignment;

	public Label(String text, float textHeight, int textColor, String typeface,
			float xPosition, float yPosition, Context context) {
		this(text, xPosition, yPosition, context);

		this.textHeight = textHeight;
		this.textColor = textColor;
		this.typeface = typeface;
		alignment = Alignment.FREE;
	}

	public Label(String text, float xPosition, float yPosition, Context context) {
		super(xPosition, yPosition, context);

		this.text = text;
		textHeight = 0.1f;
		textColor = Color.WHITE;
		typeface = "CALIBRI";
		alignment = Alignment.FREE;
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

		if (textChanged) {
			textPaint.getTextBounds(text, 0, text.length(), textBounds);
			textChanged = false;
		}

		float x = xPosition * c.getWidth();
		float y = yPosition * c.getHeight();

		switch (alignment) {
		case FREE:
			break;
		case CENTER_BOTH:
			x = c.getWidth() / 2 - textBounds.exactCenterX();
			y = c.getHeight() / 2 - textBounds.exactCenterY();
			break;
		case CENTER_HORIZONTALLY:
			x = c.getWidth() / 2 - textBounds.exactCenterX();
			break;
		case CENTER_VERTICALLY:
			y = c.getHeight() / 2 - textBounds.exactCenterY();
			break;
		default:
			break;

		}
		c.drawText(text, x, y, textPaint);

	}

	@Override
	public void update(BaseGameHandler gameHandler) {
		// Nothing to do here.
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

	public void setText(String text) {
		this.text = text;
		textChanged = true;
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
