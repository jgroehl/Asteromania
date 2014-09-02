package de.jgroehl.asteromania;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import de.jgroehl.asteromania.utils.FpsMeter;

public class MainThread extends Thread {

	private static final String TAG = MainThread.class.getSimpleName();

	private static final int MAX_FRAMES_SKIPPED = 4;

	private final MainGamePanel gamePanel;
	private final SurfaceHolder surfaceHolder;

	private boolean running;
	private final FpsMeter fpsMeter = new FpsMeter();

	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		this.gamePanel = gamePanel;
		this.surfaceHolder = surfaceHolder;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void run() {

		Log.i(TAG, "Performing gameloop...");
		running = true;
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

				if (MainActivity.DEBUG)
					diaplayDebugInfo(c, width, height);

				int framesSkipped = 0;
				while (!fpsMeter.doFpsCheck(startTime)
						&& (framesSkipped++) < MAX_FRAMES_SKIPPED) {
					startTime = System.currentTimeMillis();
					gamePanel.displayGameState(c);
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

	public boolean isRunning() {
		return running;
	}
}
