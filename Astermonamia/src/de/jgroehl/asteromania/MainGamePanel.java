package de.jgroehl.asteromania;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.GameSetup;
import de.jgroehl.asteromania.control.GameState;
import de.jgroehl.asteromania.control.SoundManager;
import de.jgroehl.asteromania.control.Transition;
import de.jgroehl.asteromania.crypto.CryptoHandler;
import de.jgroehl.asteromania.graphics.interfaces.Drawable;
import de.jgroehl.asteromania.graphics.interfaces.Updatable;
import de.jgroehl.asteromania.sensoryInfo.SensorHandler;

public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();
	private final MainThread thread;

	private final Paint backgroundPaint = new Paint();

	private final GameHandler gameHandler;
	private GameSetup gameSetup = new GameSetup();

	public MainGamePanel(Context context) {

		super(context);

		gameHandler = new GameHandler(GameState.MENU, new SoundManager(
				getContext()), getContext(), new CryptoHandler(getResources()),
				new SensorHandler(context, Context.SENSOR_SERVICE),
				new Transition(context));
		

		getHolder().addCallback(this);

		thread = new MainThread(this.getHolder(), this);

		setFocusable(true);

		backgroundPaint.setStyle(Paint.Style.FILL);
		backgroundPaint.setColor(Color.BLACK);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d(TAG, "Surface changed called...");
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		Log.d(TAG, "Focus changed called");
		super.onWindowFocusChanged(hasWindowFocus);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "Try starting application...");
		if (!thread.isRunning()) {
			gameSetup.initializeGameObjects(gameHandler);
			thread.start();
		} else {
			Log.d(TAG, "Application already running.");
		}
		Log.d(TAG, "Try starting application...[Done]");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Try stopping application...");
		boolean retry = true;
		while (retry) {
			try {
				thread.setRunning(false);
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				Log.w(TAG, "Thread was not stopped.");
			}
		}
		gameHandler.getPlayerInfo().savePlayerInfo();
		Log.d(TAG, "Try stopping application...[Done]");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gameHandler.handleEvent(event, getContext(), getWidth(), getHeight());
		return super.onTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
	}

	public void updateGameState() {
		for (Updatable u : gameHandler.getAllUpdatableObjects()) {
			u.update(gameHandler);
		}
	}

	public void displayGameState(Canvas c) {

		if (c != null) {

			clearScreen(c);

			for (Drawable d : gameHandler.getAllDrawableObjects()) {
				d.draw(c);
			}

			if (gameHandler.getGameState().equals(GameState.MAIN))
				gameHandler.getPlayerInfoDisplay().draw(c);

		} else {
			Log.e(TAG, "Severe error displaying game state: Canvas was null");
		}

	}

	private void clearScreen(Canvas c) {
		c.drawRect(new Rect(0, 0, c.getWidth(), c.getHeight()), backgroundPaint);
	}

	public void update() {
		gameHandler.update();
	}

	public GameHandler getGameHandler() {
		return gameHandler;
	}

}
