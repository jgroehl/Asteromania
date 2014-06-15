package de.jgroehl.asteromania.graphics.game;

import android.graphics.Bitmap;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.graphics.animated.AnimatedGraphicsObject;
import de.jgroehl.asteromania.graphics.interfaces.Hitable;

public class Enemy extends AnimatedGraphicsObject implements Hitable {

	private float speed = 0.005f;

	private boolean moveRight = true;

	public Enemy(Bitmap graphics, int frameCount, int animationPeriod) {
		super(0.5f, 0.2f, graphics, frameCount, animationPeriod,
				Align.MID_CENTER);
	}

	@Override
	public void update(GameHandler handler) {
		if (moveRight) {
			xPosition = xPosition + speed;
			if (xPosition > SCREEN_MAXIMUM)
				moveRight = false;
		} else {
			xPosition = xPosition - speed;
			if (xPosition < SCREEN_MINIMUM)
				moveRight = true;
		}
	}

	@Override
	public void getShot(GameHandler gameHandler, Shot shot) {
		xPosition = (float) Math.random();
		moveRight = !moveRight;
	}

}
