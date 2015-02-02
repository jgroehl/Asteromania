package de.jgroehl.api.graphics;

import de.jgroehl.api.utils.Point2d;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.util.SparseArray;

public abstract class GraphicsObject extends GameObject
{

	protected static final SparseArray<Bitmap[]> imageCache = new SparseArray<Bitmap[]>();

	private enum DisplayType
	{
		AUTOSCALE, NONE;
	}

	private static final float DEFAULT_RELATIVE_SIZE = 0.0f;

	public static final int INVALID_GRAPHICS_ID = -1;

	private static final float NOT_ROTATED = 0;
	private final Paint imagePaint = new Paint();
	private final DisplayType displayType;
	protected float relativeWidth = DEFAULT_RELATIVE_SIZE;
	protected float relativeHeight = DEFAULT_RELATIVE_SIZE;
	private float rotation = NOT_ROTATED;
	protected int graphicsId;

	public GraphicsObject(float xPosition, float yPosition, float width, float height, Context context)
	{
		super(xPosition, yPosition, context);
		relativeWidth = width;
		relativeHeight = height;
		displayType = DisplayType.NONE;
		graphicsId = INVALID_GRAPHICS_ID;
	}

	public GraphicsObject(float xPosition, float yPosition, int graphicsId, float relativeWidth, Context context)
	{
		super(xPosition, yPosition, context);
		imagePaint.setStyle(Paint.Style.FILL);
		this.graphicsId = graphicsId;
		this.relativeWidth = relativeWidth;
		displayType = DisplayType.AUTOSCALE;
	}

	@Override
	public void draw(Canvas c)
	{

		float x = getX();
		float y = getY();

		startRotate(c);

		if (displayType != DisplayType.NONE)
		{
			if (imageCache.get(graphicsId) != null)
			{
				determineRelativeSize(c, imageCache.get(graphicsId)[0]);
				c.drawBitmap(imageCache.get(graphicsId)[0], getX() * c.getWidth(), getY() * c.getHeight(), imagePaint);
			}
			else
			{
				if (displayType == DisplayType.AUTOSCALE)
				{
					Bitmap tmpBitmap = BitmapFactory.decodeResource(getContext().getResources(), graphicsId);
					imageCache.put(graphicsId, new Bitmap[] { Bitmap.createScaledBitmap(tmpBitmap,
							(int) (relativeWidth * c.getWidth()),
							(int) (tmpBitmap.getHeight() * ((relativeWidth * c.getWidth()) / tmpBitmap.getWidth())),
							true) });
					determineRelativeSize(c, imageCache.get(graphicsId)[0]);
				}
				else
				{
					throw new IllegalStateException("Wrong displayType...");
				}
			}
		}

		finishRotate(c, x, y);

	}

	protected final void finishRotate(Canvas c, float x, float y)
	{
		if (rotation != NOT_ROTATED)
		{
			c.restore();
			setPosition(x, y);
		}
	}

	protected final void startRotate(Canvas c)
	{
		if (rotation != NOT_ROTATED)
		{
			c.save(Canvas.MATRIX_SAVE_FLAG);
			c.translate((getX() + getRelativeWidth() / 2) * c.getWidth(),
					(getY() + getRelativeHeight() / 2) * c.getHeight());
			c.rotate(-(float) (rotation * 180 / Math.PI));
			setPosition(0, 0);
		}
	}

	protected void determineRelativeSize(Canvas c, Bitmap graphics)
	{
		relativeHeight = ((float) graphics.getHeight()) / c.getHeight();
		relativeWidth = ((float) graphics.getWidth()) / c.getWidth();
	}

	public void setGraphics(int graphicsId)
	{
		this.graphicsId = graphicsId;
		if (graphicsId != INVALID_GRAPHICS_ID && imageCache.get(graphicsId) == null)
		{
			imageCache.put(graphicsId,
					new Bitmap[] { BitmapFactory.decodeResource(getContext().getResources(), graphicsId) });
		}
	}

	public float getRelativeHeight()
	{
		return relativeHeight;
	}

	public float getRelativeWidth()
	{
		return relativeWidth;
	}

	protected void addGraphics(int hashCode, Bitmap[] b)
	{
		if (imageCache.get(hashCode) == null)
		{
			this.graphicsId = hashCode;
			imageCache.put(hashCode, b);
			Log.d(GraphicsObject.class.getSimpleName(), "Added an image for hash (" + hashCode + ")");
		}
		else
		{
			Log.w(GraphicsObject.class.getSimpleName(), "Try setting a new image to cache even though"
					+ "there is already an image for hash (" + hashCode + ") present.");
		}
	}

	public void setRelativeHeight(float relativeHeight)
	{
		this.relativeHeight = relativeHeight;
	}

	public void setRelativeWidth(float relativeWidth)
	{
		this.relativeWidth = relativeWidth;
	}

	public void rotateTowardsLocation(float locX, float locY)
	{
		rotation = (float) Math.atan2(-(locY - getY()), (locX - getX()));
	}

	public void rotateTowardsLocation(Point2d p2d)
	{
		rotateTowardsLocation(p2d.getX(), p2d.getY());
	}

	public void moveInDirectionOfRotation(float stepWidth)
	{
		setPosition((float) (getX() + Math.cos(getRotation()) * stepWidth), (float) (getY() - Math.sin(getRotation())
				* stepWidth));
	}

	public float getRotation()
	{
		return rotation;
	}
}
