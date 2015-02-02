package de.asteromania.dgvk.map;

import android.content.Context;
import android.graphics.Canvas;
import de.asteromania.dgvk.control.Map;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.graphics.GraphicsObject;

public class MapGraphicsObject extends GraphicsObject
{

	private final Map map;

	public MapGraphicsObject(Map map, float xPosition, float yPosition, int graphicsId, float relativeWidth,
			Context context)
	{
		super(xPosition, yPosition, graphicsId, relativeWidth, context);

		this.map = map;
	}

	@Override
	public void update(BaseGameHandler gameHandler)
	{

	}

	@Override
	public float getX()
	{
		return map.getMapPosition().getX() + super.getX();
	}

	@Override
	public float getY()
	{
		return map.getMapPosition().getY() + super.getY();
	}

	@Override
	public void draw(Canvas c)
	{
		if (getX() > -.5 || getX() < 1.5 && getY() > -.5 || getY() < 1.5)
			super.draw(c);
	}

}
