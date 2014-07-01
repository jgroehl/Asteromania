package de.jgroehl.asteromania.graphics.game;

import android.content.Context;
import android.graphics.Canvas;
import de.jgroehl.asteromania.control.GameHandler;
import de.jgroehl.asteromania.control.GameState;
import de.jgroehl.asteromania.graphics.animated.SimpleAnimatedObject;
import de.jgroehl.asteromania.graphics.game.Shot.Target;
import de.jgroehl.asteromania.graphics.game.statusBars.HpBar;
import de.jgroehl.asteromania.graphics.interfaces.Hitable;
import de.jgroehl.asteromania.graphics.interfaces.Killable;
import de.jgroehl.asteromania.time.Timer;

public class Enemy extends SimpleAnimatedObject implements Hitable, Killable {

	private static final float UPPER_BOUND = 0.1f;
	private static final float LOWER_BOUND = 0.2f;
	private final float speed;
	private final Timer shootTimer;

	private boolean moveRight = Math.random() > 0.5;
	private boolean moveTop = true;

	private final HpBar hpBar;
	private final float shotSpeed;
	private final int shotDamage;

	public Enemy(float xPosition, float yPosition, int graphicsId,
			int frameCount, int animationPeriod, Context context,
			int lifepoints, float shotSpeed, int basicShootFrequency,
			float basicSpeed, float basicDamage) {
		super(xPosition, yPosition, graphicsId, frameCount, animationPeriod,
				context);
		hpBar = new HpBar(lifepoints, 0.05f, 0.85f, 0.3f, 0.01f, context);
		this.shotSpeed = (float) (shotSpeed * getPlusMinusTwentyPercent());
		shootTimer = new Timer(
				(int) (basicShootFrequency * getPlusMinusTwentyPercent()));
		speed = (float) (basicSpeed * getPlusMinusTwentyPercent());
		shotDamage = (int) Math
				.round((basicDamage * getPlusMinusTwentyPercent()));
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
					shotDamage), GameState.MAIN);
		}

		hpBar.setPosition(xPosition, yPosition + relativeHeight);
		hpBar.setRelativeWidth(relativeWidth);

		super.update(handler);
	}

	@Override
	public void draw(Canvas c) {
		hpBar.draw(c);
		super.draw(c);
	}

	@Override
	public void getShot(GameHandler gameHandler, Shot shot) {
		if (shot.getTarget() == Target.ENEMY) {
			gameHandler.getSoundManager().playEnemyHitSound();
			hpBar.setCurrentValue(hpBar.getCurrentValue() - shot.getDamage());
			if (hpBar.getCurrentValue() <= 0) {
				Coin.addToHandler(gameHandler, this);
				gameHandler.remove(this);
			}
			gameHandler.remove(shot);
		}
	}

	public static Enemy createNormalEnemy(Context context, int level) {
		return new Enemy((float) Math.random(), 0.2f,
				de.jgroehl.asteromania.R.drawable.enemy, 15, 100, context, 20,
				0.02f, 2000, 0.01f, 1);
	}

	public static Enemy createBossEnemy(Context context, int level) {
		return new Enemy((float) Math.random(), 0.2f,
				de.jgroehl.asteromania.R.drawable.enemy2, 6, 100, context, 50,
				0.02f, 1000, 0.005f, 1);
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
