package de.jgroehl.asteromania.graphics.game;

import android.content.Context;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.GameState;
import de.jgroehl.asteromania.graphics.animated.SimpleAnimatedObject;
import de.jgroehl.asteromania.graphics.game.Shot.Target;
import de.jgroehl.asteromania.graphics.interfaces.Hitable;

public class Enemy extends SimpleAnimatedObject implements Hitable {

	private float speed = 0.01f;

	private boolean moveRight = true;

	public Enemy(int frameCount, int animationPeriod, Context context) {
		super(0.5f, 0.2f, de.jgroehl.asteromania.R.drawable.enemy, frameCount,
				animationPeriod, context);
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
			gameHandler.add(new Coin(xPosition, yPosition, context),
					GameState.MAIN);
			xPosition = (float) Math.random();
			moveRight = !moveRight;
			gameHandler.remove(shot);
		}
	}

}
