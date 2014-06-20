package de.jgroehl.asteromania.utils;

import de.jgroehl.asteromania.graphics.interfaces.Drawable;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class FpsMeter implements Drawable {

	private static final long SECOND = 1000;
	private static final long TARGET_FPS = 40;
	private static final long FRAME_PERIOD = SECOND / TARGET_FPS;
	private static final String TAG = FpsMeter.class.getSimpleName();
	private static final int MEGABYTE = 1024 * 1024;
	private final Runtime runtime = Runtime.getRuntime();

	private int frames = 0;
	private long sleepTimes = 0;

	private long time = System.currentTimeMillis();

	private int lastFpsCount = 0;
	private long lastSleepTime = 0;

	Paint whitePaint = new Paint();

	public FpsMeter() {
		whitePaint.setTextSize(18);
		whitePaint.setColor(Color.rgb(255, 255, 255));
	}

	/**
	 * 
	 * @param startTime
	 * @return <code>true</code> if the interval between startTime and the time
	 *         when this method was called is smaller or equal to the given
	 *         frame period.
	 * 
	 *         Will return <code>false</code> if the interval was longer.
	 */
	public boolean doFpsCheck(long startTime) {

		if (System.currentTimeMillis() - time >= SECOND) {
			lastFpsCount = frames;
			if (frames > 0)
				lastSleepTime = sleepTimes / frames;
			time = System.currentTimeMillis();
			sleepTimes = 0;
			frames = 0;
		}

		long sleepTime = FRAME_PERIOD
				- (System.currentTimeMillis() - startTime);
		frames++;

		if (sleepTime >= 0) {
			try {
				sleepTimes = sleepTimes + sleepTime;
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				Log.e(TAG, "Sleeping on FPS Tracking interrupted.");
			}
			return true;
		} else {
			Log.d(TAG, "Too short sleepTime: "
					+ sleepTime);
			return false;
		}
	}

	public int getFps() {
		return lastFpsCount;
	}

	@Override
	public void draw(Canvas c) {
		c.drawText("FPS: " + getFps(), 5, 20, whitePaint);
		c.drawText("SLP: " + lastSleepTime + " / " + FRAME_PERIOD, 5, 40,
				whitePaint);
		c.drawText("RAM: " + (runtime.maxMemory() - runtime.freeMemory())
				/ MEGABYTE + " / " + runtime.maxMemory() / MEGABYTE, 5, 60,
				whitePaint);
	}

}
