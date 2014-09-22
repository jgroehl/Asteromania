package de.jgroehl.api.time;

public class Timer {

	private int period;
	private long time = System.currentTimeMillis();

	public Timer(int period) {
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

	public void reset() {
		time = System.currentTimeMillis();
	}

	public void reset(int period) {
		this.period = period;
		reset();
	}
}
