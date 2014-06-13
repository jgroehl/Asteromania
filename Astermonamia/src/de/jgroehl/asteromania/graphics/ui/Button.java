package de.jgroehl.asteromania.graphics.ui;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.interfaces.EventCallback;
import de.jgroehl.asteromania.graphics.GameObject;
import de.jgroehl.asteromania.graphics.interfaces.Clickable;

public class Button extends GameObject implements Clickable {

	private final float width;
	private final float height;
	private final String buttonText;
	private Paint textPaint;
	private Rect textBounds = new Rect();
	private RectF drawRect;
	private Bitmap background;
	private Bitmap ninePatch;
	private Bitmap icon;
	private Bitmap rawIcon;
	private final Paint graphicsPaint = new Paint();
	private final EventCallback callback;

	public Button(String buttonText, float xPosition, float yPosition,
			float width, float height, Resources resources,
			EventCallback callback) {
		super(xPosition, yPosition);

		ninePatch = BitmapFactory.decodeResource(resources, R.drawable.button);
		this.buttonText = buttonText;

		this.width = width;
		this.height = height;

		this.callback = callback;

	}

	public Button(Bitmap icon, float xPosition, float yPosition, float width,
			float height, Resources resources, EventCallback callback) {
		super(xPosition, yPosition);

		this.rawIcon = icon;

		buttonText = null;
		ninePatch = BitmapFactory.decodeResource(resources, R.drawable.button);

		this.width = width;
		this.height = height;

		this.callback = callback;
	}

	@Override
	public boolean isClicked(int x, int y, int screenWidth, int screenHeight) {
		return xPosition * screenWidth - width * screenWidth / 2 < x
				&& xPosition * screenWidth + width * screenWidth / 2 > x
				&& yPosition * screenHeight - height * screenHeight / 2 < y
				&& yPosition * screenHeight + height * screenHeight / 2 > y;
	}

	@Override
	public void performAction(GameHandler gameHandler) {
		gameHandler.getSoundManager().playClickSound();
		callback.action(gameHandler);
	}

	@Override
	public void draw(Canvas c) {
		if (drawRect == null) {
			drawRect = new RectF(xPosition * c.getWidth() - (width / 2)
					* c.getWidth(), yPosition * c.getHeight() - (height / 2)
					* c.getHeight(), xPosition * c.getWidth() + (width / 2)
					* c.getWidth(), yPosition * c.getHeight() + (height / 2)
					* c.getHeight());
		}
		if (background == null) {
			background = createBackground(c.getWidth(), c.getHeight(),
					createNinePatch(ninePatch));
			ninePatch = null;
		}
		if (buttonText != null && textPaint == null) {
			textPaint = setupTextPaint(c.getHeight());
		}
		if (rawIcon != null && icon == null) {
			icon = Bitmap.createScaledBitmap(rawIcon,
					(int) (width * c.getWidth() * 0.75),
					(int) (height * c.getHeight() * 0.75), true);
			rawIcon = null;
		}
		c.drawBitmap(background,
				xPosition * c.getWidth() - width * c.getWidth() / 2, yPosition
						* c.getHeight() - height * c.getHeight() / 2,
				graphicsPaint);
		if (buttonText != null) {
			drawText(c, 2, 2, Color.LTGRAY);
			drawText(c, 0, 0, Color.GRAY);
			drawText(c, -1, -1, Color.DKGRAY);
		} else if (icon != null) {
			c.drawBitmap(icon, xPosition * c.getWidth() - width * c.getWidth()
					* 3 / 8, yPosition * c.getHeight() - height * c.getHeight()
					/ 2, graphicsPaint);
		}

	}

	private void drawText(Canvas c, int xTrans, int yTrans, int color) {
		textPaint.setColor(color);
		c.drawText(buttonText, xPosition * c.getWidth() - width * c.getWidth()
				/ 2 + (drawRect.width() - textBounds.width()) / 2 + xTrans,
				yPosition * c.getHeight() + height * c.getHeight() * 2 / 5
						- textPaint.getFontMetrics().bottom + yTrans, textPaint);
	}

	@Override
	public void update(GameHandler gameHandler) {
		// Nothing to do here
	}

	private Bitmap createBackground(int width, int height, Bitmap[] ninePatch) {
		int targetWidth = (int) (this.width * width);
		int targetHeight = (int) (this.height * height);

		Bitmap result = Bitmap.createBitmap(targetWidth, targetHeight,
				Bitmap.Config.ARGB_8888);

		Canvas c = new Canvas(result);

		int xDiff = targetWidth - ninePatch[0].getWidth() * 2;
		int yDiff = targetHeight - ninePatch[0].getHeight() * 2;

		if (xDiff >= 0 && yDiff >= 0) {

			Bitmap top = Bitmap
					.createBitmap(ninePatch[1], ninePatch[1].getWidth() / 2, 0,
							1, ninePatch[1].getHeight());
			Bitmap left = Bitmap.createBitmap(ninePatch[3], 0,
					ninePatch[3].getHeight() / 2, ninePatch[3].getWidth(), 1);
			Bitmap right = Bitmap.createBitmap(ninePatch[5], 0,
					ninePatch[5].getHeight() / 2, ninePatch[5].getWidth(), 1);
			Bitmap bottom = Bitmap
					.createBitmap(ninePatch[7], ninePatch[7].getWidth() / 2, 0,
							1, ninePatch[7].getHeight());

			c.drawBitmap(ninePatch[0], 0, 0, graphicsPaint);
			c.drawBitmap(ninePatch[2], c.getWidth() - ninePatch[2].getWidth(),
					0, graphicsPaint);
			c.drawBitmap(ninePatch[6], 0,
					c.getHeight() - ninePatch[6].getHeight(), graphicsPaint);
			c.drawBitmap(ninePatch[8], c.getWidth() - ninePatch[8].getWidth(),
					c.getHeight() - ninePatch[8].getHeight(), graphicsPaint);
			for (int x = ninePatch[0].getWidth(); x < targetWidth
					- ninePatch[0].getWidth(); x++) {
				c.drawBitmap(top, x, 0, graphicsPaint);
				c.drawBitmap(bottom, x, targetHeight - bottom.getHeight(),
						graphicsPaint);
			}
			for (int y = ninePatch[0].getHeight(); y < targetHeight
					- ninePatch[0].getHeight(); y++) {
				c.drawBitmap(left, 0, y, graphicsPaint);
				c.drawBitmap(right, targetWidth - right.getWidth(), y,
						graphicsPaint);
			}
			Paint centerPaint = new Paint();
			centerPaint.setColor(ninePatch[4].getPixel(
					ninePatch[4].getWidth() / 2, ninePatch[4].getHeight() / 2));
			for (int y = ninePatch[0].getHeight(); y < targetHeight
					- ninePatch[0].getHeight(); y++) {
				for (int x = ninePatch[0].getWidth(); x < targetWidth
						- ninePatch[0].getWidth(); x++) {
					c.drawPoint(x, y, centerPaint);
				}
			}

		} else {
			for (int i = 0; i < ninePatch.length; i++) {
				ninePatch[i] = Bitmap.createScaledBitmap(ninePatch[i],
						(int) (ninePatch[i].getWidth() * 0.9),
						(int) (ninePatch[i].getHeight() * 0.9), true);
			}
			return createBackground(width, height, ninePatch);
		}

		return result;
	}

	private Paint setupTextPaint(int height) {
		Paint textPaint = new Paint();
		textPaint.setStyle(Style.FILL);
		textPaint.setTextSize((int) (this.height * height * 0.666));
		textPaint.setColor(Color.BLACK);
		textPaint.setTypeface(Typeface.create("CALIBRI", Typeface.BOLD));
		textPaint.getTextBounds(buttonText, 0, buttonText.length(), textBounds);
		return textPaint;
	}

	private Bitmap[] createNinePatch(Bitmap buttonGraphics) {

		Bitmap[] ninePatch = new Bitmap[9];
		int i = 0;
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++, i++) {
				ninePatch[i] = Bitmap.createBitmap(buttonGraphics, x
						* buttonGraphics.getWidth() / 3,
						y * buttonGraphics.getHeight() / 3,
						buttonGraphics.getWidth() / 3,
						buttonGraphics.getHeight() / 3);
			}
		}
		return ninePatch;
	}

}
