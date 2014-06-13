package de.jgroehl.asteromania.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class GraphicsObject extends GameObject {

	private final Bitmap graphics;
	private final Paint imagePaint = new Paint();
	private final int centerX;
	private final int centerY;

	public GraphicsObject(float xPosition, float yPosition, Bitmap graphics) {
		super(xPosition, yPosition);

		imagePaint.setStyle(Paint.Style.FILL);

		this.graphics = graphics;

		centerX = graphics.getWidth() / 2;
		centerY = graphics.getHeight() / 2;

	}

	@Override
	public void draw(Canvas c) {

		c.drawBitmap(graphics, xPosition * c.getWidth() - centerX, yPosition
				* c.getHeight() - centerY, imagePaint);

	}

	public int getGraphicsWidth() {
		return centerX * 2;
	}

	public int getGraphicsHeight() {
		return centerY * 2;
	}
}
