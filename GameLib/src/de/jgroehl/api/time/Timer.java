package de.jgroehl.api.time;

/**
 * This class can be used to keep track of timing events.
 * 
 * @author Janek
 *
 */
public class Timer {

	private int period;
	private long time = System.currentTimeMillis();

	/**
	 * 
	 * @param period
	 *            must not be < 0
	 */
	public Timer(int period) {
		if (period < 0)
			throw new IllegalArgumentException("The given period was negative.");
		this.period = period;
		time = System.currentTimeMillis();
	}

	/**
	 * Checks if the specified time has elapsed and resets the counter if it
	 * did.
	 * 
	 * @return true if the time period elapsed since last reset.
	 */
	public boolean isPeriodOver() {
		if (System.currentTimeMillis() - time > period) {
			time = System.currentTimeMillis();
			return true;
		}
		return false;
	}

	/**
	 * resets the timer to the standard period.
	 */
	public void reset() {
		time = System.currentTimeMillis();
	}

	/**
	 * resets the timer and updates the period.
	 * 
	 * @param period
	 *            must not be < 0
	 */
	public void reset(int period) {
		if (period < 0)
			throw new IllegalArgumentException("The given period was negative.");
		this.period = period;
		reset();
	}

	/**
	 * 
	 * @return the remaining time of this timer.
	 */
	public long getRemainingTime() {
		return period - (System.currentTimeMillis() - time);
	}
}
