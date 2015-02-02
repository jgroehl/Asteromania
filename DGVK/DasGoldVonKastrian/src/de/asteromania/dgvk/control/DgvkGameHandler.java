package de.asteromania.dgvk.control;

import android.content.Context;
import android.view.MotionEvent;
import de.asteromania.dgvk.activities.DgvkMainActivity;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.utils.Point2d;

public class DgvkGameHandler extends BaseGameHandler
{
	private Point2d lastTapPosition;
	private Map map;

	public DgvkGameHandler(UserDataHandler userDataHandler, DgvkMainActivity context)
	{
		super(GameState.MAIN, context);
		map = new Map(new Point2d(0, 0));
		lastTapPosition = map.getMapPosition();
	}

	public DgvkMainActivity getContext()
	{
		return (DgvkMainActivity) super.getContext();
	}

	@Override
	public void handleEvent(MotionEvent event, Context context, int screenWidth, int screenHeight)
	{
		lastTapPosition = new Point2d(event.getX() / screenWidth, event.getY() / screenHeight);
		super.handleEvent(event, context, screenWidth, screenHeight);
	}

	public Point2d getLastTapPosition()
	{
		return lastTapPosition;
	}

	public void updateMap(float dx, float dy)
	{
		map.updateMapPosition(dx, dy);
	}

	public Map getMap()
	{
		return map;
	}

}
