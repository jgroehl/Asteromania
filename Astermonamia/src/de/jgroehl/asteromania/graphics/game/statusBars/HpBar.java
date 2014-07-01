package de.jgroehl.asteromania.graphics.game.statusBars;

import android.content.Context;
import android.graphics.Color;

public class HpBar extends StatusBar {

	public HpBar(int maximum, int currentValue, float xPosition,
			float yPosition, float relativeWidth, float relativeHeight,
			Context context) {
		this(maximum, xPosition, yPosition, relativeWidth, relativeHeight,
				context);
		setCurrentValue(currentValue);
	}

	public HpBar(int maximum, float xPosition, float yPosition,
			float relativeWidth, float relativeHeight, Context context) {
		super(maximum, xPosition, yPosition, relativeWidth, relativeHeight,
				context, FULL, Color.GREEN);
	}

	@Override
	public void setCurrentValue(int currentValue) {
		float relativeValue = ((float) currentValue / getMaximum());
		if (relativeValue > 0.67) {
			setColor(Color.GREEN);
		} else if (relativeValue > 0.34) {
			setColor(Color.YELLOW);
		} else {
			setColor(Color.RED);
		}
		super.setCurrentValue(currentValue);
	}

	@Override
	public void reset() {
		setCurrentValue(getMaximum());
	}

}
