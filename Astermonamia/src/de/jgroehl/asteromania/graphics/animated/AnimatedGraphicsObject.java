package de.jgroehl.asteromania.graphics.animated;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import de.jgroehl.asteromania.graphics.GameObject;
import de.jgroehl.asteromania.time.Timer;

public abstract class AnimatedGraphicsObject extends GameObject {

	private Paint imagePaint = new Paint();
	private final int centerX;
	private final int centerY;
	private final Bitmap[] imageFrames;
	private int currentFrame;
	private final int maxFrames;
	protected final Timer animationTimer;
	private int xInset = 0;
	private int yInset = 0;

	public AnimatedGraphicsObject(float xPosition, float yPosition,
			Bitmap graphics, int frameCount, int animationPeriod) {
		super(xPosition, yPosition);

		animationTimer = new Timer(animationPeriod);
		imagePaint.setStyle(Paint.Style.FILL);

		imageFrames = new Bitmap[frameCount];
		maxFrames = frameCount;

		int width = graphics.getWidth() / maxFrames;
		int height = graphics.getHeight();

		for (int i = 0; i < maxFrames; i++) {
			imageFrames[i] = Bitmap.createBitmap(graphics, i * width, 0, width,
					height);
		}

		centerX = (int) Math.round(width / 2.0);
		centerY = (int) Math.round(height / 2.0);
	}

	public void setInsets(int x, int y) {
		this.xInset = x;
		this.yInset = y;
	}

	@Override
	public void draw(Canvas c) {
		c.drawBitmap(imageFrames[currentFrame], xPosition * c.getWidth()
				- centerX + xInset, yPosition * c.getHeight() - centerY
				+ yInset, imagePaint);
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

	public int getGraphicsWidth() {
		return centerX * 2;
	}

	public int getGraphicsHeight() {
		return centerY * 2;
	}

}
