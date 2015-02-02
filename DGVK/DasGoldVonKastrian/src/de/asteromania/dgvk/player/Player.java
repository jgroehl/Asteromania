package de.asteromania.dgvk.player;

import android.content.Context;
import de.asteromania.dgvk.R;
import de.asteromania.dgvk.control.DgvkGameHandler;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.graphics.animated.AnimatedGraphicsObject;
import de.jgroehl.api.utils.Point2d;

public class Player extends AnimatedGraphicsObject
{

	private static final double STEP_WIDTH = 0.01;

	public Player(Context context)
	{
		super(0.45f, 0.45f, R.drawable.player, 0.1f, 2, 300, context);
	}

	@Override
	public void update(BaseGameHandler gameHandler)
	{
		DgvkGameHandler dgvkGameHandler = (DgvkGameHandler) gameHandler;
		Point2d tapPosition = dgvkGameHandler.getLastTapPosition();
		Point2d mapPosition = dgvkGameHandler.getMap().getMapPosition();
		if (isRelevantDistance(tapPosition, mapPosition))
		{
			setFrame((getFrame() + 1) % getMaxFrame());
			rotateTowardsLocation(tapPosition);
			dgvkGameHandler.updateMap((float) (-Math.cos(getRotation()) * STEP_WIDTH),
					(float) (Math.sin(getRotation()) * STEP_WIDTH));
		}

	}

	private boolean isRelevantDistance(Point2d tapPosition, Point2d mapPosition)
	{
		return Math.abs(tapPosition.getX() - mapPosition.getX() + tapPosition.getY() - mapPosition.getY()) > 2 * STEP_WIDTH;
	}

}
