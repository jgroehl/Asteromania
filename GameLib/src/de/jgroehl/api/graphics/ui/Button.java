package de.jgroehl.api.graphics.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.interfaces.EventCallback;
import de.jgroehl.gamelib.R;

public class Button extends AbstractClickableElement
{

	private String buttonText;

	public String getButtonText()
	{
		return buttonText;
	}

	public void setButtonText(String buttonText)
	{
		if (buttonText == null)
			throw new IllegalStateException("Buttontext must not be null");
		this.buttonText = buttonText;
	}

	private Paint textPaint;
	private Rect textBounds = new Rect();
	private RectF drawRect;
	private Bitmap ninePatch;
	private Bitmap rawIcon;
	private final Paint graphicsPaint = new Paint();

	public Button(String buttonText, float xPosition, float yPosition, float width, float height,
			EventCallback callback, Context context)
	{
		super(xPosition, yPosition, INVALID_GRAPHICS_ID, width, callback, context);

		ninePatch = BitmapFactory.decodeResource(context.getResources(), R.drawable.button);
		this.buttonText = buttonText;

		this.relativeWidth = width;
		this.relativeHeight = height;

	}

	public Button(Bitmap icon, float xPosition, float yPosition, float width, float height, EventCallback callback,
			Context context)
	{
		super(xPosition, yPosition, INVALID_GRAPHICS_ID, width, callback, context);

		this.rawIcon = icon;

		buttonText = null;
		ninePatch = BitmapFactory.decodeResource(context.getResources(), R.drawable.button);

		this.relativeWidth = width;
		this.relativeHeight = height;

	}

	@Override
	public void draw(Canvas c)
	{
		if (drawRect == null)
		{
			drawRect = new RectF(getX() * c.getWidth() - (relativeWidth / 2) * c.getWidth(), getY() * c.getHeight()
					- (relativeHeight / 2) * c.getHeight(), getX() * c.getWidth() + (relativeWidth / 2) * c.getWidth(),
					getY() * c.getHeight() + (relativeHeight / 2) * c.getHeight());
		}
		if (buttonText != null && textPaint == null)
		{
			textPaint = setupTextPaint(c.getHeight());
		}
		if (graphicsId == INVALID_GRAPHICS_ID)
		{
			graphicsId = getGraphicsId();
			if (imageCache.get(graphicsId) == null)
			{
				Bitmap b = Bitmap
						.createBitmap(createBackground(c.getWidth(), c.getHeight(), createNinePatch(ninePatch)));
				addGraphics(graphicsId, new Bitmap[] { b });
				Canvas c2 = new Canvas(imageCache.get(graphicsId)[0]);
				if (buttonText != null)
				{
					drawText(c2, 2, 2, Color.LTGRAY);
					drawText(c2, 0, 0, Color.GRAY);
					drawText(c2, -1, -1, Color.DKGRAY);
				}
				if (rawIcon != null)
				{
					Log.i("IMPORTANT", "created icon!");
					c2.drawBitmap(Bitmap.createScaledBitmap(rawIcon, (int) (relativeWidth * c.getWidth() * 0.75),
							(int) (relativeHeight * c.getHeight() * 0.75), true), (float) (c2.getWidth() * 0.125),
							(float) (c2.getHeight() * 0.0625), graphicsPaint);
					rawIcon = null;
				}
				ninePatch = null;
			}
		}

		super.draw(c);

	}

	private int getGraphicsId()
	{
		int value = (int) (relativeWidth * 1000 * 31);
		value += (int) (relativeHeight * 1000000 * 31);
		if (buttonText != null)
			value += buttonText.hashCode();
		if (rawIcon != null)
			value += rawIcon.hashCode();
		return value;
	}

	private void drawText(Canvas c, int xTrans, int yTrans, int color)
	{
		textPaint.setColor(color);
		c.drawText(buttonText, (drawRect.width() - textBounds.width()) / 2 + xTrans, drawRect.height() * 11 / 16
				+ yTrans, textPaint);
	}

	@Override
	public void update(BaseGameHandler gameHandler)
	{
		// Nothing to do here
	}

	private Bitmap createBackground(int width, int height, Bitmap[] ninePatch)
	{
		int targetWidth = (int) (this.relativeWidth * width);
		int targetHeight = (int) (this.relativeHeight * height);

		Bitmap result = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);

		Canvas c = new Canvas(result);

		int xDiff = targetWidth - ninePatch[0].getWidth() * 2;
		int yDiff = targetHeight - ninePatch[0].getHeight() * 2;

		if (xDiff >= 0 && yDiff >= 0)
		{

			Bitmap top = Bitmap.createBitmap(ninePatch[1], ninePatch[1].getWidth() / 2, 0, 1, ninePatch[1].getHeight());
			Bitmap left = Bitmap
					.createBitmap(ninePatch[3], 0, ninePatch[3].getHeight() / 2, ninePatch[3].getWidth(), 1);
			Bitmap right = Bitmap.createBitmap(ninePatch[5], 0, ninePatch[5].getHeight() / 2, ninePatch[5].getWidth(),
					1);
			Bitmap bottom = Bitmap.createBitmap(ninePatch[7], ninePatch[7].getWidth() / 2, 0, 1,
					ninePatch[7].getHeight());

			c.drawBitmap(ninePatch[0], 0, 0, graphicsPaint);
			c.drawBitmap(ninePatch[2], c.getWidth() - ninePatch[2].getWidth(), 0, graphicsPaint);
			c.drawBitmap(ninePatch[6], 0, c.getHeight() - ninePatch[6].getHeight(), graphicsPaint);
			c.drawBitmap(ninePatch[8], c.getWidth() - ninePatch[8].getWidth(),
					c.getHeight() - ninePatch[8].getHeight(), graphicsPaint);
			for (int x = ninePatch[0].getWidth(); x < targetWidth - ninePatch[0].getWidth(); x++)
			{
				c.drawBitmap(top, x, 0, graphicsPaint);
				c.drawBitmap(bottom, x, targetHeight - bottom.getHeight(), graphicsPaint);
			}
			for (int y = ninePatch[0].getHeight(); y < targetHeight - ninePatch[0].getHeight(); y++)
			{
				c.drawBitmap(left, 0, y, graphicsPaint);
				c.drawBitmap(right, targetWidth - right.getWidth(), y, graphicsPaint);
			}
			Paint centerPaint = new Paint();
			centerPaint.setColor(ninePatch[4].getPixel(ninePatch[4].getWidth() / 2, ninePatch[4].getHeight() / 2));
			for (int y = ninePatch[0].getHeight(); y < targetHeight - ninePatch[0].getHeight(); y++)
			{
				for (int x = ninePatch[0].getWidth(); x < targetWidth - ninePatch[0].getWidth(); x++)
				{
					c.drawPoint(x, y, centerPaint);
				}
			}

		}
		else
		{
			for (int i = 0; i < ninePatch.length; i++)
			{
				ninePatch[i] = Bitmap.createScaledBitmap(ninePatch[i], (int) (ninePatch[i].getWidth() * 0.9),
						(int) (ninePatch[i].getHeight() * 0.9), true);
			}
			return createBackground(width, height, ninePatch);
		}

		return result;
	}

	private Paint setupTextPaint(int height)
	{
		Paint textPaint = new Paint();
		textPaint.setStyle(Style.FILL);
		textPaint.setTextSize((int) (this.relativeHeight * height * 0.666));
		textPaint.setColor(Color.BLACK);
		textPaint.setTypeface(Typeface.create("CALIBRI", Typeface.BOLD));
		textPaint.getTextBounds(buttonText, 0, buttonText.length(), textBounds);
		return textPaint;
	}

	private Bitmap[] createNinePatch(Bitmap buttonGraphics)
	{

		Bitmap[] ninePatch = new Bitmap[9];
		int i = 0;
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++, i++)
			{
				ninePatch[i] = Bitmap
						.createBitmap(buttonGraphics, x * buttonGraphics.getWidth() / 3, y * buttonGraphics.getHeight()
								/ 3, buttonGraphics.getWidth() / 3, buttonGraphics.getHeight() / 3);
			}
		}
		return ninePatch;
	}
}
