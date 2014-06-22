package de.jgroehl.asteromania.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.util.SparseArray;

public abstract class GraphicsObject extends GameObject {

	protected static final SparseArray<Bitmap[]> imageCache = new SparseArray<Bitmap[]>();

	private static final float DEFAULT_RELATIVE_SIZE = 0.0f;

	public static final int INVALID_GRAPHICS_ID = -1;
	private final Paint imagePaint = new Paint();
	protected float relativeWidth = DEFAULT_RELATIVE_SIZE;
	protected float relativeHeight = DEFAULT_RELATIVE_SIZE;

	protected int graphicsId;

	public GraphicsObject(float xPosition, float yPosition, float width,
			float height, Context context) {
		this(xPosition, yPosition, INVALID_GRAPHICS_ID, context);
		relativeWidth = width;
		relativeHeight = height;
	}

	public GraphicsObject(float xPosition, float yPosition, int graphicsId,
			Context context) {
		super(xPosition, yPosition, context);

		imagePaint.setStyle(Paint.Style.FILL);
		this.graphicsId = graphicsId;
		if (graphicsId != INVALID_GRAPHICS_ID
				&& imageCache.get(graphicsId) == null) {
			imageCache.put(
					graphicsId,
					new Bitmap[] { BitmapFactory.decodeResource(
							context.getResources(), graphicsId) });
		}
	}

	@Override
	public void draw(Canvas c) {

		if (graphicsId != INVALID_GRAPHICS_ID
				&& imageCache.get(graphicsId) != null) {
			determineRelativeSize(c, imageCache.get(graphicsId)[0]);
			c.drawBitmap(imageCache.get(graphicsId)[0],
					xPosition * c.getWidth(), yPosition * c.getHeight(),
					imagePaint);
		}

	}

	protected void determineRelativeSize(Canvas c, Bitmap graphics) {
		relativeHeight = ((float) graphics.getHeight()) / c.getHeight();
		relativeWidth = ((float) graphics.getWidth()) / c.getWidth();
	}

	public void setGraphics(int graphicsId) {
		this.graphicsId = graphicsId;
		if (graphicsId != INVALID_GRAPHICS_ID
				&& imageCache.get(graphicsId) == null) {
			imageCache.put(
					graphicsId,
					new Bitmap[] { BitmapFactory.decodeResource(
							context.getResources(), graphicsId) });
		}
	}

	public float getRelativeHeight() {
		return relativeHeight;
	}

	public float getRelativeWidth() {
		return relativeWidth;
	}

	protected void addGraphics(int hashCode, Bitmap[] b) {
		if (imageCache.get(hashCode) == null) {
			this.graphicsId = hashCode;
			imageCache.put(hashCode, b);
			Log.d(GraphicsObject.class.getSimpleName(),
					"Added an image for hash (" + hashCode + ")");
		} else {
			Log.w(GraphicsObject.class.getSimpleName(),
					"Try setting a new image to cache even though"
							+ "there is already an image for hash (" + hashCode
							+ ") present.");
		}
	}

}
