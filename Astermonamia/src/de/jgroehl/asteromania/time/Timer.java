package de.jgroehl.asteromania.time;

public class Timer {

	private int period;
	private long time = System.currentTimeMillis();

	public Timer(int period) {
		this.period = period;
		time = System.currentTimeMillis();
	}

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
