package de.jgroehl.asteromania.graphics.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.jgroehl.asteromania.MainActivity;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.graphics.GraphicsObject;
import de.jgroehl.asteromania.graphics.game.Shot.Target;
import de.jgroehl.asteromania.graphics.interfaces.Hitable;
import de.jgroehl.asteromania.graphics.interfaces.Killable;
import de.jgroehl.asteromania.graphics.interfaces.Updatable;

public class Asteroid extends GraphicsObject implements Updatable, Hitable,
		Killable {

	private static final int BASIC_LIFE = 4;
	private static final int BASIC_DAMAGE = 8;
	private static final float BASIC_SPEED = 0.01f;
	private static final Paint textPaint = new Paint();

	private int life;
	private int damage;
	private float speed;

	public enum AsteroidType {
		SMALL(0.4f, de.jgroehl.asteromania.R.drawable.rock3, 0.05f), MEDIUM(
				0.8f, de.jgroehl.asteromania.R.drawable.rock5, 0.07f), LARGE(
				1.2f, de.jgroehl.asteromania.R.drawable.rock4, 0.09f), HUGE(
				1.7f, de.jgroehl.asteromania.R.drawable.rock2, 0.11f), EXTREME(
				2.8f, de.jgroehl.asteromania.R.drawable.rock1, 0.13f);

		private final float modifyer;
		private final float width;
		private final int image;

		private AsteroidType(float modifyer, int image, float width) {
			this.modifyer = modifyer;
			this.image = image;
			this.width = width;
		}

		public float getModifyer() {
			return modifyer;
		}

		public int getImage() {
			return image;
		}

		public float getWidth() {
			return width;
		}
	}

	private Asteroid(float xPosition, float yPosition, int graphicsId,
			float relativeWidth, Context context, int life, int damage,
			float speed) {
		super(xPosition, yPosition, graphicsId, relativeWidth, context);
		this.life = life;
		this.damage = damage;
		this.speed = speed;
		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(18);
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
				(float) (Math.random() * (-(level / 5.0))), type.getImage(),
				type.getWidth(), context, (int) Math.round(BASIC_LIFE
						* type.getModifyer() * Math.sqrt(level)),
				(int) Math.round(BASIC_DAMAGE * type.getModifyer()
						* Math.sqrt(level)), BASIC_SPEED
						/ (float) Math.cbrt(type.getModifyer()));
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
				int amountCoins = (int) Math.round((0.5 + Math.random() * 0.5)
						* Math.sqrt(damage)) + 1;
				for (int i = 0; i < amountCoins; i++)
					Coin.addToHandler(gameHandler, this);
				gameHandler.getApiHandler().destroyedAsteroid();
				gameHandler.getPlayerInfo().addScore((damage + 3) / 4);
				gameHandler.getSoundManager().playExplosionSound();
				Explosion.addExplosion(gameHandler, this);
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

	@Override
	public void draw(Canvas c) {
		super.draw(c);
		if (MainActivity.DEBUG) {
			c.drawText(
					context.getResources().getString(
							de.jgroehl.asteromania.R.string.hp)
							+ ": " + life,
					getX() * c.getWidth(),
					(getY() + getRelativeHeight()) * c.getHeight()
							+ textPaint.getTextSize(), textPaint);
			c.drawText(
					context.getResources().getString(
							de.jgroehl.asteromania.R.string.dmg)
							+ ": " + damage,
					getX() * c.getWidth(),
					(getY() + getRelativeHeight()) * c.getHeight()
							+ textPaint.getTextSize() * 2, textPaint);
		}
	}
}
