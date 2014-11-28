package de.jgroehl.api;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import de.jgroehl.api.activities.AbstractSimpleActivity;
import de.jgroehl.api.utils.FpsMeter;

/**
 * 
 * @author Janek Gröhl
 *
 */
public class GameThread extends Thread {

	private static final String TAG = GameThread.class.getSimpleName();

	private static final int MAX_FRAMES_SKIPPED = 4;

	private final AbstractGamePanel gamePanel;
	private final SurfaceHolder surfaceHolder;
	private final AbstractSimpleActivity abstractMainActivity;

	private static boolean hasBeenRunningAtLeastOnce = false;
	private boolean running;
	private final FpsMeter fpsMeter = new FpsMeter();

	/**
	 * Initializes the thread with the given parameters.
	 * 
	 * @param surfaceHolder
	 *            not <code>null</code>
	 * @param gamePanel
	 *            not <code>null</code>
	 * @param mainActivity
	 *            not <code>null</code>
	 */
	public GameThread(SurfaceHolder surfaceHolder, AbstractGamePanel gamePanel,
			AbstractSimpleActivity mainActivity) {

		if (surfaceHolder == null)
			throw new NullPointerException(
					"surfaceHolder was null in GameThread");
		if (gamePanel == null)
			throw new NullPointerException("gamePanel was null in GameThread");
		if (mainActivity == null)
			throw new NullPointerException(
					"mainActivity was null in GameThread");

		this.gamePanel = gamePanel;
		this.surfaceHolder = surfaceHolder;
		this.abstractMainActivity = mainActivity;
	}

	/**
	 * If set to false, the thread will stop running.
	 * 
	 * @param running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void run() {

		Log.i(TAG, "Performing gameloop...");
		running = true;
		hasBeenRunningAtLeastOnce = true;
		while (running) {

			long startTime = System.currentTimeMillis();

			int width = surfaceHolder.getSurfaceFrame().width();
			int height = surfaceHolder.getSurfaceFrame().height();

			Canvas c = null;
			try {
				c = surfaceHolder.lockCanvas();

				gamePanel.update();

				gamePanel.updateGameState();

				gamePanel.displayGameState(c);

				if (abstractMainActivity.isInDebug())
					diaplayDebugInfo(c, width, height);

				int framesSkipped = 0;
				while (!fpsMeter.doFpsCheck(startTime)
						&& (framesSkipped++) < MAX_FRAMES_SKIPPED) {
					startTime = System.currentTimeMillis();
					gamePanel.displayGameState(c);
					if (abstractMainActivity.isInDebug())
						diaplayDebugInfo(c, width, height);
				}
			} catch (Throwable e) {
				Log.e(TAG, "Error while displaying GameState by ExType: "
						+ e.getClass().getSimpleName());
				e.printStackTrace();
			} finally {
				if (c != null)
					surfaceHolder.unlockCanvasAndPost(c);
			}

		}
		Log.i(TAG, "Performing gameloop...[Done]");
	}

	private void diaplayDebugInfo(Canvas c, int width, int height) {
		fpsMeter.draw(c);
	}

	/**
	 * 
	 * @return true if the {@link GameThread} is still running.
	 */
	public boolean isRunning() {
		return running;
	}

	public boolean hasBeenRunningAtLeastOnce() {
		return hasBeenRunningAtLeastOnce;
	}
	
}