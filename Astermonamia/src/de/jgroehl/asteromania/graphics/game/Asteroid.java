package de.jgroehl.asteromania.graphics.game;

import android.content.Context;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.graphics.GraphicsObject;
import de.jgroehl.asteromania.graphics.game.Shot.Target;
import de.jgroehl.asteromania.graphics.interfaces.Hitable;
import de.jgroehl.asteromania.graphics.interfaces.Updatable;

public class Asteroid extends GraphicsObject implements Updatable, Hitable {

	private int life;
	private int damage;

	public Asteroid(float xPosition, float yPosition, int graphicsId,
			Context context, int life, int damage) {
		super(xPosition, yPosition, graphicsId, context);
		this.life = life;
		this.damage = damage;
	}

	@Override
	public void update(GameHandler gameHandler) {
		if (imagesOverlap(getX(), getY(), relativeWidth, relativeHeight,
				gameHandler.getPlayer().getX(), gameHandler.getPlayer().getY(),
				gameHandler.getPlayer().getRelativeWidth(), gameHandler
						.getPlayer().getRelativeHeight())) {
			gameHandler.getPlayer().getHitByAsteroid(gameHandler, damage);
			gameHandler.remove(this);
		}
	}

	public static Asteroid createSmallAsteroid(Context context) {
		return new Asteroid((float) Math.random(),
				(float) (Math.random() * (-2)),
				de.jgroehl.asteromania.R.drawable.rock5, context, 5, 3);
	}

	@Override
	public void getShot(GameHandler gameHandler, Shot shot) {
		if (shot.getTarget() == Target.ENEMY) {
			gameHandler.getSoundManager().playEnemyHitSound();
			life -= shot.getDamage();
			gameHandler.remove(shot);
			if (life <= 0)
				gameHandler.remove(this);
		}
	}
}
