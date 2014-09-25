package de.jgroehl.api.graphics.statusBars;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class FramedHpBar extends HpBar {

	private Bitmap frame;
	private Bitmap ninePatch;
	private final Paint paint = new Paint();
	private int xDiff;

	public FramedHpBar(int maximum, int currentValue, float xPosition,
			float yPosition, float relativeWidth, float relativeHeight,
			Context context, int frameGraphicsId) {
		super(maximum, currentValue, xPosition, yPosition, relativeWidth,
				relativeHeight, context);
		ninePatch = BitmapFactory.decodeResource(context.getResources(),
				frameGraphicsId);
	}

	public FramedHpBar(int maximum, float xPosition, float yPosition,
			float relativeWidth, float relativeHeight, Context context,
			int frameGraphicsId) {
		super(maximum, xPosition, yPosition, relativeWidth, relativeHeight,
				context);
		ninePatch = BitmapFactory.decodeResource(context.getResources(),
				frameGraphicsId);
	}

	@Override
	public void draw(Canvas c) {
		super.draw(c);
		if (frame == null) {
			frame = constructFrame(fullHpBounds, createSixPatch(ninePatch));
			ninePatch = null;
		}
		c.drawBitmap(frame, fullHpBounds.left - xDiff, fullHpBounds.top, paint);
	}

	private Bitmap constructFrame(RectF bounds, Bitmap[] createNinePatch) {
		float vSize = bounds.bottom - bounds.top;
		float hSize = bounds.right - bounds.left;

		convertSize(createNinePatch, 0, vSize, false);
		convertSize(createNinePatch, 1, vSize, true);
		convertSize(createNinePatch, 2, vSize, false);
		convertSize(createNinePatch, 3, vSize, false);
		convertSize(createNinePatch, 4, vSize, true);
		convertSize(createNinePatch, 5, vSize, false);

		Bitmap returnBitmap = Bitmap.createBitmap(
				(int) (hSize + 2 * createNinePatch[0].getWidth()), (int) vSize,
				Bitmap.Config.ARGB_8888);

		xDiff = createNinePatch[0].getWidth();

		Canvas c = new Canvas(returnBitmap);

		c.drawBitmap(createNinePatch[0], 0, 0, paint);
		c.drawBitmap(createNinePatch[3], 0, vSize / 2, paint);
		c.drawBitmap(createNinePatch[2], c.getWidth() - xDiff, 0, paint);
		c.drawBitmap(createNinePatch[5], c.getWidth() - xDiff, vSize / 2, paint);

		for (int x = xDiff; x < hSize + xDiff; x++) {
			c.drawBitmap(createNinePatch[1], x, 0, paint);
			c.drawBitmap(createNinePatch[4], x, vSize / 2, paint);
		}

		return returnBitmap;
	}

	private void convertSize(Bitmap[] createNinePatch, int i, float vSize,
			boolean trim) {
		createNinePatch[i] = Bitmap.createScaledBitmap(
				createNinePatch[i],
				trim ? 1 : Math.round((vSize / 2)
						/ createNinePatch[i].getHeight()
						* createNinePatch[i].getWidth()),
				Math.round(vSize / 2), false);
	}

	private Bitmap[] createSixPatch(Bitmap buttonGraphics) {

		Bitmap[] ninePatch = new Bitmap[9];
		int i = 0;
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 3; x++, i++) {
				ninePatch[i] = Bitmap.createBitmap(buttonGraphics, x
						* buttonGraphics.getWidth() / 3,
						y * buttonGraphics.getHeight() / 2,
						buttonGraphics.getWidth() / 3,
						buttonGraphics.getHeight() / 2);
			}
		}
		return ninePatch;
	}

}
