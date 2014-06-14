package de.jgroehl.asteromania.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class GraphicsObject extends GameObject {

	protected Bitmap graphics;
	private final Paint imagePaint = new Paint();
	protected int alignmentX;
	protected int alignmentY;
	protected int width;
	protected int height;

	/**
	 * The Alignment of the image relative to its x and y position.
	 * 
	 * @author Janek
	 * 
	 */
	public enum Align {
		TOP_LEFT, TOP_CENTER, TOP_RIGHT, MID_LEFT, MID_CENTER, MID_RIGHT, BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT
	}

	public GraphicsObject(float xPosition, float yPosition, Bitmap graphics,
			Align align) {
		super(xPosition, yPosition);

		imagePaint.setStyle(Paint.Style.FILL);

		this.graphics = graphics;

		if (graphics != null) {
			width = graphics.getWidth();
			height = graphics.getHeight();
		} else {
			width = 0;
			height = 0;
		}

		alignmentX = determineAlignmentX(align);
		alignmentY = determineAlignmentY(align);

	}

	public GraphicsObject(float xPosition, float yPosition, int width,
			int height, Align align) {
		super(xPosition, yPosition);

		graphics = null;
		imagePaint.setStyle(Paint.Style.FILL);

		this.width = width;
		this.height = height;

		alignmentX = determineAlignmentX(align);
		alignmentY = determineAlignmentY(align);

	}

	private int determineAlignmentX(Align align) {
		switch (align) {
		case BOTTOM_LEFT:
		case MID_LEFT:
		case TOP_LEFT:
			return width;
		case BOTTOM_CENTER:
		case MID_CENTER:
		case TOP_CENTER:
			return width / 2;
		case BOTTOM_RIGHT:
		case MID_RIGHT:
		case TOP_RIGHT:
			return 0;
		default:
			return 0;
		}
	}

	private int determineAlignmentY(Align align) {
		switch (align) {
		case BOTTOM_LEFT:
		case BOTTOM_RIGHT:
		case BOTTOM_CENTER:
			return 0;
		case MID_CENTER:
		case MID_LEFT:
		case MID_RIGHT:
			return height / 2;
		case TOP_CENTER:
		case TOP_LEFT:
		case TOP_RIGHT:
			return height;
		default:
			return 0;
		}
	}

	@Override
	public void draw(Canvas c) {

		if (graphics != null)
			c.drawBitmap(graphics, xPosition * c.getWidth() - alignmentX,
					yPosition * c.getHeight() - alignmentY, imagePaint);

	}

	public int getGraphicsWidth() {
		return width;
	}

	public int getGraphicsHeight() {
		return height;
	}

	public void setAlignment(Align align) {
		alignmentX = determineAlignmentX(align);
		alignmentY = determineAlignmentY(align);
	}

	public void setGraphics(Bitmap graphics, Align align) {
		this.graphics = graphics;
		width = graphics.getWidth();
		height = graphics.getHeight();
		setAlignment(align);
	}
}
