package de.jgroehl.asteromania.graphics.game;

import android.content.Context;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.GameState;
import de.jgroehl.asteromania.graphics.animated.SimpleAnimatedObject;
import de.jgroehl.asteromania.graphics.game.Shot.Target;
import de.jgroehl.asteromania.graphics.interfaces.Hitable;

public class Enemy extends SimpleAnimatedObject implements Hitable {

	private static final float UPPER_BOUND = 0.1f;
	private static final float LOWER_BOUND = 0.2f;
	private float speed = 0.01f;

	private boolean moveRight = true;
	private boolean moveTop = true;

	public Enemy(float xPosition, float yPosition, int graphicsId,
			int frameCount, int animationPeriod, Context context) {
		super(xPosition, yPosition, graphicsId, frameCount, animationPeriod,
				context);
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

		if (moveTop) {
			yPosition = yPosition - speed / 2;
			if (yPosition < UPPER_BOUND)
				moveTop = false;
		} else {
			yPosition = yPosition + speed / 2;
			if (yPosition > LOWER_BOUND)
				moveTop = true;
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

	public static Enemy createNormalEnemy(Context context) {

		return new Enemy((float) Math.random(), 0.2f,
				de.jgroehl.asteromania.R.drawable.enemy, 15, 100, context);

	}

}
