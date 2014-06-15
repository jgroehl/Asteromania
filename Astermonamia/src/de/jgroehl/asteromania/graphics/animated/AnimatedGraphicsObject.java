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
	private int xInset = 0;
	private int yInset = 0;

	public AnimatedGraphicsObject(float xPosition, float yPosition,
			Bitmap graphics, int frameCount, int animationPeriod, Align align) {
		super(xPosition, yPosition, graphics.getWidth() / frameCount, graphics
				.getHeight(), align);

		animationTimer = new Timer(animationPeriod);
		imagePaint.setStyle(Paint.Style.FILL);

		imageFrames = new Bitmap[frameCount];
		maxFrames = frameCount;

		for (int i = 0; i < maxFrames; i++) {
			imageFrames[i] = Bitmap.createBitmap(graphics, i * width, 0, width,
					height);
		}
	}

	public void setInsets(int x, int y) {
		this.xInset = x;
		this.yInset = y;
	}

	@Override
	public void draw(Canvas c) {
		determineRelativeSize(c, imageFrames[currentFrame]);
		c.drawBitmap(imageFrames[currentFrame], xPosition * c.getWidth()
				- alignmentX + xInset, yPosition * c.getHeight() - alignmentY
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

}
