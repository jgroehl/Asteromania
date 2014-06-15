package de.jgroehl.asteromania.graphics.game;

import android.graphics.Bitmap;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.graphics.animated.SimpleAnimatedObject;
import de.jgroehl.asteromania.graphics.game.Shot.Target;
import de.jgroehl.asteromania.graphics.interfaces.Hitable;

public class Enemy extends SimpleAnimatedObject implements Hitable {

	private float speed = 0.005f;

	private boolean moveRight = true;

	public Enemy(Bitmap graphics, int frameCount, int animationPeriod) {
		super(0.5f, 0.2f, graphics, frameCount, animationPeriod);
	}

	@Override
	public void update(GameHandler handler) {
		if (moveRight) {
			xPosition = xPosition + speed;
			if (xPosition > SCREEN_MAXIMUM - getRelativeWidth() / 2)
				moveRight = false;
		} else {
			xPosition = xPosition - speed;
			if (xPosition < SCREEN_MINIMUM - getRelativeWidth() / 2)
				moveRight = true;
		}
		super.update(handler);
	}

	@Override
	public void getShot(GameHandler gameHandler, Shot shot) {
		if (shot.getTarget() == Target.ENEMY) {
			xPosition = (float) Math.random();
			moveRight = !moveRight;
			gameHandler.remove(shot);
		}
	}

}
