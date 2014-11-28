package de.jgroehl.asteromania;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import de.jgroehl.api.AbstractGamePanel;
import de.jgroehl.api.activities.AbstractGooglePlayGamesLoginActivity;
import de.jgroehl.api.control.GameState;
import de.jgroehl.api.crypto.CryptoHandler;
import de.jgroehl.api.graphics.interfaces.Drawable;
import de.jgroehl.api.graphics.interfaces.Updatable;
import de.jgroehl.api.io.FileHandler;
import de.jgroehl.api.utils.SensorHandler;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;
import de.jgroehl.asteromania.control.GameSetup;
import de.jgroehl.asteromania.control.GoogleApiHandler;
import de.jgroehl.asteromania.control.SoundManager;
import de.jgroehl.asteromania.control.Transition;
import de.jgroehl.asteromania.graphics.ui.Highscore;

public class AsteromaniaGamePanel extends AbstractGamePanel
{

	private static final String TAG = AsteromaniaGamePanel.class.getSimpleName();

	private final Paint backgroundPaint = new Paint();

	private final AsteromaniaGameHandler gameHandler;
	private GameSetup gameSetup = new GameSetup();

	public AsteromaniaGamePanel(Context context, GoogleApiHandler handler,
			AbstractGooglePlayGamesLoginActivity abstractMainActivity)
	{

		super(context, abstractMainActivity);

		FileHandler fileHandler = new FileHandler(new CryptoHandler(getContext()), getContext());
		gameHandler = new AsteromaniaGameHandler(GameState.MENU, new SoundManager(getContext()), getContext(),
				fileHandler, new SensorHandler(context, Context.SENSOR_SERVICE), new Transition(context),
				new Highscore(context, fileHandler), handler, this);

		backgroundPaint.setStyle(Paint.Style.FILL);
		backgroundPaint.setColor(Color.BLACK);

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		super.surfaceDestroyed(holder);
		gameHandler.getPlayerInfo().savePlayerInfo();
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		gameHandler.handleEvent(event, getContext(), getWidth(), getHeight());
		return super.onTouchEvent(event);
	}

	@Override
	public void updateGameState()
	{
		for (Updatable u : gameHandler.getAllUpdatableObjects())
		{
			u.update(gameHandler);
		}
	}

	@Override
	public void displayGameState(Canvas c)
	{

		if (c != null)
		{

			clearScreen(c);

			for (Drawable d : gameHandler.getAllDrawableObjects())
			{
				d.draw(c);
			}

			if (gameHandler.getGameState().equals(GameState.MAIN))
				gameHandler.getPlayerInfoDisplay().draw(c);

		}
		else
		{
			Log.e(TAG, "Severe error displaying game state: Canvas was null");
		}

	}

	private void clearScreen(Canvas c)
	{
		c.drawRect(new Rect(0, 0, c.getWidth(), c.getHeight()), backgroundPaint);
	}

	@Override
	public void update()
	{
		gameHandler.update();
	}

	public AsteromaniaGameHandler getGameHandler()
	{
		return gameHandler;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
	{
		super.surfaceChanged(arg0, arg1, arg2, arg3);
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0)
	{
		super.surfaceCreated(arg0);
	}

	@Override
	public void initializeGameObjects()
	{
		gameSetup.initializeGameObjects(gameHandler);
	}

	@Override
	public boolean gameObjectsInitialized()
	{
		return gameHandler.gameObjectsInitialized();
	}
}
