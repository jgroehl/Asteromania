package de.jgroehl.asteromania.graphics.animated;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.SparseArray;
import de.jgroehl.asteromania.graphics.GraphicsObject;
import de.jgroehl.asteromania.time.Timer;

public abstract class AnimatedGraphicsObject extends GraphicsObject {

	private static final SparseArray<Bitmap[]> imageCache = new SparseArray<Bitmap[]>();

	private final Paint imagePaint = new Paint();
	protected final int imageResource;
	private int currentFrame;
	private final int maxFrames;
	protected final Timer animationTimer;

	public AnimatedGraphicsObject(float xPosition, float yPosition,
			int imageResource, int frameCount, int animationPeriod,
			Context context) {
		super(xPosition, yPosition, null, context);

		this.imageResource = imageResource;
		animationTimer = new Timer(animationPeriod);
		imagePaint.setStyle(Paint.Style.FILL);

		maxFrames = frameCount;

		if (imageCache.get(imageResource) == null) {
			Bitmap graphics = BitmapFactory.decodeResource(
					context.getResources(), imageResource);
			int width = graphics.getWidth() / frameCount;
			int height = graphics.getHeight();
			Bitmap[] imageFrames = new Bitmap[frameCount];

			for (int i = 0; i < maxFrames; i++) {
				imageFrames[i] = Bitmap.createBitmap(graphics, i * width, 0,
						width, height);
			}
			imageCache.put(imageResource, imageFrames);
		}

	}

	@Override
	public void draw(Canvas c) {
		determineRelativeSize(c, imageCache.get(imageResource)[currentFrame]);
		c.drawBitmap(imageCache.get(imageResource)[currentFrame],
				xPosition * c.getWidth(), yPosition * c.getHeight(), imagePaint);
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
