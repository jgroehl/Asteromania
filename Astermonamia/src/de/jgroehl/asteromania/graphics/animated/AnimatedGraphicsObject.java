package de.jgroehl.asteromania.graphics.animated;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import de.jgroehl.asteromania.graphics.GraphicsObject;
import de.jgroehl.asteromania.time.Timer;

public abstract class AnimatedGraphicsObject extends GraphicsObject {

	private Paint imagePaint = new Paint();
	private final Bitmap[] imageFrames;
	private int currentFrame;
	private final int maxFrames;
	protected final Timer animationTimer;

	public AnimatedGraphicsObject(float xPosition, float yPosition,
			Bitmap graphics, int frameCount, int animationPeriod) {
		super(xPosition, yPosition, null);

		int width = graphics.getWidth() / frameCount;
		int height = graphics.getHeight();
		animationTimer = new Timer(animationPeriod);
		imagePaint.setStyle(Paint.Style.FILL);

		imageFrames = new Bitmap[frameCount];
		maxFrames = frameCount;

		for (int i = 0; i < maxFrames; i++) {
			imageFrames[i] = Bitmap.createBitmap(graphics, i * width, 0, width,
					height);
		}
	}

	@Override
	public void draw(Canvas c) {
		determineRelativeSize(c, imageFrames[currentFrame]);
		c.drawBitmap(imageFrames[currentFrame], xPosition * c.getWidth(),
				yPosition * c.getHeight(), imagePaint);
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
