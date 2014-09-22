package de.jgroehl.api.graphics.animated;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import de.jgroehl.api.graphics.GraphicsObject;
import de.jgroehl.api.time.Timer;

public abstract class AnimatedGraphicsObject extends GraphicsObject {

	protected final Timer animationTimer;

	private final Paint imagePaint = new Paint();
	private final int maxFrames;
	private int currentFrame;
	private int frameCount;

	public AnimatedGraphicsObject(float xPosition, float yPosition,
			int graphicsId, float relativeWidth, int frameCount,
			int animationPeriod, Context context) {
		super(xPosition, yPosition, INVALID_GRAPHICS_ID, relativeWidth, context);

		this.graphicsId = graphicsId;
		this.frameCount = frameCount;
		maxFrames = frameCount;
		animationTimer = new Timer(animationPeriod);
		imagePaint.setStyle(Paint.Style.FILL);
	}

	@Override
	public void draw(Canvas c) {
		if (imageCache.get(graphicsId) == null) {
			Bitmap graphics = BitmapFactory.decodeResource(
					context.getResources(), graphicsId);
			int width = graphics.getWidth() / frameCount;
			int height = graphics.getHeight();
			Bitmap[] imageFrames = new Bitmap[frameCount];

			for (int i = 0; i < maxFrames; i++) {
				imageFrames[i] = Bitmap
						.createScaledBitmap(
								Bitmap.createBitmap(graphics, i * width, 0,
										width, height),
								(int) (relativeWidth * c.getWidth()),
								(int) (height * ((relativeWidth * c.getWidth()) / (float) width)),
								true);
			}
			imageCache.put(graphicsId, imageFrames);
			determineRelativeSize(c, imageCache.get(graphicsId)[0]);

		} else {
			determineRelativeSize(c, imageCache.get(graphicsId)[currentFrame]);
			c.drawBitmap(imageCache.get(graphicsId)[currentFrame], xPosition
					* c.getWidth(), yPosition * c.getHeight(), imagePaint);
		}
	}

	protected void setFrame(int frame) {
		if (frame < 0 || frame >= maxFrames)
			throw new IllegalArgumentException("The given frame " + frame
					+ " was out of bounds!");
		if (animationTimer.isPeriodOver())
			currentFrame = frame;
	}

	protected int getMaxFrame() {
		return maxFrames;
	}

	public int getFrame() {
		return currentFrame;
	}

}
