package de.jgroehl.api.utils;

/**
 * 
 * @author Janek Gröhl
 *
 */
public class Point2d
{

	private float x;
	private float y;

	public Point2d(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public void update(float dx, float dy)
	{
		x += dx;
		y += dy;
	}
}
