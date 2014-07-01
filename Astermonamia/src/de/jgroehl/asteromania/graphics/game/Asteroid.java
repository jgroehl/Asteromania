package de.jgroehl.asteromania.graphics.game;

import android.content.Context;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.GameState;
import de.jgroehl.asteromania.graphics.GraphicsObject;
import de.jgroehl.asteromania.graphics.game.Shot.Target;
import de.jgroehl.asteromania.graphics.interfaces.Hitable;
import de.jgroehl.asteromania.graphics.interfaces.Killable;
import de.jgroehl.asteromania.graphics.interfaces.Updatable;

public class Asteroid extends GraphicsObject implements Updatable, Hitable,
		Killable {

	private int life;
	private int damage;
	private float speed;

	public enum AsteroidType {
		SMALL, MEDIUM, LARGE, HUGE, EXTREME;
	}

	public Asteroid(float xPosition, float yPosition, int graphicsId,
			Context context, int life, int damage, float speed) {
		super(xPosition, yPosition, graphicsId, context);
		this.life = life;
		this.damage = damage;
		this.speed = speed;
	}

	@Override
	public void update(GameHandler gameHandler) {
		if (imagesOverlap(getX(), getY(), relativeWidth, relativeHeight,
				gameHandler.getPlayer().getX(), gameHandler.getPlayer().getY(),
				gameHandler.getPlayer().getRelativeWidth(), gameHandler
						.getPlayer().getRelativeHeight())) {
			gameHandler.getPlayer().getHitByAsteroid(gameHandler, damage);
			life = 0;
			gameHandler.remove(this);
		}
		yPosition = yPosition + speed;
		if (yPosition > (1.0 + getRelativeHeight())) {
			gameHandler.remove(this);
			life = 0;
		}
	}

	public static Asteroid createAsteroid(Context context, int level) {
		return new Asteroid((float) Math.random(),
				(float) (Math.random() * (-2)),
				de.jgroehl.asteromania.R.drawable.rock5, context, 5, 3, 0.01f);
	}

	@Override
	public void getShot(GameHandler gameHandler, Shot shot) {
		if (shot.getTarget() == Target.ENEMY) {
			gameHandler.getSoundManager().playEnemyHitSound();
			life -= shot.getDamage();
			gameHandler.remove(shot);
			if (life <= 0) {
				gameHandler.remove(this);
				gameHandler.add(new Coin(xPosition + relativeWidth / 2,
						yPosition + relativeHeight * 0.4f, context),
						GameState.MAIN);
			}
		}
	}

	@Override
	public boolean isAlive() {
		return life > 0;
	}

	@Override
	public void kill() {
		life = 0;
	}
}
