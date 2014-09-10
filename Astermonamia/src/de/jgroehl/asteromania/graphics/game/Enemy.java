package de.jgroehl.asteromania.graphics.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.jgroehl.asteromania.MainActivity;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.GameState;
import de.jgroehl.asteromania.graphics.animated.SimpleAnimatedObject;
import de.jgroehl.asteromania.graphics.game.Shot.Target;
import de.jgroehl.asteromania.graphics.game.statusBars.HpBar;
import de.jgroehl.asteromania.graphics.interfaces.Hitable;
import de.jgroehl.asteromania.graphics.interfaces.Killable;
import de.jgroehl.asteromania.time.Timer;

public class Enemy extends SimpleAnimatedObject implements Hitable, Killable {

	private static final double HEALTH_DROP_CHANCE = 0.5;
	private static final float BASIC_NORMAL_SPEED = 0.01f;
	private static final int BASIC_NORMAL_SHOT_RATE = 2000;
	private static final float BASIC_NORMAL_SHOT_SPEED = 0.02f;
	private static final float UPPER_BOUND = 0.1f;
	private static final float LOWER_BOUND = 0.2f;
	private static final int BASIC_NORMAL_LIFEPOINTS = 3;
	private static final float BASIC_NORMAL_DAMAGE = 1;
	private static final int MINIMUM_AMOUNT_COINS_DROPPED = 1;
	private final float speed;
	private final Timer shootTimer;

	private boolean moveRight = Math.random() > 0.5;
	private boolean moveTop = true;

	private final HpBar hpBar;
	private final float shotSpeed;
	private final int shotDamage;
	private static final Paint textPaint = new Paint();
	private static final float NORMAL_ENEMY_WIDTH = 0.12f;
	private static final float BOSS_ENEMY_WIDTH = 0.1f;

	private Enemy(float xPosition, float yPosition, int graphicsId,
			float relativeWidth, int frameCount, int animationPeriod,
			Context context, int lifepoints, float shotSpeed,
			int basicShootFrequency, float basicSpeed, float basicDamage) {
		super(xPosition, yPosition, graphicsId, relativeWidth, frameCount,
				animationPeriod, context);
		hpBar = new HpBar(lifepoints, 0.05f, 0.85f, 0.3f, 0.01f, context);
		this.shotSpeed = (float) (shotSpeed * getPlusMinusTwentyPercent());
		shootTimer = new Timer(
				(int) (basicShootFrequency * getPlusMinusTwentyPercent()));
		speed = (float) (basicSpeed * getPlusMinusTwentyPercent());
		shotDamage = (int) Math
				.round((basicDamage * getPlusMinusTwentyPercent()));
		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(18);
	}

	private double getPlusMinusTwentyPercent() {
		return Math.random() * 0.4 + 0.8;
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

		if (shootTimer.isPeriodOver()) {
			handler.getSoundManager().playEnemyShotSound();
			handler.add(new Shot(xPosition + relativeWidth / 2, yPosition
					+ relativeHeight * 0.4f, Target.PLAYER, shotSpeed, context,
					shotDamage, this), GameState.MAIN);
		}

		hpBar.setPosition(xPosition, yPosition + relativeHeight);
		hpBar.setRelativeWidth(relativeWidth);

		super.update(handler);
	}

	@Override
	public void draw(Canvas c) {
		hpBar.draw(c);
		super.draw(c);
		if (MainActivity.DEBUG) {
			c.drawText(
					hpBar.getCurrentValue() + "/" + hpBar.getMaximum(),
					getX() * c.getWidth(),
					(getY() + getRelativeHeight()) * c.getHeight()
							+ textPaint.getTextSize(), textPaint);
			c.drawText(
					context.getResources().getString(
							de.jgroehl.asteromania.R.string.dmg)
							+ ": " + shotDamage,
					getX() * c.getWidth(),
					(getY() + getRelativeHeight()) * c.getHeight()
							+ textPaint.getTextSize() * 2, textPaint);
		}
	}

	@Override
	public void getShot(GameHandler gameHandler, Shot shot) {
		if (shot.getTarget() == Target.ENEMY) {
			gameHandler.getSoundManager().playEnemyHitSound();
			hpBar.setCurrentValue(hpBar.getCurrentValue() - shot.getDamage());
			if (hpBar.getCurrentValue() <= 0) {
				int amountCoins = (int) ((0.25 + Math.random() * 0.5)
						* shotDamage + MINIMUM_AMOUNT_COINS_DROPPED);
				for (int i = 0; i < amountCoins; i++)
					Coin.addToHandler(gameHandler, this);
				if (gameHandler.getPlayerInfo().isMissingHealth()
						&& Math.random() < HEALTH_DROP_CHANCE) {
					Heart.addToHandler(shotDamage / 2, gameHandler, this);
				}
				gameHandler.getPlayerInfo().addScore(shotDamage);
				gameHandler.getSoundManager().playExplosionSound();
				Explosion.addExplosion(gameHandler, this);
				gameHandler.remove(this);
			}
			gameHandler.remove(shot);
		}
	}

	public static Enemy createNormalEnemy(Context context, int level) {
		return new Enemy((float) Math.random(), 0.2f,
				de.jgroehl.asteromania.R.drawable.enemy, NORMAL_ENEMY_WIDTH,
				15, 100, context,
				(int) (BASIC_NORMAL_LIFEPOINTS * 2 * ((level + 1) / 2.0)),
				BASIC_NORMAL_SHOT_SPEED / (float) ((Math.cbrt(level) + 2) / 3),
				(int) (BASIC_NORMAL_SHOT_RATE / 1.5), BASIC_NORMAL_SPEED,
				(int) (BASIC_NORMAL_DAMAGE * Math.sqrt(level)));
	}

	public static Enemy createBossEnemy(Context context, int level) {
		return new Enemy((float) Math.random(), 0.2f,
				de.jgroehl.asteromania.R.drawable.enemy2, BOSS_ENEMY_WIDTH, 6,
				100, context,
				(int) (BASIC_NORMAL_LIFEPOINTS * 5 * (level + 1) / 2.0),
				BASIC_NORMAL_SHOT_SPEED / (float) ((Math.cbrt(level) + 2) / 3),
				(int) (BASIC_NORMAL_SHOT_RATE / 3.0), (BASIC_NORMAL_SPEED / 2),
				(int) (BASIC_NORMAL_DAMAGE * 3 * Math.sqrt(level)));
	}

	@Override
	public boolean isAlive() {
		return hpBar.getCurrentValue() > 0;
	}

	@Override
	public void kill() {
		hpBar.setCurrentValue(0);
	}

}
