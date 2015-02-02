package de.asteromania.dgvk.control;

import android.graphics.Canvas;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.graphics.interfaces.Drawable;
import de.jgroehl.api.graphics.interfaces.Updatable;
import de.jgroehl.api.utils.Point2d;

public class Map implements Drawable, Updatable
{
	private Point2d mapPosition;
	

	public Map(Point2d initialMapPosition)
	{
		mapPosition = initialMapPosition;
	}

	public Point2d getMapPosition()
	{
		return mapPosition;
	}

	public void updateMapPosition(float dx, float dy)
	{
		mapPosition.update(dx, dy);
	}

	@Override
	public void update(BaseGameHandler gameHandler)
	{

	}

	@Override
	public void draw(Canvas c)
	{

	}

}
