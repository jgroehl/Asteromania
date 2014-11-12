package de.jgroehl.api;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import de.jgroehl.api.activities.AbstractSimpleActivity;
import de.jgroehl.api.control.interfaces.KeyEventListener;

/**
 *
 * @author Janek Gröhl
 *
 */
public abstract class AbstractGamePanel extends SurfaceView implements SurfaceHolder.Callback
{

	private static final String TAG = null;
	private final AbstractSimpleActivity abstractMainActivity;
	private final List<KeyEventListener> keyEventListener = new ArrayList<KeyEventListener>();
	private GameThread thread;

	public AbstractGamePanel(Context context, AbstractSimpleActivity abstractMainActivity)
	{
		super(context);

		this.abstractMainActivity = abstractMainActivity;
		getHolder().addCallback(this);

		setFocusable(true);
		setDrawingCacheEnabled(true);
	}

	/**
	 * Updates the game state.
	 */
	public abstract void updateGameState();

	/**
	 * Displays the game state onto the given canvas object.
	 * 
	 * @param c
	 */
	public abstract void displayGameState(Canvas c);

	/**
	 * Does updates necessary before the updateGameState() method is called.
	 */
	public abstract void update();

	/**
	 * Initializes the Game Objects.
	 */
	public abstract void initializeGameObjects();

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		switch (keyCode)
		{
			case KeyEvent.KEYCODE_ENTER:
				for (KeyEventListener l : keyEventListener)
				{
					l.completedInput();
				}
				break;
			case KeyEvent.KEYCODE_DEL:
				for (KeyEventListener l : keyEventListener)
				{
					l.charDeleted();
				}
				break;
			default:
				for (KeyEventListener l : keyEventListener)
				{
					l.charEntered((char) event.getUnicodeChar());
				}
				break;
		}
		return super.onKeyUp(keyCode, event);
	}

	/**
	 * Sets a keyEventListener, which will be called whenever the
	 * {@link #onKeyUp(int, KeyEvent)}-Method is called. <br>
	 * <br>
	 * The listener will only be added once. Further calls of this method with
	 * the same listener instance will be ignored.<br>
	 * <br>
	 * 
	 * @see {@link #removeKeyEventListener(KeyEventListener)}
	 * 
	 * @param listener
	 */
	public void setKeyEventListener(KeyEventListener listener)
	{
		keyEventListener.clear();
		keyEventListener.add(listener);
	}

	/**
	 * Removes the given listener if registered.
	 * 
	 * @see {@link #setKeyEventListener(KeyEventListener)}
	 * 
	 * @param listener
	 */
	public void removeKeyEventListener(KeyEventListener listener)
	{
		keyEventListener.remove(listener);
	}

	protected GameThread getThread()
	{
		return thread;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		Log.d(TAG, "Surface changed called...");
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus)
	{
		Log.d(TAG, "Focus changed " + (hasWindowFocus ? "to Asteromania" : "to another program"));
		super.onWindowFocusChanged(hasWindowFocus);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		Log.d(TAG, "Try starting application...");
		if (thread == null)
		{
			Log.d(TAG, "Creating new MainThread...");
			thread = new GameThread(this.getHolder(), this, abstractMainActivity);
			Log.d(TAG, "Creating new MainThread...[Done]");
		}
		if (!getThread().isRunning())
		{
			if (!getThread().hasBeenRunningAtLeastOnce())
				initializeGameObjects();
			thread.start();
		}
		else
		{
			Log.d(TAG, "Application already running.");
		}
		Log.d(TAG, "Try starting application...[Done]");
	}

	private void shutDown()
	{
		if (!(thread == null))
		{
			boolean retry = true;
			while (retry)
			{
				try
				{
					thread.setRunning(false);
					thread.join();
					Log.d(TAG, "Releasing MainThread...");
					thread = null;
					Log.d(TAG, "Releasing MainThread...[Done]");
					retry = false;
				}
				catch (InterruptedException e)
				{
					Log.w(TAG, "Thread was not stopped.");
				}
			}
		}
		else
		{
			Log.d(TAG, "MainThread already released.");
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		Log.d(TAG, "Try stopping application...");
		shutDown();
		Log.d(TAG, "Try stopping application...[Done]");
	}

}
