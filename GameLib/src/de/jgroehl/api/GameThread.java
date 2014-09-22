package de.jgroehl.api;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import de.jgroehl.api.utils.FpsMeter;

public class GameThread extends Thread {

	private static final String TAG = GameThread.class.getSimpleName();

	private static final int MAX_FRAMES_SKIPPED = 4;

	private final AbstractGamePanel gamePanel;
	private final SurfaceHolder surfaceHolder;
	private final AbstractMainActivity abstractMainActivity;

	private static boolean running;
	private final FpsMeter fpsMeter = new FpsMeter();

	public GameThread(SurfaceHolder surfaceHolder, AbstractGamePanel gamePanel,
			AbstractMainActivity abstractMainActivity) {
		this.gamePanel = gamePanel;
		this.surfaceHolder = surfaceHolder;
		this.abstractMainActivity = abstractMainActivity;
	}

	public void setRunning(boolean running) {
		GameThread.running = running;
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

	public boolean isRunning() {
		return running;
	}
}
