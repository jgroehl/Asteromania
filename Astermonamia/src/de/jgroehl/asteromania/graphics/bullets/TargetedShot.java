package de.jgroehl.asteromania.graphics.bullets;

import android.content.Context;
import de.jgroehl.api.control.BaseGameHandler;
import de.jgroehl.api.graphics.AbstractDamagingAbility;
import de.jgroehl.api.graphics.GraphicsObject;
import de.jgroehl.api.graphics.Target;
import de.jgroehl.asteromania.control.AsteromaniaGameHandler;
import de.jgroehl.asteromania.graphics.enemies.BaseEnemy;
import de.jgroehl.asteromania.graphics.interfaces.Hitable;

public class TargetedShot extends AbstractDamagingAbility
{

	private static final float ROCKET_WIDTH = 0.1f;
	private static final int DAMAGE_FACTOR = 2;
	private final BaseEnemy targetEnemy;

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
		super(xPosition, yPosition, de.jgroehl.asteromania.R.drawable.rocket, ROCKET_WIDTH, Target.ENEMY, damage,
				context);
		this.targetEnemy = targetEnemy;
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
		rotateTowardsLocation(getX(), getY(), targetEnemy.getX(), targetEnemy.getY());
		moveInDirectionOfRotation(0.01f);

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
		return new TargetedShot(gameHandler.getPlayer().getX() - gameHandler.getPlayer().getRelativeWidth() / 2
				+ ROCKET_WIDTH / 2, gameHandler.getPlayer().getY() + gameHandler.getPlayer().getRelativeHeight() / 2,
				DAMAGE_FACTOR
						* (gameHandler.getPlayer().getShotDamage() + gameHandler.getPlayerInfo().getBonusDamage()),
				target, gameHandler.getContext());
	}
}
