package de.jgroehl.api;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class AbstractGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = null;
	private final AbstractMainActivity abstractMainActivity;
	private GameThread thread;

	public AbstractGamePanel(Context context,
			AbstractMainActivity abstractMainActivity) {
		super(context);

		this.abstractMainActivity = abstractMainActivity;
		getHolder().addCallback(this);

		setFocusable(true);
		setDrawingCacheEnabled(true);
	}

	public abstract void updateGameState();

	public abstract void displayGameState(Canvas c);

	public abstract void update();

	public abstract void initializeGameObjects();

	protected GameThread getThread() {
		return thread;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d(TAG, "Surface changed called...");
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		Log.d(TAG, "Focus changed "
				+ (hasWindowFocus ? "to Asteromania" : "to another program"));
		super.onWindowFocusChanged(hasWindowFocus);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "Try starting application...");
		if (thread == null) {
			Log.d(TAG, "Creating new MainThread...");
			thread = new GameThread(this.getHolder(), this,
					abstractMainActivity);
			Log.d(TAG, "Creating new MainThread...[Done]");
		}
		if (!getThread().isRunning()) {
			initializeGameObjects();
			thread.start();
		} else {
			Log.d(TAG, "Application already running.");
		}
		Log.d(TAG, "Try starting application...[Done]");
	}

	private void shutDown() {
		if (!(thread == null)) {
			boolean retry = true;
			while (retry) {
				try {
					thread.setRunning(false);
					thread.join();
					Log.d(TAG, "Releasing MainThread...");
					thread = null;
					Log.d(TAG, "Releasing MainThread...[Done]");
					retry = false;
				} catch (InterruptedException e) {
					Log.w(TAG, "Thread was not stopped.");
				}
			}
		} else {
			Log.d(TAG, "MainThread already released.");
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Try stopping application...");
		shutDown();
		Log.d(TAG, "Try stopping application...[Done]");
	}

}
