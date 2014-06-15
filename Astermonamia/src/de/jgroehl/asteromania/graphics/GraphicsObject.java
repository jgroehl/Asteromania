package de.jgroehl.asteromania.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class GraphicsObject extends GameObject {

	private static final float DEFAULT_RELATIVE_SIZE = 0.0f;
	protected Bitmap graphics;
	private final Paint imagePaint = new Paint();
	protected float relativeWidth = DEFAULT_RELATIVE_SIZE;
	protected float relativeHeight = DEFAULT_RELATIVE_SIZE;

	public GraphicsObject(float xPosition, float yPosition, Bitmap graphics) {
		super(xPosition, yPosition);

		imagePaint.setStyle(Paint.Style.FILL);

		this.graphics = graphics;
	}

	@Override
	public void draw(Canvas c) {

		if (graphics != null) {
			determineRelativeSize(c, graphics);
			c.drawBitmap(graphics, xPosition * c.getWidth(),
					yPosition * c.getHeight(), imagePaint);
		}

	}

	protected void determineRelativeSize(Canvas c, Bitmap graphics) {
		relativeHeight = ((float) graphics.getHeight()) / c.getHeight();
		relativeWidth = ((float) graphics.getWidth()) / c.getWidth();
	}

	public void setGraphics(Bitmap graphics) {
		this.graphics = graphics;
	}

	public float getRelativeHeight() {
		return relativeHeight;
	}

	public float getRelativeWidth() {
		return relativeWidth;
	}

}
