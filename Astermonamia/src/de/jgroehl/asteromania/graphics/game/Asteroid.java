package de.jgroehl.asteromania.graphics.game;

import android.content.Context;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.graphics.GraphicsObject;
import de.jgroehl.asteromania.graphics.game.Shot.Target;
import de.jgroehl.asteromania.graphics.interfaces.Hitable;
import de.jgroehl.asteromania.graphics.interfaces.Killable;
import de.jgroehl.asteromania.graphics.interfaces.Updatable;

public class Asteroid extends GraphicsObject implements Updatable, Hitable,
		Killable {

	private static final int BASIC_LIFE = 2;
	private static final int BASIC_DAMAGE = 2;
	private static final float BASIC_SPEED = 0.015f;

	private int life;
	private int damage;
	private float speed;

	public enum AsteroidType {
		SMALL(0.5f, de.jgroehl.asteromania.R.drawable.rock3), MEDIUM(0.67f,
				de.jgroehl.asteromania.R.drawable.rock5), LARGE(1f,
				de.jgroehl.asteromania.R.drawable.rock4), HUGE(1.5f,
				de.jgroehl.asteromania.R.drawable.rock2), EXTREME(2f,
				de.jgroehl.asteromania.R.drawable.rock1);

		private final float modifyer;
		private final int image;

		private AsteroidType(float modifyer, int image) {
			this.modifyer = modifyer;
			this.image = image;
		}

		public float getModifyer() {
			return modifyer;
		}

		public int getImage() {
			return image;
		}
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
		AsteroidType type = getRandomAsteroidType();
		return new Asteroid((float) Math.random(),
				(float) (Math.random() * (-Math.cbrt(level))), type.getImage(),
				context, (int) Math.round(BASIC_LIFE * type.getModifyer()
						* Math.cbrt(level)), (int) Math.round(BASIC_DAMAGE
						* type.getModifyer() * Math.sqrt(level)), BASIC_SPEED
						/ (float) Math.sqrt(type.getModifyer()));
	}

	private static AsteroidType getRandomAsteroidType() {
		return AsteroidType.values()[(int) (Math.random() * AsteroidType
				.values().length)];
	}

	@Override
	public void getShot(GameHandler gameHandler, Shot shot) {
		if (shot.getTarget() == Target.ENEMY) {
			gameHandler.getSoundManager().playEnemyHitSound();
			life -= shot.getDamage();
			gameHandler.remove(shot);
			if (life <= 0) {
				gameHandler.remove(this);
				int amountCoins = (int) Math.round(Math.random()
						* Math.sqrt(damage)) + 1;
				for (int i = 0; i < amountCoins; i++)
					Coin.addToHandler(gameHandler, this);
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
