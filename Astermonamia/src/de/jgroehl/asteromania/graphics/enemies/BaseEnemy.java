package de.jgroehl.asteromania.graphics.enemies;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.graphics.AbstractDamagingAbility;
import de.jgroehl.api.graphics.Target;
import de.jgroehl.api.graphics.animated.SimpleAnimatedObject;
import de.jgroehl.api.graphics.interfaces.Killable;
import de.jgroehl.api.graphics.statusBars.HpBar;
import de.jgroehl.api.time.Timer;
import de.jgroehl.asteromania.AsteromaniaMainActivity;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;
import de.jgroehl.asteromania.graphics.Explosion;
import de.jgroehl.asteromania.graphics.interfaces.Hitable;

public abstract class BaseEnemy extends SimpleAnimatedObject implements Hitable, Killable
{

	private static final float UPPER_BOUND = 0.075f;
	private static final float LOWER_BOUND = 0.175f;
	private final float speed;
	private final Timer shootTimer;

	private boolean moveRight = Math.random() > 0.5;
	private boolean moveTop = true;

	private final HpBar hpBar;
	private final float shotSpeed;
	private final int shotDamage;
	private static final Paint textPaint = new Paint();

	protected BaseEnemy(float xPosition, float yPosition, int graphicsId, float relativeWidth, int frameCount,
			int animationPeriod, Context context, int lifepoints, float shotSpeed, int basicShootFrequency,
			float basicSpeed, float basicDamage)
	{
		super(xPosition, yPosition, graphicsId, relativeWidth, frameCount, animationPeriod, context);
		hpBar = new HpBar(lifepoints, 0.05f, 0.85f, 0.3f, 0.01f, context);
		this.shotSpeed = (float) (shotSpeed * getPlusMinusTwentyPercent());
		shootTimer = new Timer((int) (basicShootFrequency * getPlusMinusTwentyPercent()));
		speed = (float) (basicSpeed * getPlusMinusTwentyPercent());
		shotDamage = (int) Math.round((basicDamage * getPlusMinusTwentyPercent()));
		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(18);
	}

	private double getPlusMinusTwentyPercent()
	{
		return Math.random() * 0.4 + 0.8;
	}

	public float getShotSpeed()
	{
		return shotSpeed;
	}

	public int getShotDamage()
	{
		return shotDamage;
	}

	@Override
	public void update(BaseGameHandler handler)
	{
		if (handler instanceof AsteromaniaGameHandler)
		{

			AsteromaniaGameHandler asteromaniaGameHandler = (AsteromaniaGameHandler) handler;
			if (moveRight)
			{
				setPosition(getX() + speed, getY());
				if (getX() > SCREEN_MAXIMUM - getRelativeWidth() / 2)
					moveRight = false;
			}
			else
			{
				setPosition(getX() - speed, getY());
				if (getX() < SCREEN_MINIMUM - getRelativeWidth() / 2)
					moveRight = true;
			}

			if (moveTop)
			{
				setPosition(getX(), getY() - speed / 2);
				if (getY() < UPPER_BOUND)
					moveTop = false;
			}
			else
			{
				setPosition(getX(), getY() + speed / 2);
				if (getY() > LOWER_BOUND)
					moveTop = true;
			}

			if (shootTimer.isPeriodOver())
			{
				shoot(asteromaniaGameHandler);
			}

			hpBar.setPosition(getX(), getY() + relativeHeight);
			hpBar.setRelativeWidth(relativeWidth);

			super.update(handler);
		}
	}

	protected abstract void shoot(AsteromaniaGameHandler handler);

	@Override
	public void draw(Canvas c)
	{
		hpBar.draw(c);
		super.draw(c);
		if (AsteromaniaMainActivity.DEBUG)
		{
			c.drawText(hpBar.getCurrentValue() + "/" + hpBar.getMaximum(), getX() * c.getWidth(),
					(getY() + getRelativeHeight()) * c.getHeight() + textPaint.getTextSize(), textPaint);
			c.drawText(getContext().getResources().getString(de.jgroehl.asteromania.R.string.dmg) + ": " + shotDamage,
					getX() * c.getWidth(),
					(getY() + getRelativeHeight()) * c.getHeight() + textPaint.getTextSize() * 2, textPaint);
		}
	}

	@Override
	public void getShot(BaseGameHandler gameHandler, AbstractDamagingAbility shot)
	{
		if (gameHandler instanceof AsteromaniaGameHandler)
		{

			AsteromaniaGameHandler asteromaniaGameHandler = (AsteromaniaGameHandler) gameHandler;
			if (shot.getTarget() == Target.ENEMY)
			{
				asteromaniaGameHandler.getSoundManager().playEnemyHitSound();
				hpBar.setCurrentValue(hpBar.getCurrentValue() - shot.getDamage());
				if (hpBar.getCurrentValue() <= 0)
				{
					dropItems(asteromaniaGameHandler);
					asteromaniaGameHandler.getApiHandler().killedEnemy();
					asteromaniaGameHandler.getPlayerInfo().addScore(shotDamage);
					asteromaniaGameHandler.getSoundManager().playExplosionSound();
					Explosion.addExplosion(asteromaniaGameHandler, this);
					gameHandler.remove(this);
				}
				((AsteromaniaGameHandler) gameHandler).getPlayerInfo().incrementHits();
				gameHandler.remove(shot);
			}
		}
	}

	protected abstract void dropItems(AsteromaniaGameHandler asteromaniaGameHandler);

	@Override
	public boolean isAlive()
	{
		return hpBar.getCurrentValue() > 0;
	}

	@Override
	public void kill()
	{
		hpBar.setCurrentValue(0);
	}

}
