package de.asteromania.dgvk.control;

import android.content.Context;
import android.view.MotionEvent;
import de.asteromania.dgvk.activities.DgvkMainActivity;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.utils.Point2d;

public class DgvkGameHandler extends BaseGameHandler
{

	private static final String TAG = DgvkGameHandler.class.getSimpleName();

	private Point2d lastTapPosition;

	public DgvkGameHandler(UserDataHandler userDataHandler, DgvkMainActivity context)
	{
		super(GameState.MAIN, context);
	}

	public DgvkMainActivity getContext()
	{
		return (DgvkMainActivity) super.getContext();
	}

	@Override
	public void handleEvent(MotionEvent event, Context context, int screenWidth, int screenHeight)
	{
		lastTapPosition = new Point2d(event.getX(), event.getY());
		super.handleEvent(event, context, screenWidth, screenHeight);
	}

}
