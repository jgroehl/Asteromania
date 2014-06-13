package de.jgroehl.asteromania.time;

public class Timer {

	private final int period;
	private long time = System.currentTimeMillis();

	public Timer(int period) {
		this.period = period;
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

}
