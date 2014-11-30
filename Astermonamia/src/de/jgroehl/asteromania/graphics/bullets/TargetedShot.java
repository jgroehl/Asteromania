package de.jgroehl.asteromania.graphics.bullets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.graphics.AbstractDamagingAbility;
import de.jgroehl.api.graphics.GraphicsObject;
import de.jgroehl.api.graphics.Target;
import de.jgroehl.api.graphics.interfaces.Hitable;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;
import de.jgroehl.asteromania.graphics.enemies.BaseEnemy;

public class TargetedShot extends AbstractDamagingAbility
{

	private final BaseEnemy targetEnemy;
	private float rotation = 0;
	private Paint crosshairsPaint = new Paint();

	/**
	 * Fires a targeted shot from the players perspective onto an enemy.
	 * 
	 * @param xPosition
	 * @param yPosition
	 * @param shotSpeed
	 * @param context
	 * @param damage
	 * @param source
	 */
	public TargetedShot(float xPosition, float yPosition, int damage, BaseEnemy targetEnemy, Context context)
	{
		super(xPosition, yPosition, de.jgroehl.asteromania.R.drawable.rocket, 0.1f, Target.ENEMY, damage, context);
		this.targetEnemy = targetEnemy;

		crosshairsPaint.setColor(Color.RED);
		crosshairsPaint.setStyle(Style.FILL_AND_STROKE);
	}

	@Override
	public void draw(Canvas c)
	{
		c.save(Canvas.MATRIX_SAVE_FLAG);
		float x = getX();
		float y = getY();
		c.translate((getX() + getRelativeWidth() / 2) * c.getWidth(),
				(getY() + getRelativeHeight() / 2) * c.getHeight());
		c.rotate(-(float) (rotation * 180 / Math.PI));
		setPosition(0, 0);
		super.draw(c);
		c.restore();
		setPosition(x, y);
		drawCrossHairs(c);
	}

	private void drawCrossHairs(Canvas c)
	{
		c.drawCircle((targetEnemy.getX() + targetEnemy.getRelativeWidth() / 2) * c.getWidth(),
				(targetEnemy.getY() + targetEnemy.getRelativeHeight() / 2) * c.getHeight(), 5, crosshairsPaint);
	}

	@Override
	public void update(BaseGameHandler gameHandler)
	{
		AsteromaniaGameHandler handler = (AsteromaniaGameHandler) gameHandler;
		if (!targetEnemy.isAlive())
		{
			handler.remove(this);
			handler.getPlayerInfo().incrementMisses();
			return;
		}
		rotation = calcRotation();
		setPosition((float) (getX() + Math.cos(rotation) * 0.01f), (float) (getY() - Math.sin(rotation) * 0.01f));

		for (Hitable hitable : handler.getHitableObjects())
		{
			GraphicsObject go = (GraphicsObject) hitable;
			if (imagesOverlap(getX(), getY(), getRelativeWidth(), getRelativeHeight(), go.getX(), go.getY(),
					go.getRelativeWidth(), go.getRelativeHeight()))
			{
				if (hitable.isAlive())
					hitable.getShot(handler, this);
			}
		}
	}

	private float calcRotation()
	{
		return (float) Math.atan2(-(targetEnemy.getY() - getY()), (targetEnemy.getX() - getX()));
	}

	public static TargetedShot createShotWithRandomTarget(AsteromaniaGameHandler gameHandler)
	{
		BaseEnemy target = gameHandler.getRandomEnemy();
		if (target == null)
			return null;
		else
			return createTargetedShot(gameHandler, target);
	}

	private static TargetedShot createTargetedShot(AsteromaniaGameHandler gameHandler, BaseEnemy target)
	{
		return new TargetedShot(gameHandler.getPlayer().getX() + gameHandler.getPlayer().getRelativeWidth() / 2,
				gameHandler.getPlayer().getY() + gameHandler.getPlayer().getRelativeHeight() / 2, gameHandler
						.getPlayer().getShotDamage() + gameHandler.getPlayerInfo().getBonusDamage(), target,
				gameHandler.getContext());
	}
}
