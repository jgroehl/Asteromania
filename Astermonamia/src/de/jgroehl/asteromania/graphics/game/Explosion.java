package de.jgroehl.asteromania.graphics.game;

import android.content.Context;
import de.jgroehl.asteromania.R;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.graphics.animated.AnimatedGraphicsObject;

public class Explosion extends AnimatedGraphicsObject {

	public Explosion(float xPosition, float yPosition, Context context) {
		super(xPosition, yPosition, R.drawable.kaboom, 6, 50, context);
	}

	@Override
	public void update(GameHandler gameHandler) {
		setFrame(getFrame() + 1);
		if (getFrame() == getMaxFrame()-1) {
			gameHandler.remove(this);
		}
	}
}
