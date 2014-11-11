package de.jgroehl.asteromania.control;

import android.content.Context;
import android.graphics.Canvas;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.graphics.GameObject;

public class Transition extends GameObject
{

	private enum State
	{
		ACCELERATE, BRAKE, NOT_INITIALIZED, FINISHED
	}

	private static final float ACCELERATION_VALUE = 0.2f;

	private static final float MAX_ACCELERATION = 10.0f;

	private static final float MIN_ACCELERATION = 1.0f;

	private State state = State.FINISHED;

	public Transition(Context context)
	{
		super(0, 0, context);
	}

	@Override
	public void draw(Canvas c)
	{
	}

	@Override
	public void update(BaseGameHandler gameHandler)
	{

		if (gameHandler instanceof AsteromaniaGameHandler)
		{
			AsteromaniaGameHandler handler = (AsteromaniaGameHandler) gameHandler;
			switch (state)
			{
				case ACCELERATE:
					handler.getStarfield().accelerate(ACCELERATION_VALUE);
					if (handler.getStarfield().getAcceleration() >= MAX_ACCELERATION)
						state = State.BRAKE;
					break;
				case BRAKE:
					handler.getStarfield().accelerate(-ACCELERATION_VALUE);
					if (handler.getStarfield().getAcceleration() <= MIN_ACCELERATION)
						state = State.FINISHED;
					break;
				default:
					break;

			}
		}
	}

	public void initialize()
	{
		if (state != State.NOT_INITIALIZED)
			throw new IllegalStateException("Trying to initialize an already initialized Transition...");
		state = State.ACCELERATE;
	}

	public boolean isInitialized()
	{
		return state != State.NOT_INITIALIZED;
	}

	public boolean isFinished()
	{
		return state == State.FINISHED;
	}

	public void reset()
	{
		state = State.NOT_INITIALIZED;
	}
}
